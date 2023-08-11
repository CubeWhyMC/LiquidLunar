/**
 * 加入服务器事件
 * */
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

    /**
     * Get server IP
     * @return IP
     * */
    public String getIp() {
        return ip;
    }


    /**
     * Get server port
     * @return port
     * */
    public int getPort() {
        return port;
    }

    /**
     * Get server address
     * @return ip:port
     * */
    public String getAddress() {
        return ip + ":" + port;
    }
}
