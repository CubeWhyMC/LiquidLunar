package org.cubewhy.lunarcn.gui.mainmenu.lunar.ui.buttons;

import net.minecraft.client.renderer.GlStateManager;
import org.cubewhy.lunarcn.account.IAccount;
import org.cubewhy.lunarcn.gui.mainmenu.lunar.font.FontUtil;
import org.cubewhy.lunarcn.gui.mainmenu.lunar.util.ClientGuiUtils;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

public class AccountButton {
    public IAccount account;

    public int x, y;
    public int width = 100;
    public int height = 15;

    public int hoverFade = 0;

    public boolean hovered;

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

        GlStateManager.color(1.0F, 1.0F, 1.0F);

        // Text and rect
        ClientGuiUtils.drawRoundedRect(this.x - 1, this.y - 1, this.width + 2, this.height + 2, 2, new Color(30, 30, 30, 60));
        ClientGuiUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 2, new Color(255, 255, 255, 38 + hoverFade));

        ClientGuiUtils.drawRoundedOutline(this.x, this.y, this.x + this.width, this.y + this.height, 2, 3, new Color(255, 255, 255, 30).getRGB());

        FontUtil.TEXT_BOLD.getFont().drawCenteredString(this.account.getUserName(), this.x + (float) this.width / 2 + 0.5F, this.y + (float) (this.height - 4) / 2 + 0.5F, new Color(30, 30, 30, 50).getRGB());
        FontUtil.TEXT_BOLD.getFont().drawCenteredString(this.account.getUserName(), this.x + (float) this.width / 2, this.y + (float) (this.height - 4) / 2, new Color(190, 195, 189).getRGB());

        // Buttons

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
