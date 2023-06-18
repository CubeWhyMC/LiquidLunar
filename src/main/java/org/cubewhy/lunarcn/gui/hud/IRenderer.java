package org.cubewhy.lunarcn.gui.hud;

public interface IRenderer extends IRenderConfig {
    int getWidth();
    int getHeight();

    void render(ScreenPosition position);

    default void renderDummy(ScreenPosition position) {
        render(position);
    }

    default boolean isEnabled() {
        return true;
    }
}
