package org.cubewhy.lunarcn.gui.components;

import net.minecraft.client.renderer.GlStateManager;
import org.cubewhy.lunarcn.gui.AbstractComponent;
import org.cubewhy.lunarcn.ActionListener;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class ButtonComponent extends AbstractComponent {
    private boolean hovered = false;
    private int mouseX;
    private int mouseY;

    private final String text;
    private final boolean shadow;

    public final ActionListener actionListener;

    public ButtonComponent(String text, int x, int y, int width, int height, ActionListener actionListener, boolean shadow) {
        this.text = text;
        this.actionListener = actionListener;
        this.x = x;
        this.y = y;
        this.setWidth(width);
        this.setHeight(height);
        this.shadow = shadow;
    }

    public ButtonComponent(String text, int x, int y, int width, int height, ActionListener actionListener) {
        this(text, x, y, width, height, actionListener, false);
    }

    public ButtonComponent(String text, int x, int y, ActionListener actionListener, boolean shadow) {
        this(text, x, y, 200, 20, actionListener, shadow);
    }

    public ButtonComponent(String text, int x, int y, ActionListener actionListener) {
        this(text, x, y, actionListener, false);
    }

    @Override
    public void drawComponent() {
        this.hovered = RenderUtils.isHovering(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + getHeight());
        // text color
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Color colorRect;
        int color;
        if (hovered) {
            color = new Color(238, 220, 10).getRGB();
            colorRect = new Color(60, 60, 60, 60);
        } else {
            color = new Color(255, 255, 255).getRGB();
            colorRect = new Color(0, 0, 0, 60);
        }
        // draw background
        RenderUtils.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 2, colorRect);
        // draw text
        RenderUtils.drawString(this.text, getX(), getY() + fontRenderer.FONT_HEIGHT, color, this.shadow);
    }

    @Override
    public void mouseMove(int x, int y, boolean offscreen) {
        this.mouseX = x;
        this.mouseY = y;
    }

    @Override
    public void mouseReleased(int button, int x, int y, boolean offscreen) {
        if (offscreen) {
            return; // mouse isn't in-game
        }
        if (button == 0 && this.hovered) {
            this.actionListener.onAction(); // do the action
        }
    }
}
