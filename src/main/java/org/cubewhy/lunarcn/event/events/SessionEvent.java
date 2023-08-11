package org.cubewhy.lunarcn.event.events;

import net.minecraft.util.Session;
import org.cubewhy.lunarcn.event.Event;

/**
 * Session event
 * Call when change a session
 * */
public class SessionEvent extends Event {
    private final Session session;

    public SessionEvent(Session session) {
        this.session = session;
    }

    /**
     * Get the session changed to
     * @return session
     * */
    public Session getSession() {
        return session;
    }
}
