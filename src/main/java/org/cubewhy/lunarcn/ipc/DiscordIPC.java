package org.cubewhy.lunarcn.ipc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.utils.UserUtils;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.time.OffsetDateTime;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class DiscordIPC {
    public static @Nullable IPCClient startIPC() {
        try {
            IPCClient client = new IPCClient(Client.discordAppId);
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    RichPresence.Builder builder = new RichPresence.Builder();
                    builder.setState("Loading client")
                            .setDetails(Client.clientName)
                            .setStartTimestamp(OffsetDateTime.now())
                            .setLargeImage("logo", "ICON")
                            .setSpectateSecret("Join");
                    client.sendRichPresence(builder.build());
                }
            });

            client.connect();
            return client;
        } catch (Throwable e) {
            logger.catching(e);
            return null;
        }
    }
}
