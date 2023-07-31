package org.cubewhy.lunarcn.gui.elements;

import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static org.cubewhy.lunarcn.utils.ClientUtils.fontRenderer;
import static org.cubewhy.lunarcn.utils.ClientUtils.logger;

public class CopyButton {
    public static final ResourceLocation imageCopy = new ResourceLocation("lunarcn/icons/copy.png");
    public static boolean hasPressed = false;
    public boolean hovered = false;
    public int x;
    public int y;

    public String text;

    public CopyButton(String text) {
        this.x = fontRenderer.getStringWidth(text) + 5;
        this.text = text;
    }

    public void drawButton(int mouseX, int mouseY, int y) {
        this.y = y;
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
