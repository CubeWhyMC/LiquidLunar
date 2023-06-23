package org.cubewhy.lunarcn.account;

import com.google.gson.JsonElement;
import org.cubewhy.lunarcn.files.configs.AccountConfigFile;
import org.cubewhy.lunarcn.utils.MinecraftInstance;

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
