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

    public Packet getPacket() {
        return packet;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        RECEIVE,
        SEND
    }
}
