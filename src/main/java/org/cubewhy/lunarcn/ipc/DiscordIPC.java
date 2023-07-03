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

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class DiscordIPC {
    public static @Nullable IPCClient startIPC() {
        try {
            IPCClient client = new IPCClient(Client.discordAppId);
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    RichPresence.Builder builder = new RichPresence.Builder();
                    builder.setState("Playing on " + mc.getSession().getUsername() + (UserUtils.isOffline() ? " (Genuine)" : " (Offline)"))
                            .setDetails("Minecraft 1.8.9")
                            .setStartTimestamp(OffsetDateTime.now())
                            .setLargeImage("large", "LiqLC")
//                            .setSmallImage("ptb-small", "Discord PTB")
                            .setSpectateSecret("look");
                    client.sendRichPresence(builder.build());
                }
            });

            client.connect();
            return client;
        } catch (Throwable ignored) {
            return null;
        }
    }
}
