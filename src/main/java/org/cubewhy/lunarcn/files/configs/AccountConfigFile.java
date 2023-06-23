package org.cubewhy.lunarcn.files.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.account.OfflineAccount;
import org.cubewhy.lunarcn.files.ConfigFile;
import org.cubewhy.lunarcn.utils.AccountUtils;

import java.util.*;

public class AccountConfigFile extends ConfigFile {
    private static AccountConfigFile instance = null;
    private static JsonObject config;

    public AccountConfigFile() {
        super(Client.configDir + "/accounts.json");
        config = getConfig();
    }

    public static AccountConfigFile getInstance() {
        if (instance == null) {
            instance = new AccountConfigFile();
        }
        return instance;
    }

    public void addAccount(IAccount account) {
        JsonElement json = account.toJson();
        if (account instanceof OfflineAccount) {
            for (Map.Entry<String, JsonElement> entry :
                    config.entrySet()) {
                if (config.get(entry.getKey()).getAsJsonObject().get("username").getAsString().equals(account.getUserName())) {
                    return;
                }
            }
        }
        config.add(account.getUuid(), json);
        save();
    }

    public void setCurrentAccount(IAccount account) {
        String uuid = account.getUuid();
        config.addProperty("current", uuid);
        save();
    }

    public void removeAccount(IAccount account) {
        config.remove(account.getUuid());
        save();
    }

    public IAccount getCurrentAccount() {
        if (!config.has("current")) {
            return null;
        }
        String current = config.get("current").getAsString();
        if (!config.has(current)) {
            return null;
        }

        return AccountUtils.fromJson(config.get(current).getAsJsonObject());
    }


    public IAccount[] getAccounts() {
        List<IAccount> accounts = new ArrayList<>(Collections.emptyList());
        for (Map.Entry<String, JsonElement> account :
                config.entrySet()) {
            String uuid = account.getKey();
            if (!Objects.equals(uuid, "current")) {
                accounts.add(AccountUtils.fromJson(config.get(uuid).getAsJsonObject())); // TODO fix this
            }
        }
        return accounts.toArray(new IAccount[0]);
    }
}
