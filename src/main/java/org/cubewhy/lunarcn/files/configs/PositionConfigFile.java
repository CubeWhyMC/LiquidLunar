package org.cubewhy.lunarcn.files.configs;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.files.ConfigFile;
import org.cubewhy.lunarcn.gui.hud.IRenderer;
import org.cubewhy.lunarcn.gui.hud.ScreenPosition;
import org.cubewhy.lunarcn.module.ModuleDraggable;

public class PositionConfigFile extends ConfigFile {
    private static PositionConfigFile instance = null;
    private static JsonObject config;

    public PositionConfigFile() {
        super(Client.configDir + "/position.json");
        config = getConfig();
    }

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
