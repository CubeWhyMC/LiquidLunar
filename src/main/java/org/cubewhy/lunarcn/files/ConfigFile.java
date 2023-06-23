package org.cubewhy.lunarcn.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class ConfigFile {
    public String configFilePath;
    private static JsonObject config;

    public ConfigFile(String configFilePath) {
        this.configFilePath = configFilePath;
        load();
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

    public JsonObject getConfig() {
        return config;
    }
}
