package org.cubewhy.lunarcn.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.files.AccountConfigFile;
import org.cubewhy.lunarcn.utils.MinecraftInstance;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public interface IAccount {
    default void switchAccount() {
        MinecraftInstance.setSession(this); // 切换账户
        AccountConfigFile.getInstance().setCurrentAccount(this);
    }

    String getUserName();
    String getUuid();
    String getAccessToken();
    JsonElement toJson();
}
