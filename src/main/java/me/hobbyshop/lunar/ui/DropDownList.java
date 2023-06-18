package me.hobbyshop.lunar.ui;

import me.hobbyshop.lunar.util.ClientGuiUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.cubewhy.lunarcn.gui.elements.LunarButton;

import java.awt.*;

public class DropDownList {
    protected LunarButton[] items;
    protected int x, y;
    protected int width, height;

    private LunarButton currentItem;

    public int hoverFade = 0;

    public DropDownList(LunarButton[] items, int x, int y) {
        this.items = items;
        this.x = x;
        this.y = y;

        this.width = 132;
        this.height = 11;

        this.currentItem = items[0];
    }

    public void drawList(int mouseX, int mouseY) {
        boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        if (hovered) {
            if (hoverFade < 40) hoverFade += 10;
        } else {
            if (hoverFade > 0) hoverFade -= 10;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        ClientGuiUtils.drawRoundedRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, 2, new Color(30, 30, 30, 60));
        ClientGuiUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(255, 255, 255, 38 + hoverFade));

        ClientGuiUtils.drawRoundedOutline(this.x, this.y, this.x + this.width, this.y + this.height, 2, 3, new Color(255, 255, 255, 30).getRGB());

        if (hovered) {
            // TODO 展开列表
            for (int i = 0; i < items.length; i++) {
                LunarButton item = items[i];
                item.x = this.x;
                item.y = this.y += i * item.height + 5;
                item.drawButton(mouseX, mouseY);
            }
        } else {
            currentItem.drawButton(mouseX, mouseY);
        }
    }
}
