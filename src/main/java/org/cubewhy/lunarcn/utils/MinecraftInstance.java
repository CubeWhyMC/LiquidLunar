package org.cubewhy.lunarcn.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.cubewhy.lunarcn.account.IAccount;

public class MinecraftInstance {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void setSession(Session session) {
        mc.session = session;
        LoggerUtils.LOGGER.info("Switch account, PlayerName: " + session.getUsername());
    }

    public static void setSession(IAccount account) {
        Session session1 = new Session(account.getUserName(), account.getUuid(), account.getAccessToken(), "legary");
        setSession(session1);
    }
}
