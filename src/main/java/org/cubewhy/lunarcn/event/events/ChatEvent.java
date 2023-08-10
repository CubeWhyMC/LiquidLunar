/**
 * 聊天事件
 * */
package org.cubewhy.lunarcn.event.events;

import net.minecraft.util.IChatComponent;
import org.cubewhy.lunarcn.event.Event;

public class ChatEvent extends Event {
    private final IChatComponent chat;

    public ChatEvent(IChatComponent chatComponent) {
        this.chat = chatComponent;
    }

    /**
     * Get the chat message
     * @return chat message(@link IChatComponent)
     * */
    public IChatComponent getChatComponent() {
        return chat;
    }

    /**
     * Get as a String
     * @return chat String {@link String}
     * */

    public String getChatMessage() {
        return getChatComponent().getUnformattedText();
    }
}
