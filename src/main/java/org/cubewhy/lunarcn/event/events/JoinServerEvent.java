package org.cubewhy.lunarcn.event.events;

import net.minecraft.client.multiplayer.ServerData;
import org.cubewhy.lunarcn.event.Event;

public class JoinServerEvent extends Event {
    private final String ip;
    private final int port;

    public JoinServerEvent(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return ip + ":" + port;
    }
}
