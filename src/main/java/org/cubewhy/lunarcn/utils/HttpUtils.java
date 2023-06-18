package org.cubewhy.lunarcn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import okhttp3.*;

import java.io.IOException;

public class HttpUtils {
    public static final OkHttpClient httpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");

    public static Call get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return httpClient.newCall(request);
    }

    public static Call post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return httpClient.newCall(request);
    }

    public static Call post(String url, JsonElement json) throws IOException {
        Gson gson = new Gson();
        String realJson = gson.toJson(json);
        return post(url, realJson);
    }

    public static Call request(Request request) throws IOException {
        return httpClient.newCall(request);
    }
}
