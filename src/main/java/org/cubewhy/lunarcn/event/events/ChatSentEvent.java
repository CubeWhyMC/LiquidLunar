package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.Event;

public class ChatSentEvent extends Event {
    private final String chatMessage;

    public ChatSentEvent(String chat) {
        this.chatMessage = chat;
    }

    public String getChatMessage() {
        return chatMessage;
    }
}
