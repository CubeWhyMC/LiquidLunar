package org.cubewhy.lunarcn.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.Client;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class PCLWarning extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "PCL2 Warning", this.width / 2, 10, new Color(255, 255, 255).getRGB());
        drawCenteredString(fontRenderer, String.format("PCL2 have a bug, hackers can hack your account via the bug, and %s isn't fully support PCL2, use your own risk", Client.clientName), this.width / 2, 10 + fontRenderer.FONT_HEIGHT, new Color(255, 255, 255).getRGB());
        drawCenteredString(fontRenderer, "Click OK to continue playing", this.width / 2, 10 + fontRenderer.FONT_HEIGHT * 2, new Color(255, 255, 255).getRGB());
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2, "OK"));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(@NotNull GuiButton button) {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(null);
                break;
        }
    }
}
