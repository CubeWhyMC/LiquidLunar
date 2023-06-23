package org.cubewhy.lunarcn.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.files.configs.AccountConfigFile;
import org.cubewhy.lunarcn.utils.MicrosoftAccountUtils;

import java.io.IOException;
import java.util.Date;

public class MicrosoftAccount implements IAccount {
    private String userName;
    private String uuid;
    private String accessToken;
    private final String refreshToken;
    private Date lastFresh;

    public MicrosoftAccount(String userName, String uuid, String accessToken, String refreshToken, Date lastFresh) {
        this.userName = userName;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.lastFresh = lastFresh;
    }

    public MicrosoftAccount(String userName, String uuid, String refreshToken) throws IOException {
        this.userName = userName;
        this.uuid = uuid;
        this.refreshToken = refreshToken;
        refresh();
    }

    /*
     * 使用refreshToken刷新accessToken
     */

    public void refresh() throws IOException {
		 MicrosoftAccount account = MicrosoftAccountUtils.getInstance().loginWithToken(this.refreshToken);
         this.userName = account.userName;
         this.uuid = account.uuid;
         this.accessToken = account.accessToken;
         this.lastFresh = new Date();
        AccountConfigFile.getInstance().addAccount(this);
    }

    /*
     * 将账户信息转化成json 以便存入配置文件中
     */
    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("username", userName);
        json.addProperty("uuid", uuid);
        json.addProperty("accessToken", accessToken);
        json.addProperty("refreshToken", refreshToken);
        json.addProperty("lastRefresh", lastFresh.getTime());
        json.addProperty("type", "Microsoft");
        return json;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public Date getLastFresh() {
        return lastFresh;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
