package org.cubewhy.lunarcn;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import io.netty.buffer.Unpooled;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import okhttp3.Call;
import okhttp3.Response;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.cubewhy.lunarcn.event.events.SessionEvent;
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
import java.io.IOException;
import java.util.*;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class Client {

    public static final String splashImage = "lunarcn/splash.png"; // loading screen
    private static final Client instance = new Client(); // instance
    public static final String clientName = "LiquidLunar"; // client name
    public static final String clientVersion = GitUtils.gitInfo.getProperty("git.build.version"); // client version
    public static final String[] clientDev = new String[]{"CubeWhy", "catand", "yuxiangll"}; // Client dev
    public static final String clientId = clientName.toLowerCase() + ":" + GitUtils.gitBranch;
    public static String clientDir = System.getProperty("user.home") + "/.cubewhy/liquidlunar";
    public static String configDir = clientDir + "/config";
    public static ResourceLocation clientLogo = new ResourceLocation("lunarcn/logo.png");
    public static FeaturedServerData[] featuredServerDataList = new FeaturedServerData[]{};
    public static final String metadataApi = "https://api.badlion.top/api/liquid/metadata";
    public static JsonObject metadata;
    public static long discordAppId = 1072028154564198420L; // Discord api id
    public IPCClient discordIPC;

    private HudManager hudManager;
    public boolean debugMode = false;
    public final ArrayList<UUID> sameClient = new ArrayList<>();

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

        try {
            // load metadata
            Call call = HttpUtils.get(metadataApi);
            try (Response response = call.execute()) {
                JsonObject responseJson = new JsonParser().parse(response.body().string()).getAsJsonObject();
                int code = responseJson.get("code").getAsInt();
                if (code == 200) {
                    // request success
                    metadata = responseJson.getAsJsonObject("data");
                } else {
                    // request failed
                    // Throw a error
                    throw new Exception("Fetch metadata failed, code = " + code);
                }
            }
            ArrayList<FeaturedServerData> serverDataList = new ArrayList<>();
            // Load pinned servers
            for (Map.Entry<String, JsonElement> set :
                    metadata.get("pinned-servers").getAsJsonObject().entrySet()) {
                serverDataList.add(new FeaturedServerData(set.getKey(), set.getValue().getAsString()));
            }
            featuredServerDataList = serverDataList.toArray(new FeaturedServerData[]{});
        } catch (Throwable e) {
            // Log errors
            logger.catching(e);
        }
    }

    public void onStart() {
        discordIPC = DiscordIPC.startIPC(); // Init IPC
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

        new SessionEvent(mc.session).call();
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
            hudManager.openConfigScreen();
        }
    }

    /**
     * 客户端包处理, 在进入游戏后发送C17PacketCustomPayload来让服务器认为你在使用LiquidLunar
     *
     * @param event PacketEvent
     */
    @EventTarget
    public void onPacket(PacketEvent event) throws IOException {
        if (debugMode) {
            ClientUtils.addMessage("[" + clientName + "] " + "Packet "
                    + (event.getType() == PacketEvent.Type.RECEIVE ? "received" : "sent") + ", type: " + event.getPacket().getClass().getSimpleName());
        }
        if (event.getPacket() instanceof C17PacketCustomPayload) {
            C17PacketCustomPayload payload = (C17PacketCustomPayload) event.getPacket();
            if (payload.getChannelName().equals("REGISTER")) {
                // sned packet
                PacketBuffer clientPacketInfo = new PacketBuffer(Unpooled.buffer());
                clientPacketInfo.writeString(clientId + ":" + mc.getSession().getPlayerID());
                // TODO hypixel blocked this
//                mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|Brand", clientPacketInfo));
            } else if (payload.getChannelName().equals("MC|Brand")) {
                // handle packet
                String info = new String(payload.data.readByteArray());
                if (info.startsWith(clientId)) {
                    UUID playerUuid = UUID.fromString(JavaUtils.getLastItem(info.split(":")).toString());
                    sameClient.add(playerUuid);
                }
            } else if (payload.getChannelName().equalsIgnoreCase("MC|Brand")) {
                // TEST: LunarClient
                String info = new String(payload.data.readByteArray());
                if (info.startsWith("lunarclient:")) {
                    logger.info("LunarClient: " + info);
                }
            }
        }
    }
}
