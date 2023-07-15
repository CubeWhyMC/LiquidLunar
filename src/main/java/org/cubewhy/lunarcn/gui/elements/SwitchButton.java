package org.cubewhy.lunarcn.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.cubewhy.lunarcn.module.Module;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class SwitchButton extends GuiButton {

    private final int enableColor = new Color(0, 255, 0).getRGB(); // 启用颜色
    private final int disableColor = new Color(255, 0, 0).getRGB(); // 禁用颜色
    private final Module bindModule;

    private boolean hovered;

    public SwitchButton(int buttonId, int x, int y, Module bindModule) {
        super(buttonId, x, y, bindModule.getModuleInfo().name());
        this.width = fontRenderer.getStringWidth(bindModule.getModuleInfo().name());
        this.height = fontRenderer.FONT_HEIGHT + 10;
        this.bindModule = bindModule;
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        int color;
        if (this.bindModule.getState()) {
            color = enableColor;
        } else {
            color = disableColor;
        }
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        drawString(fontRenderer, this.displayString, this.xPosition, this.yPosition, color);
    }

    public void toggle() {
        bindModule.setState(!bindModule.getState());
    }

    public Module getBindModule() {
        return bindModule;
    }

    @Override
    public boolean isMouseOver() {
        return this.hovered;
    }
}
