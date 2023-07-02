/**
 * 账户切换事件
 * */
package org.cubewhy.lunarcn.event.events;

import net.minecraft.util.Session;
import org.cubewhy.lunarcn.event.Event;

public class SessionEvent extends Event {
    private final Session session;

    public SessionEvent(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
