package org.cubewhy.lunarcn.gui.clickgui;

import org.cubewhy.lunarcn.gui.hud.ScreenPosition;
import org.jetbrains.annotations.NotNull;

public class Element {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;

    public void drawScreen(int mouseX, int mouseY) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(@NotNull ScreenPosition position) {
        this.x = position.getAbsoluteX();
        this.y = position.getAbsoluteY();
    }

    public ScreenPosition getPosition() {
        return ScreenPosition.fromAbsolutePosition(this.x, this.y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
