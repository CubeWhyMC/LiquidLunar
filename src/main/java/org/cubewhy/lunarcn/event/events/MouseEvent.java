package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.Event;

/**
 * Mouse event
 * Call when you're clicking your mouse
 * */
public class MouseEvent extends Event {

    public final MouseButton button;

    public MouseEvent(MouseButton button) {
        this.button = button;

    }
    public enum MouseButton {
        LEFT,
        MIDDLE,
        RIGHT
    }
}
