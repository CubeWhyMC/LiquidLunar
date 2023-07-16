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

    public static void setSession(@NotNull Session session) {
        mc.session = session;
        logger.info("Switch account, PlayerName: " + session.getUsername());
    }

    public static void setSession(@NotNull IAccount account) {
        Session session = new Session(account.getUserName(), account.getUuid(), account.getAccessToken(), "legary");
        setSession(session);
        new SessionEvent(session).callEvent();
    }

    /**
     * 进入服务器
     * @param serverData 服务器数据
     * */
    public static void connectToServer(ServerData serverData) {
        mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, serverData));
    }
}
