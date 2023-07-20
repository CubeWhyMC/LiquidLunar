package org.cubewhy.lunarcn.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;

public class BasicConfigFile {
    public final String configFilePath;
    private JsonObject config;

    public BasicConfigFile(String configFilePath) {
        this.configFilePath = configFilePath;
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

    public void initValue(String key, JsonPrimitive value) {
        if (!this.config.has(key)) {
            this.config.add(key, value);
            save();
        }
    }

    public void setValue(String key, JsonPrimitive value) {
        this.config.add(key, value);
        save();
    }

    public JsonElement getValue(String key) {
        return this.config.get(key);
    }

    public void deleteValue(String key) {
        this.config.remove(key);
    }

    public JsonObject getConfig() {
        return config;
    }
}
