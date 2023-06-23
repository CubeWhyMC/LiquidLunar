package org.cubewhy.lunarcn.gui.clickgui.elements;

import java.awt.*;

public class ButtonElement {
    private String displayName;
    private Color color = new Color(255, 255, 255);

    public ButtonElement(String name) {
        this.displayName = name;
    }

    public Color getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }
}
