package org.cubewhy.lunarcn.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class ClientButton extends GuiButton {
    private final boolean drawRect;

    public ClientButton(int buttonId, int x, int y, String buttonText, boolean showRect) {
        super(buttonId, x, y, buttonText);
        drawRect = showRect;
    }

    public ClientButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, buttonText, true);
    }

    public ClientButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean showRect) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        drawRect = showRect;
    }

    public ClientButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this(buttonId, x, y, widthIn, heightIn, buttonText, true);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();

            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
//            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
//            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;
            Color rectColor;

            if (!this.enabled) {
                color = 10526880;
                rectColor = new Color(128, 128, 128, 40);
            } else if (this.hovered) {
                color = 16777120;
                rectColor = new Color(255, 255, 255, 30);
            } else {
                rectColor = new Color(128, 128, 128, 30);
            }

            if (this.drawRect) {
                RenderUtils.drawRoundedRect(this.xPosition, this.yPosition, this.width - 1, this.height - 1, 2, rectColor);
            }

            this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }
}
