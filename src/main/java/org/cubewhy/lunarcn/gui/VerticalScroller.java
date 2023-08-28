package org.cubewhy.lunarcn.gui;

import net.minecraft.client.gui.Gui;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class VerticalScroller extends Gui {
    public int x;
    public final int y;
    public final int width;
    public final int height;
    public int totalHeight;
    public double current = 0;
    public boolean hovered = false;

    public VerticalScroller(int x, int y, int width, int height, int totalHeight) {
        this.x = x;
        this.y = y;
        this.totalHeight = totalHeight;
        this.width = width;
        this.height = height;
    }

    public void drawScroller() {
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        int status = Mouse.getDWheel();

        this.hovered = RenderUtils.isHovering(mouseX, mouseY, x, y, x + width, y + height);
        RenderUtils.drawRoundedRect(x, y, width, height, 2, new Color(0, 0, 0, 40));
        if (hovered || status != 0) {
            // progress
            RenderUtils.drawRoundedRect(x, (int) (y + current * height), width, height / totalHeight, 2, new Color(0, 90, 0, 50));
        }

        if (status > 0 && current < 1) {
            // UP
            current += 0.01;
        } else if (status < 0 && current > 0) {
            // down
            current -= 0.01;
        }
    }
}
