package org.cubewhy.lunarcn.gui.hud;

public interface IRenderer extends IRenderConfig {
    /**
     * 获取宽
     * */
    int getWidth();

    /**
     * 获取高
     * */
    int getHeight();

    /**
     * 绘制组件
     * @param position 坐标
     */

    void render(ScreenPosition position);


    /**
     * 在{@link HudDesigner}中绘制的样子
     * 也就是预览
     * */
    default void renderDummy(ScreenPosition position) {
        render(position);
    }

    /**
     * 获取是否渲染
     * 如果要切换状态请使用{@link org.cubewhy.lunarcn.module.Module}中的state变量
     * */
    default boolean isEnabled() {
        return true;
    }
}
