package org.cubewhy.lunarcn.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.UUID;

public class OfflineAccount implements IAccount {
    private final String userName;
    private final String uuid;
    private final String accessToken;

    public OfflineAccount(String userName, String uuid, String accessToken) {
        this.userName = userName;
        this.uuid = uuid;
        this.accessToken = accessToken;
    }

    public OfflineAccount(String userName, String uuid) {
        this(userName, uuid, uuid);
    }

    public OfflineAccount(String userName) {
        this(userName, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("username", userName);
        json.addProperty("uuid", uuid);
        json.addProperty("accessToken", accessToken);
        json.addProperty("type", "Offline");
        return json;
    }
}
