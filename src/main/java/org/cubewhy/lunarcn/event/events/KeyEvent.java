package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.EventCancelable;

/**
 * Key Event
 * <p>
 * Calls when press a key
 */
public class KeyEvent extends EventCancelable {
    private final int keyCode;

    public KeyEvent(int key) {
        this.keyCode = key;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
