package org.cubewhy.lunarcn.worker;

import net.minecraft.client.multiplayer.ServerAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;

public class PingWorkerCallable {
    private final SocketAddress address;
    private final String rawAddress;

    public PingWorkerCallable(String address) {
        ServerAddress serveraddress = ServerAddress.fromString(address);
        this.address = new InetSocketAddress(serveraddress.getIP(), serveraddress.getPort());
        this.rawAddress = address;
    }

    public String getRawAddress() {
        return this.rawAddress;
    }

    public SocketAddress getAddress() {
        return this.address;
    }

    public Long getPing() {
        Socket socket = new Socket();

        try {
            long i = System.currentTimeMillis();
            socket.connect(this.address, 1000);
            socket.close();
            return System.currentTimeMillis() - i;
        } catch (IOException var5) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException var4) {
                    logger.catching(var4);
                }
            }
            return -1L;
        }
    }
}
