package org.cubewhy.lunarcn.event.events;

import net.minecraft.client.multiplayer.WorldClient;
import org.cubewhy.lunarcn.event.Event;

public class WorldEvent extends Event {
    public final WorldClient worldClient;

    public WorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
