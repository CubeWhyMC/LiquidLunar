package org.cubewhy.lunarcn.gui.clickgui;

import org.cubewhy.lunarcn.gui.clickgui.elements.ButtonElement;
import org.cubewhy.lunarcn.gui.clickgui.elements.ModuleElement;

import java.awt.*;

public abstract class Style {

    public abstract void drawPanel(final int mouseX, final int mouseY, final Panel panel);

    public abstract void drawDescription(final int mouseX, final int mouseY, final String text);

    public abstract void drawButtonElement(final int mouseX, final int mouseY, final ButtonElement button);

    public abstract void drawModuleElement(final int mouseX, final int mouseY, final ModuleElement moduleElement);

}
