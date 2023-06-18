package org.cubewhy.lunarcn.utils;

import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.account.MicrosoftAccount;
import org.cubewhy.lunarcn.account.OfflineAccount;

import java.util.Date;

public class AccountUtils {
    public static IAccount fromJson(JsonObject json) {
        String userName = json.get("username").getAsString();
        String uuid = json.get("uuid").getAsString();
        String accessToken = json.get("accessToken").getAsString();
        String type = json.get("type").getAsString();

        IAccount account;

        switch (type) {
            case "Microsoft":
                String refreshToken = json.get("refreshToken").getAsString();
                long freshDate = json.get("lastRefresh").getAsLong();
                account = new MicrosoftAccount(userName, uuid, accessToken, refreshToken, new Date(freshDate));
                break;
            case "Offline":
                account = new OfflineAccount(userName, uuid, accessToken);
                break;
            default:
                account = null;
                break;
        }

        return account;
    }
}
