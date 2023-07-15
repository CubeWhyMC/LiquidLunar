package org.cubewhy.lunarcn.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class PCLWarning extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "PCL2警告", this.width / 2, 10, new Color(255, 255, 255).getRGB());
        drawCenteredString(fontRenderer, "此客户端为完全开源的客户端, 不存在任何后门, 如果你分享了PCL2的错误报告而导致被盗号, 与本客户端无关", this.width / 2, 10 + fontRenderer.FONT_HEIGHT, new Color(255, 255, 255).getRGB());
        drawCenteredString(fontRenderer, "点下面的确认继续游戏", this.width / 2, 10 + fontRenderer.FONT_HEIGHT * 2, new Color(255, 255, 255).getRGB());
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2, "确认"));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(null);
                break;
        }
    }
}
