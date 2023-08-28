package org.cubewhy.lunarcn.event.events;

import net.minecraft.entity.Entity;
import org.cubewhy.lunarcn.event.Event;
import org.cubewhy.lunarcn.event.EventCancelable;

/**
 * Attack event
 * Call when you're attacking a target
 * */
public class AttackEvent extends EventCancelable {
    private final Entity target;

    public AttackEvent(Entity target) {
        this.target = target;
    }

    /**
     * Get attack target
     * @return target
     * */
    public Entity getTarget() {
        return target;
    }
}
