package org.cubewhy.lunarcn.files.configs;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.cubewhy.lunarcn.files.ConfigFile;
import org.cubewhy.lunarcn.gui.mainmenu.lunar.ui.LunarMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.gui.mainmenu.LiquidLunarMainMenu;

public class ClientConfigFile extends ConfigFile {
    private static ClientConfigFile instance = null;

    private final JsonObject config;

    public ClientConfigFile() {
        super(Client.configDir + "/client.json");
        config = getConfig();
    }

    public static ClientConfigFile getInstance() {
        if (instance == null) {
            instance = new ClientConfigFile();
        }
        return instance;
    }

    public void initClient() {

        if (!config.has("unfocusedFps")) {
            config.add("unfocusedFps", new JsonPrimitive(15));
        }

        if (!config.has("menu-style")) {
            config.add("menu-style", new JsonPrimitive("lunar"));
        }

        save();
    }

    public GuiMainMenu getMenuStyle() {

        String style = config.get("menu-style").getAsString();
        GuiMainMenu menu;
        switch (style) {
            case "liquid-lunar":
                menu = new LiquidLunarMainMenu();
                break;
            case "lunar":
                menu = new LunarMainMenu();
                break;
            case "minecraft":
            default:
                menu = new GuiMainMenu();
                break;
        }

        return menu;
    }
}
