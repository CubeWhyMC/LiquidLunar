package org.cubewhy.lunarcn.files.configs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerConfigFile {
    public static String configFilePath = Client.configDir + "/server.json";
    private static ServerConfigFile instance = null;
    private JsonObject config;

    public static ServerConfigFile getInstance() {
        if (instance == null) {
            instance = new ServerConfigFile();
        }
        return instance;
    }

    public List<String> getServerAddressesList(@NotNull ServerEnum serverType) {
        ArrayList<String> ipList = new ArrayList<>();
        for (JsonElement element : config.get(serverType.toString()).getAsJsonArray()) {
            ipList.add(element.getAsString());
        }
        return ipList;
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

            this.initDefaultConfig();
        }
    }

    /**
     * Set the default config if the config haven't initiated
     */
    private void initDefaultConfig() {
        for (ServerEnum value : ServerEnum.values()) {
            if (!this.config.has(value.toString())) {
                this.config.add(value.toString(), new JsonArray());
            }
        }
    }

    public enum ServerEnum {
        HYPIXEL("*.hypixel.net"),
        QBYPIXEL("mc.cubewhy.eu.org");

        public final String ip;

        ServerEnum(String defaultIp) {
            this.ip = defaultIp;
        }

        @Override
        public @NotNull String toString() {
            return name().toLowerCase();
        }
    }
}
