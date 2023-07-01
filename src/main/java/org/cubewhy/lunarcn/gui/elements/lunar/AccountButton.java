package org.cubewhy.lunarcn.gui.elements.lunar;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.gui.font.FontType;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class AccountButton {
    public IAccount account;

    public int x, y;
    public int width = 100;
    public int height = 15;

    public int hoverFade = 0;

    public boolean hovered;
    public boolean btnDeleteHeld;

    public AccountButton(IAccount account) {
        this.account = account;
    }

    public void drawButton(int mouseX, int mouseY) {
        hovered = RenderUtils.isHovering(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + this.height);

        if (hovered) {
            if (hoverFade < 40) hoverFade += 10;
        } else {
            if (hoverFade > 0) hoverFade -= 10;
        }

        btnDeleteHeld = RenderUtils.isHovering(mouseX, mouseY, this.x + this.width - 10, this.y + 5, this.x + this.width - 5, this.y + 10);

        if (btnDeleteHeld) {
            hoverFade = 0;
            hovered = false;
            RenderUtils.drawImage(new ResourceLocation("lunarcn/delete-red.png"), this.x + this.width - 10, this.y + 5, 5, 5);
        } else {
            RenderUtils.drawImage(new ResourceLocation("lunarcn/delete.png"), this.x + this.width - 10, this.y + 5, 5, 5);
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        // Text and rect
        RenderUtils.drawRoundedRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, 2, new Color(30, 30, 30, 60));
        RenderUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(255, 255, 255, 38 + hoverFade));

        RenderUtils.drawRoundedOutline(this.x, this.y, this.x + this.width, this.y + this.height, 2, 3, new Color(255, 255, 255, 30).getRGB());

        FontType.TEXT_BOLD.getFont().drawCenteredString(this.account.getUserName(), this.x + (float) this.width / 2 + 0.5F, this.y + (float) (this.height - 4) / 2 + 0.5F, new Color(30, 30, 30, 50).getRGB());
        FontType.TEXT_BOLD.getFont().drawCenteredString(this.account.getUserName(), this.x + (float) this.width / 2, this.y + (float) (this.height - 4) / 2, new Color(190, 195, 189).getRGB());

        // Buttons
        int color = new Color(232, 232, 232, 183).getRGB();
        float f1 = (color >> 24 & 0xFF) / 255.0F;
        float f2 = (color >> 16 & 0xFF) / 255.0F;
        float f3 = (color >> 8 & 0xFF) / 255.0F;
        float f4 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f2, f3, f4, f1);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    public String getUserName() {
        return this.account.getUserName();
    }

    public String getUuid() {
        return this.account.getUuid();
    }

    public String getAccessToken() {
        return this.account.getAccessToken();
    }
}
