package org.cubewhy.lunarcn.utils;

import com.google.gson.JsonObject;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cubewhy.lunarcn.Client;

public class ClientUtils extends MinecraftInstance {
    public static final Logger logger = LogManager.getLogger(Client.clientName);


    public static void addMessage(java.lang.String message) {
        if (mc.thePlayer == null) {
            logger.info("(MCChat)" + message);
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }

    public static ChatComponentText buildChatComponent(String message, EnumChatFormatting color) {
        ChatComponentText text = new ChatComponentText(message);
        text.getChatStyle().setColor(color);
        return text;
    }
}
