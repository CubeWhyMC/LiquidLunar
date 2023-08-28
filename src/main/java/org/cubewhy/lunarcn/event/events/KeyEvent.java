package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.Event;

/**
 * Key Event
 * <p>
 * Calls when press a key
 */
public class KeyEvent extends Event {
    private final int keyCode;

    public KeyEvent(int key) {
        this.keyCode = key;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
