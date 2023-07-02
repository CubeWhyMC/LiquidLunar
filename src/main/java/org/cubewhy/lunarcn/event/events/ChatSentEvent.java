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
     * 获取发送的消息
     * @return 消息 {@link String}
     * */
    public String getChatMessage() {
        return chatMessage;
    }
}
