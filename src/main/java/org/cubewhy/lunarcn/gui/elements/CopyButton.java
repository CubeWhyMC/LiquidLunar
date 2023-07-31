package org.cubewhy.lunarcn.gui.elements;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.utils.ClientUtils;
import org.cubewhy.lunarcn.utils.MSTimer;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static org.cubewhy.lunarcn.utils.ClientUtils.*;

public class CopyButton {
    public static boolean hasPressed = false;

    public static final ResourceLocation imageCopy = new ResourceLocation("lunarcn/icons/copy.png");

    public boolean hovered = false;
    public int x;
    public int y;

    public String text;

    public CopyButton(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public void drawButton(int mouseX, int mouseY) {
        hovered = RenderUtils.isHovering(mouseX, mouseY, this.x, this.y, this.x + 10, this.y + 10);
        // draw image
        RenderUtils.drawImage(imageCopy, this.x, this.y, 10, 10);

        if (Mouse.isButtonDown(0) ^ hasPressed) {
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(this.text), null);
                logger.info("Copied text: " + this.text);
                hasPressed = Mouse.isButtonDown(0);
            } catch (Exception e) {
                logger.catching(e); // copy failed
            }
        }
    }
}
