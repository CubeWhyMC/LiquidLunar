package org.cubewhy.lunarcn.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MessageUtils {
    public static ChatComponentText buildChatComponent(String message, EnumChatFormatting color) {
        ChatComponentText text = new ChatComponentText(message);
        text.getChatStyle().setColor(color);
        return text;
    }
}
