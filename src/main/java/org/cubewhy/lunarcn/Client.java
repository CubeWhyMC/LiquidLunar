package org.cubewhy.lunarcn;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import io.netty.buffer.Unpooled;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import okhttp3.Call;
import okhttp3.Response;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.files.configs.*;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.cubewhy.lunarcn.gui.altmanager.LoginScreen;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.ipc.DiscordIPC;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.utils.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.util.*;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class Client {

    public static final String splashImage = "lunarcn/splash.png"; // loading screen
    private static final Client instance = new Client(); // instance
    public static final String clientName = "LiquidLunar"; // client name
    public static final String clientVersion = GitUtils.gitInfo.getProperty("git.build.version"); // client version
    public static final String[] clientDev = new String[] {"CubeWhy", "catand", "yuxiangll"}; // Client dev
    public static String clientDir = System.getProperty("user.home") + "/.cubewhy/liquidlunar";
    public static String configDir = clientDir + "/config";
    public static ResourceLocation clientLogo = new ResourceLocation("lunarcn/logo.png");
    public static FeaturedServerData[] featuredServerDataList = new FeaturedServerData[]{
            new FeaturedServerData("QbyPixel", "mc.cubewhy.eu.org")
    };
    public static final String pinnedServerApi = "https://www.lunarcn.top/api/pinned_servers.php?format=json";
    public static long discordAppId = 1072028154564198420L; // Discord api id
    public IPCClient discordIPC;

    private HudManager hudManager;
    public boolean debugMode = false;

    public KeyBinding keyBindClickGui = new KeyBinding("ClickGui", Keyboard.KEY_RSHIFT, clientName);

    public static Client getInstance() {
        return instance;
    }

    public void onInit() {
        SplashProgress.setProgress(1, "Initializing Minecraft");
        Display.setTitle(clientName + " | Initializing");
        EventManager.register(this);

        ClientConfigFile.getInstance().load(); // load config
        ClientConfigFile.getInstance().initClient(); // init client config

        AccountConfigFile.getInstance().load(); // Accounts

        discordIPC = DiscordIPC.startIPC();

        // load pinned servers
        try {
            Call call = HttpUtils.get(pinnedServerApi);
            ArrayList<FeaturedServerData> serverDataList = new ArrayList<>();
            try (Response response = call.execute()) {
                if (response.body() != null) {
                    JsonObject json = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    for (Map.Entry<String, JsonElement> set :
                            json.entrySet()) {
                        serverDataList.add(new FeaturedServerData(set.getKey(), set.getValue().getAsString()));
                    }
                }
            }

            featuredServerDataList = serverDataList.toArray(new FeaturedServerData[]{});
        } catch (Throwable ignored) {

        }
    }

    public void onStart() {
        MinecraftInstance.fontRenderer = mc.fontRendererObj;
        SplashProgress.setProgress(4, "Initializing " + clientName);
        Display.setTitle(clientName + " " + clientVersion + " (" + GitUtils.gitBranch + "/" + GitUtils.gitInfo.getProperty("git.commit.id.abbrev") + ")");
        ModuleConfigFile.getInstance().load(); // module config
        PositionConfigFile.getInstance().load(); // module draggable position config
        ServerConfigFile.getInstance().load();
        hudManager = HudManager.getInstance(); // hud manager
        ModuleManager.getInstance().registerModules(); // register modules

        List<KeyBinding> bindings = new ArrayList<>(Arrays.asList(mc.gameSettings.keyBindings)); // Keybindings
        bindings.add(this.keyBindClickGui);
        mc.gameSettings.keyBindings = bindings.toArray(new KeyBinding[0]);

        new Thread(() -> {
            try {
                MicrosoftAccountUtils.LoginHttpServer.getInstance(); // start login server
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error", "Failed to start the Login Server", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }).start();

        MicrosoftAccountUtils.getInstance().autoRefresh(); // 刷新账户

        IAccount currentAccount = AccountConfigFile.getInstance().getCurrentAccount();
        if (currentAccount == null) {
            mc.displayGuiScreen(new LoginScreen());
        } else {
            currentAccount.switchAccount();
        }
    }

    public void onStop() {
        logger.info(clientName + clientVersion + " stopping!");
    }

    /**
     * @param event TickEvent
     */
    @EventTarget
    public void onTick(TickEvent event) {
        if (keyBindClickGui.isPressed()) {
            hudManager.openConfigScreen(); // click-gui没写，暂时用hud-manager
        }

//        if (!mc.inGameHasFocus) {
//            mc.gameSettings.limitFramerate = ClientConfigFile.getInstance().getConfig().get("unfocusedFps").getAsInt();
//        } else {
//            mc.gameSettings.limitFramerate = (int) 260.0F;
//        }
    }

    /**
     * 客户端包处理, 在进入游戏后发送C17PacketCustomPayload来让服务器认为你在使用LiquidLunar
     *
     * @param event PacketEvent
     */
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (debugMode) {
            ClientUtils.addMessage("[" + clientName + "] " + "Packet "
                    + (event.getType() == PacketEvent.Type.RECEIVE ? "received" : "sent") + ", type: " + event.getPacket().getClass().getSimpleName());
        }
        if (event.getPacket() instanceof S3FPacketCustomPayload) {
            S3FPacketCustomPayload payload = (S3FPacketCustomPayload) event.getPacket();
            if (payload.getChannelName().equals("REGISTER")) {
                PacketBuffer clientPacketInfo = new PacketBuffer(Unpooled.buffer())
                        .writeString("INFO")
                        .writeString("UUID:" + mc.getSession().getPlayerID())
                        .writeString("{  \n" +
                                "   \"version\": \"3.9.25\",\n" +
                                "   \"ccp\": {  \n" +
                                "      \"enabled\": true,\n" +
                                "      \"version\": 2\n" +
                                "   },\n" +
                                "   \"shadow\":{  \n" +
                                "      \"enabled\": true,\n" +
                                "      \"version\": 1\n" +
                                "   },\n" +
                                "   \"addons\": [  \n" +
                                "      {  \n" +
                                "         \"uuid\": \"null\",\n" +
                                "         \"name\": \"null\"\n" +
                                "      }\n" +
                                "   ],\n" +
                                "   \"mods\": [\n" +
                                "      {  \n" +
                                "      }\n" +
                                "   ]\n" +
                                "}");

//                mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(clientName.toLowerCase() + ":" + GitUtils.gitBranch, clientPacketInfo));
            }
        }

        if (event.getPacket() instanceof S3FPacketCustomPayload) {
            S3FPacketCustomPayload packet = (S3FPacketCustomPayload) event.getPacket();
            String channelName = packet.getChannelName();
            if (debugMode) {
                ClientUtils.addMessage("[" + clientName + "] " + "Player sent C17, channelName: " + channelName);
            }
        }
    }
}
