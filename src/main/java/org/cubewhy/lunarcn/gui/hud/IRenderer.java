package org.cubewhy.lunarcn.gui.hud;

public interface IRenderer extends IRenderConfig {
    /**
     * Get the width of the component
     * @return width
     * */
    int getWidth();

    /**
     * Get the height of the component
     * @return height
     * */
    int getHeight();

    /**
     * Render in hub
     * @param position where the component render
     */

    void render(ScreenPosition position);


    /**
     * Render in {@link HudDesigner}
     * */
    default void renderDummy(ScreenPosition position) {
        render(position);
    }

    /**
     * Control the renderer on the hub
     * */
    default boolean isRendererEnabled() { return true;}
}
