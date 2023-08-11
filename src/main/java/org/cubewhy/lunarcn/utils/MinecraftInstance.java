package org.cubewhy.lunarcn.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.event.events.SessionEvent;
import org.jetbrains.annotations.NotNull;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;

public class MinecraftInstance {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fontRenderer;

    /**
     * Login to another account
     * @param session Session object
     * */
    public static void setSession(@NotNull Session session) {
        // change session
        mc.session = session;
        // call session event
        new SessionEvent(session).callEvent();
        logger.info("Switch account, PlayerName: " + session.getUsername());
    }

    /**
     * Login to another Account
     * @param account Account object
     * */
    public static void setSession(@NotNull IAccount account) {
        Session session = new Session(account.getUserName(), account.getUuid(), account.getAccessToken(), "legary");
        setSession(session);
    }

    /**
     * Join server
     * @param serverData data of the server
     * */
    public static void connectToServer(ServerData serverData) {
        mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, serverData));
    }
}
