/**
 * 数据包事件
 * */
package org.cubewhy.lunarcn.event.events;

import net.minecraft.network.Packet;
import org.cubewhy.lunarcn.event.Event;

public class PacketEvent extends Event {

    private final Packet packet;
    private final Type type;

    public PacketEvent(Packet packet, Type type) {
        this.packet = packet;
        this.type = type;
    }

    /**
     * 获取原始数据包
     * @return packet
     * */

    public Packet getPacket() {
        return packet;
    }

    /**
     * 获取类型
     * {@link Type}
     * RECEIVE 是接受数据包
     * SEND 是发送数据包
     * @return type
     * */
    public Type getType() {
        return type;
    }

    public enum Type {
        RECEIVE,
        SEND
    }
}
