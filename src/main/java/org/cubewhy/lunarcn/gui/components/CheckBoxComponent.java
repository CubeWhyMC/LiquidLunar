package org.cubewhy.lunarcn.gui.components;

import org.cubewhy.lunarcn.ActionListener;
import org.cubewhy.lunarcn.gui.AbstractComponent;
import org.cubewhy.lunarcn.utils.MouseUtils;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class CheckBoxComponent extends AbstractComponent {
    public final ActionListener actionListener;
    public final int height;
    public final int width;
    public int x;
    public int y;
    public String text;
    public boolean state;

    public boolean hovered = false;

    public CheckBoxComponent(String text, int x, int y, boolean initState, ActionListener actionListener) {
        this.text = text;
        this.state = initState;
        this.actionListener = actionListener;

        this.x = x;
        this.y = y;

        this.width = 10 + fontRenderer.getStringWidth(this.text);
        this.height = fontRenderer.FONT_HEIGHT;
    }

    public CheckBoxComponent(String text, int x, int y, boolean initState) {
        this(text, x, y, initState, null); // No action
    }

    @Override
    public void drawComponent() {
        int mouseX = MouseUtils.getX();
        int mouseY = MouseUtils.getY();

        this.hovered = RenderUtils.isHovering(mouseX, mouseY, x, y, x + 10, y + 10);
        // draw box
        RenderUtils.drawHollowRect(x, y, 10, 10, new Color(255, 255, 255).getRGB());
        if (state) {
            RenderUtils.drawRoundedRect(x, y, 9, 9, 2, new Color(0, 0, 0));
        }
        // draw text
        RenderUtils.drawString(this.text, x + 11, y, true);
    }

    @Override
    public void mouseReleased(int button, int x, int y, boolean offscreen) {
        if (this.hovered && button == 0 && this.actionListener != null) {
            this.actionListener.onAction(); // do action
        }
    }
}
