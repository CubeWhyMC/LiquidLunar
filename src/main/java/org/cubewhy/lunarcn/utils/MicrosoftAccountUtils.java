package org.cubewhy.lunarcn.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.account.MicrosoftAccount;
import org.cubewhy.lunarcn.files.configs.AccountConfigFile;
import org.cubewhy.lunarcn.gui.altmanager.LoginScreen;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.gui.notification.Notification;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.HttpUtils.JSON;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;
import static org.cubewhy.lunarcn.utils.WebUtils.getRequestParam;
import static org.cubewhy.lunarcn.utils.WebUtils.handleResponse;

public class MicrosoftAccountUtils {
    public static final String REDIRECT_URL = "https://127.0.0.1:8888/login";
    public static String CLIENT_ID = "dbb5fc17-43ac-4aa6-997e-ca69cde129a4";
    private static MicrosoftAccountUtils instance = null;

    private MicrosoftAccountUtils() {
    }

    public static MicrosoftAccountUtils getInstance() {
        if (instance == null) {
            instance = new MicrosoftAccountUtils();
        }
        return instance;
    }

    /**
     * 自动刷新账户
     */
    public void autoRefresh() {
        logger.info("Start refreshing accounts...");
        IAccount[] accounts = AccountConfigFile.getInstance().getAccounts();
        for (IAccount account : accounts) {
            if (account instanceof MicrosoftAccount) {
                Date lastFresh = ((MicrosoftAccount) account).getLastFresh();
                if (lastFresh.before(new Date(lastFresh.getTime() + 86400000))) {
                    try {
                        ((MicrosoftAccount) account).refresh();
                        logger.info("Refreshing " + account.getUserName());
                    } catch (IOException e) {
                        logger.error("Failed to refresh " + account.getUserName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getLoginUrl() {
        return "https://login.live.com/oauth20_authorize.srf?client_id=" + CLIENT_ID +
                "&response_type=code&redirect_uri=" + REDIRECT_URL +
                "&scope=XboxLive.signin%20offline_access";
    }

    public MicrosoftAccount loginWithToken(String token) throws IOException {
        JsonElement tokenMicrosoft = MicrosoftAccountUtils.getInstance().authFromRefreshToken(token);
        String accessToken = tokenMicrosoft.getAsJsonObject().get("access_token").getAsString();

        JsonElement tokenXboxLive = MicrosoftAccountUtils.getInstance().authWithXboxLive(accessToken);
        String xboxLiveToken = tokenXboxLive.getAsJsonObject().get("Token").getAsString();
        String userHash = tokenXboxLive.getAsJsonObject().get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();

        JsonElement tokenXSTS = MicrosoftAccountUtils.getInstance().authWithXSTS(xboxLiveToken);
        String XSTSToken = tokenXSTS.getAsJsonObject().get("Token").getAsString();

        JsonElement tokenMinecraft = MicrosoftAccountUtils.getInstance().authWithMinecraft(userHash, XSTSToken);
        String minecraftToken = tokenMinecraft.getAsJsonObject().get("access_token").getAsString();

        JsonElement storeInformation = MicrosoftAccountUtils.getInstance().getStoreInformation(minecraftToken);
        JsonArray storeProducts = storeInformation.getAsJsonObject().get("items").getAsJsonArray();
        boolean haveGame = false;
        for (JsonElement product : storeProducts) {
            if (product.getAsJsonObject().get("name").getAsString().equals("game_minecraft")) {
                haveGame = true;
                break;
            }
        }
        if (!haveGame) {
            throw new RuntimeException("no game");
        }

        JsonElement MinecraftProfile = MicrosoftAccountUtils.getInstance().getProfile(minecraftToken);
        String uuid = MinecraftProfile.getAsJsonObject().get("id").getAsString();
        String userName = MinecraftProfile.getAsJsonObject().get("name").getAsString();
        String refreshToken = tokenMicrosoft.getAsJsonObject().get("refresh_token").getAsString();

        return new MicrosoftAccount(userName, uuid, minecraftToken, refreshToken, new Date());
    }

    private JsonElement authFromAuthCode(String code) throws IOException {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client_id", CLIENT_ID);
        paramsMap.put("code", code);
        paramsMap.put("grant_type", "authorization_code");
        paramsMap.put("redirect_uri", REDIRECT_URL);

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url("https://login.live.com/oauth20_token.srf").post(formBody).build();

        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement authFromRefreshToken(String token) throws IOException {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client_id", CLIENT_ID);
        paramsMap.put("refresh_token", token);
        paramsMap.put("grant_type", "refresh_token");
        paramsMap.put("redirect_uri", REDIRECT_URL);

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url("https://login.live.com/oauth20_token.srf").post(formBody).build();

        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement authWithXboxLive(String accessToken) throws IOException {
        String json = String.format("{\"Properties\": {\"AuthMethod\": \"RPS\", \"SiteName\": \"user.auth.xboxlive.com\", \"RpsTicket\": \"d=%s\"}, \"RelyingParty\": \"http://auth.xboxlive.com\", \"TokenType\": \"JWT\"}", accessToken);
        Request request = new Request.Builder()
                .url("https://user.auth.xboxlive.com/user/authenticate")
                .post(RequestBody.create(json, JSON))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement authWithXSTS(String accessToken) throws IOException {
        String json = String.format("{\"Properties\": {\"SandboxId\": \"RETAIL\",\n\"UserTokens\": [\"%s\"]},\"RelyingParty\": \"rp://api.minecraftservices.com/\",\"TokenType\": \"JWT\"}", accessToken);
        Request request = new Request.Builder()
                .url("https://xsts.auth.xboxlive.com/xsts/authorize")
                .post(RequestBody.create(json, JSON))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement authWithMinecraft(String userHash, String accessToken) throws IOException {
        String json = String.format("{\"identityToken\": \"XBL3.0 x=%s;%s\"}", userHash, accessToken);
        Request request = new Request.Builder()
                .url("https://api.minecraftservices.com/authentication/login_with_xbox")
                .post(RequestBody.create(json, JSON))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement getStoreInformation(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.minecraftservices.com/entitlements/mcstore")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    private JsonElement getProfile(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.minecraftservices.com/minecraft/profile")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = HttpUtils.request(request).execute()) {
            if (response.body() != null) {
                return new JsonParser().parse(response.body().string());
            }
        }
        return null;
    }

    public void loginWithBrowser() {
        try {
            URI uri = new URI(this.getLoginUrl());
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class LoginHttpServer {
        private static LoginHttpServer instance = null;
        private final HttpsServer httpsServer;
        private final ExecutorService executorService;
        private final int POOL_SIZE = 4;

        private final Handler handler = new Handler();

        private LoginHttpServer() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException {
            httpsServer = HttpsServer.create(new InetSocketAddress(8888), 0);
            httpsServer.createContext("/login", handler);
            httpsServer.createContext("/", new PageAboutClient());
            executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * POOL_SIZE);
            httpsServer.setExecutor(executorService);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(FileUtils.getFile("lunarcn/127.0.0.1.jks"), "abc123".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, "abc123".toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(kmf.getKeyManagers(), null, null);
            HttpsConfigurator httpsConfigurator = new HttpsConfigurator(sslContext);
            httpsServer.setHttpsConfigurator(httpsConfigurator);
            httpsServer.start();
        }

        public static LoginHttpServer getInstance() throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
            if (instance == null) {
                instance = new LoginHttpServer();
            }
            return instance;
        }

        public String getCode() {
            return handler.getCode();
        }

        static class PageAboutClient implements HttpHandler {

            /**
             * Handle the given request and generate an appropriate response.
             * See {@link HttpExchange} for a description of the steps
             * involved in handling an exchange.
             *
             * @param exchange the exchange containing the request from the
             *                 client and used to send the response
             * @throws NullPointerException if exchange is <code>null</code>
             */
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    handleResponse(exchange, "<meta http-equiv=\"Refresh\" content=\"0; URL=http://cubewhy.github.io/\" />");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        static class Handler implements HttpHandler {
            private String code;

            public String getCode() {
                return code;
            }

            @Override
            public void handle(HttpExchange httpExchange) {
                String responseText;
                try {
                    String args = getRequestParam(httpExchange);
                    this.code = args.substring(5);

                    JsonElement tokenMicrosoft = MicrosoftAccountUtils.getInstance().authFromAuthCode(this.code);
                    String accessToken = tokenMicrosoft.getAsJsonObject().get("access_token").getAsString();

                    JsonElement tokenXboxLive = MicrosoftAccountUtils.getInstance().authWithXboxLive(accessToken);
                    String xboxLiveToken = tokenXboxLive.getAsJsonObject().get("Token").getAsString();
                    String userHash = tokenXboxLive.getAsJsonObject().get("DisplayClaims").getAsJsonObject().get("xui").getAsJsonArray().get(0).getAsJsonObject().get("uhs").getAsString();

                    JsonElement tokenXSTS = MicrosoftAccountUtils.getInstance().authWithXSTS(xboxLiveToken);
                    String XSTSToken = tokenXSTS.getAsJsonObject().get("Token").getAsString();

                    JsonElement tokenMinecraft = MicrosoftAccountUtils.getInstance().authWithMinecraft(userHash, XSTSToken);
                    String minecraftToken = tokenMinecraft.getAsJsonObject().get("access_token").getAsString();

                    JsonElement storeInformation = MicrosoftAccountUtils.getInstance().getStoreInformation(minecraftToken);
                    JsonArray storeProducts = storeInformation.getAsJsonObject().get("items").getAsJsonArray();
                    boolean haveGame = false;
                    for (JsonElement product : storeProducts) {
                        if (product.getAsJsonObject().get("name").getAsString().equals("game_minecraft")) {
                            haveGame = true;
                            break;
                        }
                    }
                    if (!haveGame) {
                        throw new RuntimeException("no game");
                    }

                    JsonElement MinecraftProfile = MicrosoftAccountUtils.getInstance().getProfile(minecraftToken);
                    String uuid = MinecraftProfile.getAsJsonObject().get("id").getAsString();
                    String userName = MinecraftProfile.getAsJsonObject().get("name").getAsString();
                    String refreshToken = tokenMicrosoft.getAsJsonObject().get("refresh_token").getAsString();

                    responseText = String.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                                    "<title>LunarCN Dev</title>\n" +
                                    "<h1>Login successful, now you can close this page</h1>\n" +
                                    "<label>Not this account? Try clean cookies in your browser</label>\n" +
                                    "<h2>Account information</h2>\n" +
                                    "<pre>UserName: %s\n" +
                                    "UUID: %s\n" +
                                    "AccessToken: %s\n" +
                                    "RefreshToken: %s</pre>\n" +
                                    "Client ID: " + CLIENT_ID + "\n" +
                                    "<label>不要将此界面发送给其他人, 这会让你的Minecraft账户被盗!</label><br>" +
                                    "<label>Don't send this interface to others as it may result in your Minecraft account being stolen!</label>"
                            , userName, uuid, minecraftToken, refreshToken);
                    MicrosoftAccount account = new MicrosoftAccount(userName, uuid, minecraftToken, refreshToken, new Date());
                    AccountConfigFile.getInstance().addAccount(account);
                    account.switchAccount();
                    HudManager.getInstance().addNotification(new Notification("AccountManager", "Login successful, username: " + userName, Notification.Type.INFO, 3));
                    if (mc.currentScreen instanceof LoginScreen) {
                        mc.displayGuiScreen(null);
                    }
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw, true));
                    responseText = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                            "<title>LunarCN Dev</title>\n" +
                            "<h1>Login failed</h1>\n" +
                            "Might you don't have a Minecraft account.\n" +
                            "Need help? See <a href=\"https://github.com/cubewhy/LiquidLunar/wiki/Login-problems\">Wiki</a>\n" +
                            "<h2>Stack trace (for dev)</h2>\n" +
                            "<pre>" + sw.getBuffer().toString() + "</pre>";
                }

                try {
                    handleResponse(httpExchange, responseText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
