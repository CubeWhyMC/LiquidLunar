package org.cubewhy.lunarcn.event.events;

import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.event.Event;

public class ScreenChangeEvent extends Event {
    public final GuiScreen screen;

    public ScreenChangeEvent(GuiScreen screen) {
        this.screen = screen;
    }
}
