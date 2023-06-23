package org.cubewhy.lunarcn.files.configs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.gui.hud.IRenderer;
import org.cubewhy.lunarcn.gui.hud.ScreenPosition;
import org.cubewhy.lunarcn.module.ModuleDraggable;

import java.io.*;

public class PositionConfigFile {
    public static String configFilePath = Client.configDir + "/position.json";
    private static PositionConfigFile instance = null;
    private static JsonObject config;

    public static PositionConfigFile getInstance() {
        if (instance == null) {
            instance = new PositionConfigFile();
        }
        return instance;
    }

    public void initModule(ModuleDraggable module) {
        String name = module.getModuleInfo().name();

        if (!config.has(name)) {

            JsonObject newModule = new JsonObject();
            ScreenPosition pos = module.load();
            newModule.add("x", new JsonPrimitive(pos.getRelativeX()));
            newModule.add("y", new JsonPrimitive(pos.getRelativeY()));

            config.add(name, newModule);
        } else {
            module.save(ScreenPosition.fromRelativePosition(config.getAsJsonObject(name).get("x").getAsDouble(), config.getAsJsonObject(name).get("y").getAsDouble()));
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

    public JsonObject getConfig() {
        return config;
    }

    public void saveModule(IRenderer renderer, ScreenPosition position) {
        ModuleDraggable module = (ModuleDraggable) renderer;
        JsonObject newModule = new JsonObject();
        ScreenPosition pos = module.load();
        newModule.add("x", new JsonPrimitive(pos.getRelativeX()));
        newModule.add("y", new JsonPrimitive(pos.getRelativeY()));

        config.add(module.getModuleInfo().name(), newModule);

        save();
    }
}
