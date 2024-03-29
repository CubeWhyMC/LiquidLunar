package org.cubewhy.lunarcn.gui;


public abstract class AbstractComponent {
    protected int x;
    protected int y;
    private int width;
    private int height;
    private boolean sizeChanged;

    public AbstractComponent() {
    }

    public abstract void drawComponent();

    public int getEventPriority() {
        return 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (this.x != x) setSizeChanged(true);

        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (this.y != y) setSizeChanged(true);

        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (this.width != width) setSizeChanged(true);

        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (this.height != height) setSizeChanged(true);

        this.height = height;
    }

    public boolean isSizeChanged() {
        return sizeChanged;
    }

    public void setSizeChanged(boolean sizeChanged) {
        this.sizeChanged = sizeChanged;
    }

    public boolean keyPressed(int key, char c) {
        return false;
    }

    public void mouseReleased(int button, int x, int y, boolean offscreen) {
    }

    public void mouseMove(int x, int y, boolean offscreen) {
    }

    public boolean mousePressed(int button, int x, int y, boolean offscreen) {
        return false;
    }

    public boolean mouseWheel(int change) {
        return false;
    }

}
