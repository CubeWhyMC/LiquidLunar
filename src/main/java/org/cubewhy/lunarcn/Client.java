package org.cubewhy.lunarcn;

import com.jagrosh.discordipc.IPCClient;
import io.netty.buffer.Unpooled;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.files.AccountConfigFile;
import org.cubewhy.lunarcn.files.ClientConfigFile;
import org.cubewhy.lunarcn.files.ModuleConfigFile;
import org.cubewhy.lunarcn.files.PositionConfigFile;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.cubewhy.lunarcn.gui.altmanager.LoginScreen;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.ipc.DiscordIPC;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.utils.ClientUtils;
import org.cubewhy.lunarcn.utils.GitUtils;
import org.cubewhy.lunarcn.utils.LoggerUtils;
import org.cubewhy.lunarcn.utils.MicrosoftAccountUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class Client {

    public static final String splashImage = "lunarcn/splash.png"; // loading screen
    private static final Client instance = new Client(); // instance
    public static String clientName = "LiquidLunar"; // client name
    public static String clientVersion = "1.0.0"; // client version
    public static String clientOwner = "CubeWhy"; // Client dev
    public static String configDir = System.getProperty("user.home") + "/.cubewhy/liquidlunar/config";
    public static ResourceLocation clientLogo = new ResourceLocation("lunarcn/logo.png");
    public static long discordAppId = 1072028154564198420L; // Discord api id
    public IPCClient discordIPC;

    private HudManager hudManager;
    public boolean debugMode = false;

    public KeyBinding keyBindClickGui = new KeyBinding("ClickGui", Keyboard.KEY_RSHIFT, clientName);

    public static Client getInstance() {
        return instance;
    }

    public void onInit() {
        SplashProgress.setProgress(1, "Initializing Minecraft 1.8.9");
        Display.setTitle("Minecraft 1.8.9 | Initializing");
        EventManager.register(this);

        ClientConfigFile.getInstance().load(); // load config
        ClientConfigFile.getInstance().initClient(); // init client config

        AccountConfigFile.getInstance().load(); // Accounts

        discordIPC = DiscordIPC.startIPC();
    }

    public void onStart() {
        SplashProgress.setProgress(4, "Initializing " + clientName);
        Display.setTitle(clientName + " " + clientVersion + " (" + GitUtils.gitBranch + "/" + GitUtils.gitInfo.getProperty("git.commit.id.abbrev") + ")");
        hudManager = HudManager.getInstance(); // hud manager
        ModuleConfigFile.getInstance().load(); // module config
        PositionConfigFile.getInstance().load(); // module draggable position config
        ModuleManager.getInstance().registerModules(); // register modules

        List<KeyBinding> bindings = new ArrayList<>(Arrays.asList(mc.gameSettings.keyBindings)); // Keybindings
        bindings.add(this.keyBindClickGui);
        mc.gameSettings.keyBindings = bindings.toArray(new KeyBinding[0]);

        new Thread(() -> {
            try {
                MicrosoftAccountUtils.LoginHttpServer.getInstance(); // start login server
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        IAccount currentAccount = AccountConfigFile.getInstance().getCurrentAccount();
        if (currentAccount == null) {
            mc.displayGuiScreen(new LoginScreen());
        } else {
            currentAccount.switchAccount();
        }

        // HttpRequest.get("https://api.lunarcn.top/liquid/start.php?uuid=" + mc.getSession().getPlayerID()).userAgent("liquidlunar").ok();
    }

    public void onStop() {
        LoggerUtils.LOGGER.info(clientName + clientVersion + " stopping!");
    }

    public void loadFeaturedServers(@NotNull ServerList base) {
        base.addServerData(new FeaturedServerData("QbyPixel", "mc.cubewhy.eu.org"));
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

        if (event.getPacket() instanceof C17PacketCustomPayload) {
            C17PacketCustomPayload packet = (C17PacketCustomPayload) event.getPacket();
            String channelName = packet.getChannelName();
            if (debugMode) {
                ClientUtils.addMessage("[" + clientName + "] " + "Player sent C17, channelName: " + channelName);
            }
        }
    }

//    @EventTarget
//    public void onChat(ChatSentEvent event) {
//        String msg = event.getChatMessage();
//        if (msg.contains(".login")) {
//            MicrosoftAccountUtils.getInstance().loginWithBrowser();
//        }
//    }
}
