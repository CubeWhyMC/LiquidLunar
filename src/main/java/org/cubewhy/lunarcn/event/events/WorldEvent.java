package org.cubewhy.lunarcn.event.events;

import net.minecraft.client.multiplayer.WorldClient;
import org.cubewhy.lunarcn.event.Event;

/**
 * World event
 * Call when change a world
 * */
public class WorldEvent extends Event {
    /**
     * The world changed to
     * */
    public final WorldClient worldClient;

    public WorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
