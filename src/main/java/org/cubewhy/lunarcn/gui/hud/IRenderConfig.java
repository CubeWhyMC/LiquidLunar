package org.cubewhy.lunarcn.gui.hud;

public interface IRenderConfig {

    void save(ScreenPosition position);

    ScreenPosition load();
}
