package org.cubewhy.lunarcn.utils;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class UserUtils {
    /**
     * 获取当前登陆的账户是否为离线
     * @return boolean
     */
    public static boolean isOffline() {
        String token = mc.getSession().getToken();
        System.out.println(token);
        return token.length() >= 50;
    }
}
