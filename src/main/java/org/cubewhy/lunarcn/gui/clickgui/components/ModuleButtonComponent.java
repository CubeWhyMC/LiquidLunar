package org.cubewhy.lunarcn.gui.clickgui.components;

import org.cubewhy.lunarcn.gui.clickgui.AbstractComponent;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class ModuleButtonComponent extends AbstractComponent {

    private final int enableColor = new Color(0, 255, 0).getRGB(); // 启用颜色
    private final int disableColor = new Color(255, 0, 0).getRGB(); // 禁用颜色
    private final Module bindModule;

    private boolean hovered;

    public ModuleButtonComponent(Module module, int x, int y, int width, int height) {
        // Initialize the superclass
        super();
        // Set the x and y coordinates
        this.x = x;
        this.y = y;
        // Set the width and height of the component
        this.setWidth(width);
        this.setHeight(height);
        // Set the text of the component
        this.bindModule = module;
    }

    public ModuleButtonComponent(Module module, int x, int y) {
        this(module, x, y, fontRenderer.getStringWidth(module.getModuleInfo().name()), 22);
    }

    public String getText() {
        return this.bindModule.getModuleInfo().name();
    }

    public Module getBindModule() {
        return bindModule;
    }

    @Override
    public void drawComponent() {
        int color;
        if (this.bindModule.getState()) {
            color = enableColor;
        } else {
            color = disableColor;
        }
        RenderUtils.drawString(this.getText(), x, y, color, true);
    }

    @Override
    public boolean mouseReleased(int button, int mouseX, int mouseY, boolean offscreen) {
        if (button == 0) {
            this.hovered = RenderUtils.isHovering(mouseX, mouseY, this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight());
            if (hovered) {
                this.toggle();
            }
        }
        return false;
    }

    private void toggle() {
        this.bindModule.setState(!this.bindModule.getState());
    }
}
