package org.cubewhy.lunarcn.event.events;

import net.minecraft.network.Packet;
import org.cubewhy.lunarcn.event.Event;
import org.cubewhy.lunarcn.event.EventCancelable;

/**
 * Packet event
 * Call when send a packet
 * */
public class PacketEvent extends EventCancelable {

    private final Packet packet;
    private final Type type;

    public PacketEvent(Packet packet, Type type) {
        this.packet = packet;
        this.type = type;
    }

    /**
     * Get the raw packet
     * @return packet
     * */

    public Packet getPacket() {
        return packet;
    }

    /**
     * Get event type
     * {@link Type}
     * RECEIVE: receive packet
     * SEND: sent packet
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
