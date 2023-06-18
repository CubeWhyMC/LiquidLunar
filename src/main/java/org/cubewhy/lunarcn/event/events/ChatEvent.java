package org.cubewhy.lunarcn.event.events;

import net.minecraft.util.IChatComponent;
import org.cubewhy.lunarcn.event.Event;

public class ChatEvent extends Event {
    private final IChatComponent chat;

    public ChatEvent(IChatComponent chatComponent) {
        this.chat = chatComponent;
    }

    public IChatComponent getChatComponent() {
        return chat;
    }

    public java.lang.String getChatMessage() {
        return getChatComponent().getUnformattedText();
    }
}
