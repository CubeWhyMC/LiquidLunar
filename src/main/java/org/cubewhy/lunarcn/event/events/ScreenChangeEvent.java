package org.cubewhy.lunarcn.event.events;

import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.event.Event;

/**
 * Screen change event
 * Call when call Minecraft.displayGuiScreen
 * */
public class ScreenChangeEvent extends Event {
    /**
     * Changed screen
     * */
    public final GuiScreen screen;

    public ScreenChangeEvent(GuiScreen screen) {
        this.screen = screen;
    }
}
