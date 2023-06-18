package org.cubewhy.lunarcn.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.event.events.SessionEvent;
import org.jetbrains.annotations.NotNull;

public class MinecraftInstance {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void setSession(@NotNull Session session) {
        mc.session = session;
        LoggerUtils.LOGGER.info("Switch account, PlayerName: " + session.getUsername());
    }

    public static void setSession(@NotNull IAccount account) {
        Session session = new Session(account.getUserName(), account.getUuid(), account.getAccessToken(), "legary");
        setSession(session);
        new SessionEvent(session).callEvent();
    }
}
