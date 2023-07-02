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
     * 获取聊天信息
     * @return 聊天内容(@link IChatComponent)
     * */
    public IChatComponent getChatComponent() {
        return chat;
    }

    /**
     * 获取聊天文字(String)
     * @return 聊天文字 {@link String}
     * */

    public String getChatMessage() {
        return getChatComponent().getUnformattedText();
    }
}
