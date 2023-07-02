package org.cubewhy.lunarcn.files.configs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.cubewhy.lunarcn.gui.mainmenu.LunarMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.gui.mainmenu.LiquidLunarMainMenu;
import org.cubewhy.lunarcn.proxy.ProxyManager;

import java.io.*;

public class ClientConfigFile {
    public static String configFilePath = Client.configDir + "/client.json";
    private static ClientConfigFile instance = null;
    private static JsonObject config;

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

        if (!config.has("proxy-address")) {
            setProxyAutomatically();
        } else {
            ProxyManager.getInstance().setAddress(config.get("proxy-address").getAsString());
        }

        save();
    }
    
    public void setProxyAutomatically() {
        config.add("proxy-address", new JsonPrimitive(ProxyManager.getInstance().getProxyAddress()));
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
