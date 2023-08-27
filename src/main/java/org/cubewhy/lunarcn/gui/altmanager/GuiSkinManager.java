package org.cubewhy.lunarcn.gui.altmanager;

import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;
import java.util.ArrayList;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;


public class GuiSkinManager extends GuiScreen {
    private int rightX = 0;
    private int skinX;
    private int skinY;
    public final ArrayList<Skin> skins = new ArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground(); // render bg
        // init right x
        rightX = this.width / 4;
        // render line
        this.drawVerticalLine(rightX, 0, this.height, new Color(255, 255, 255).getRGB());
        // render the skin list
        for (Skin skin : skins) {
            renderItem(skin);
            skinY += 30; // skull height
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void renderItem(Skin skin) {
        this.drawString(fontRenderer, skin.getName(), skinX, skinY, new Color(255, 255, 255).getRGB());

    }
}
