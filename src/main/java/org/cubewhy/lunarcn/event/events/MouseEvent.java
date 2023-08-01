package org.cubewhy.lunarcn.event.events;

import org.cubewhy.lunarcn.event.Event;

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
