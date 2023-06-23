package org.cubewhy.lunarcn.files.configs;

import com.google.gson.JsonObject;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.files.ConfigFile;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.utils.ClassUtils;
import org.cubewhy.lunarcn.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModuleConfigFile extends ConfigFile {
    private static ModuleConfigFile instance = null;
    private static JsonObject config;

    private ModuleConfigFile() {
        super(Client.configDir + "/config.json");
        config = getConfig();
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
}
