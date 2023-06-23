package org.cubewhy.lunarcn.files.configs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.utils.ClassUtils;
import org.cubewhy.lunarcn.value.Value;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;

public class ModuleConfigFile {
    public static String configJsonPath = Client.configDir + "/config.json";
    private static ModuleConfigFile instance = null;
    private static JsonObject config;

    private ModuleConfigFile() {
    }

    public static ModuleConfigFile getInstance() {
        if (instance == null) {
            instance = new ModuleConfigFile();
        }
        return instance;
    }

    public void setModuleConfig(@NotNull Module module) {
        String name = module.getModuleInfo().name();
        boolean state = module.getState();
        List<Value<?>> moduleVarList = ClassUtils.INSTANCE.getValues(module.getClass(), module);

        JsonObject newModule = new JsonObject();
        newModule.addProperty("state", state);
        for (Value value : moduleVarList) {
            newModule.add(value.getName(), value.toJson());
        }

        config.add(name, newModule);

		save();
    }

    public void initModule(@NotNull Module module) {
        String name = module.getModuleInfo().name();
        List<Value<?>> moduleVarList = ClassUtils.INSTANCE.getValues(module.getClass(), module);

        if (!config.has(name)) {
			setModuleConfig(module);
        } else {

            module.setState(config.getAsJsonObject(name).get("state").getAsBoolean());

            for (Value value : moduleVarList) {
                value.fromJson(config.getAsJsonObject(name).get(value.getName()));
            }
        }

        save();
    }

    public void save() {
        try {
            File configFile = new File(configJsonPath);
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
        File configFile = new File(configJsonPath);
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
