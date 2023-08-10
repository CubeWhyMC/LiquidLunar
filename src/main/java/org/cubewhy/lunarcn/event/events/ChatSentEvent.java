/**
 * 聊天发送事件
 * */
package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.Event;

public class ChatSentEvent extends Event {
    private final String chatMessage;

    public ChatSentEvent(String chat) {
        this.chatMessage = chat;
    }

    /**
     * Get sent message
     * @return message String {@link String}
     * */
    public String getChatMessage() {
        return chatMessage;
    }
}
