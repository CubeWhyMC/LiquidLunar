package org.cubewhy.lunarcn.files.configs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerConfigFile {
    public static String configFilePath = Client.configDir + "/server.json";
    private static ServerConfigFile instance = null;
    private static JsonObject config;

    public static ServerConfigFile getInstance() {
        if (instance == null) {
            instance = new ServerConfigFile();
        }
        return instance;
    }

    public static List<String> getServerAddressesList(ServerEnum serverType) {
        if (!config.isJsonNull()) {
            ArrayList<String> serverAddresses = new ArrayList<>();
            // 添加别名
            for (JsonElement serverAddress : config.get(serverType.toString()).getAsJsonArray()) {
                serverAddresses.add(serverAddress.getAsString());
            }
            // 添加原本的IP
            serverAddresses.add(serverType.ip);
            return serverAddresses;
        } else {
            //TODO 初始化服务器列表
            return new ArrayList<>();
        }
    }

    public void save() {
        try {
            File configFile = new File(configFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
            bufferedWriter.write(config.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        Gson gson = new Gson();
        File configFile = new File(configFilePath);
        BufferedReader bufferedReader;
        boolean successful = false;

        while (!successful) {
            try {
                bufferedReader = new BufferedReader(new FileReader(configFile));
                config = gson.fromJson(bufferedReader, JsonObject.class);
                if (config == null) {
                    config = new JsonObject();
                }
                successful = true;
            } catch (FileNotFoundException e) {

                try {
                    if (!configFile.getParentFile().exists()) {
                        configFile.getParentFile().mkdirs();
                    }
                    configFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public enum ServerEnum {
        HYPIXEL("*.hypixel.net"),
        QBYPIXEL("mc.cubewhy.eu.org");

        public final String ip;

        ServerEnum(String keyInJson) {
            this.ip = keyInJson;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
