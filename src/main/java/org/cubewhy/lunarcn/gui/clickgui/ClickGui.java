package org.cubewhy.lunarcn.gui.clickgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.files.configs.ModuleConfigFile;
import org.cubewhy.lunarcn.gui.VerticalScroller;
import org.cubewhy.lunarcn.gui.elements.SwitchButton;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.module.ModuleCategory;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.utils.MSTimer;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class ClickGui extends GuiScreen {

    private final MSTimer timer = new MSTimer();
    private ModuleCategory currentCategory = null;
    private VerticalScroller scroller;

    private int panelX;
    private int panelY;
    private int panelWidth;
    private int panelHeight;

    private int lastMouseX;
    private int lastMouseY;

    @Override
    public void initGui() {
        EventManager.register(this);

        this.panelWidth = this.width / 2 + 100;
        this.panelHeight = this.height / 2 + 100;
        this.panelX = this.width / 2;
        this.panelY = this.height / 2;

        scroller = new VerticalScroller(this.panelX, this.panelY, 10, this.panelHeight, 0);
        timer.reset();

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();

        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.drawPanel();
        this.drawModules();

        this.scroller.drawScroller();

        super.drawScreen(mouseX, mouseY, partialTicks);

        // Module doc
        for (int i = 0; i < this.buttonList.size(); i++) {
            GuiButton button = this.buttonList.get(i);
            if (button instanceof SwitchButton) {
                SwitchButton button1 = (SwitchButton) button;
                if (button1.isMouseOver()) {
                    this.drawDocument(mouseX, mouseY, button1.getBindModule().getModuleInfo().description());
                }
            }
        }

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void drawModules() {
        int x = this.panelX + 20;
        int y = this.panelY + 100;
        int scrollHeight = 0;

        int i = 0;
        this.buttonList = new ArrayList<>(); // reset button List TODO: try to remove this
        for (Module module : ModuleManager.getInstance().getRegisteredModules()) {
            if (currentCategory != null && module.getModuleInfo().category() != currentCategory || module.getModuleInfo().hideFromClickGui()) {
                continue;
            }

            this.drawModule(module, x, y, i);

            x += fontRenderer.getStringWidth(module.getModuleInfo().name()) + 20;
            if (x > (this.panelX + this.panelWidth) - 20) {
                x = this.panelX + 20;
                y += 40;
                scrollHeight += 40;
            }
            i++;
        }
        scroller.totalHeight = scrollHeight;
    }

    private void drawModule(@NotNull Module module, int x, int y, int id) {
        RenderUtils.drawImage(module.getModuleImage(), x, y, 30, 30);
        this.buttonList.add(new SwitchButton(id, x, y + 30, module));
    }

    private void drawPanel() {
        RenderUtils.drawRoundedRect(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 2, new Color(0, 0, 0, 50));
        this.drawString(fontRenderer, Client.clientName, this.panelX + 80, this.panelY + 30, new Color(255, 255, 255).getRGB());
        RenderUtils.drawImage(Client.clientLogo, this.panelX + 10, this.panelY + 5, 55, 55);
        this.drawHorizontalLine(this.panelX, this.panelX + this.panelWidth, this.panelY + 80, new Color(255, 255, 255).getRGB());

        int x = this.panelX + 30; // category X
        int y = this.panelY + 65; // category Y

        Color color = new Color(255, 255, 255, 40);

        if (currentCategory == null) {
            color = new Color(255, 200, 255);
        }

        drawString(fontRenderer, "ALL", x, y, color.getRGB());
        x += fontRenderer.getStringWidth("ALL") + 50;

        for (ModuleCategory category : ModuleCategory.values()) {
            if (currentCategory == category) {
                color = new Color(255, 200, 255);
            } else {
                color = new Color(255, 255, 255, 40);
            }
            drawString(fontRenderer, category.name().toUpperCase(), x, y, color.getRGB());
            x += fontRenderer.getStringWidth(category.name().toUpperCase()) + 50;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        for (Module module : ModuleManager.getInstance().getRegisteredModules()) {
            ModuleConfigFile.getInstance().setModuleConfig(module); // Auto save module settings
        }
        EventManager.unregister(this);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof SwitchButton && timer.hasTimePassed(1000)) {
            SwitchButton button1 = ((SwitchButton) button);
//            Module module = button1.getBindModule();
            button1.toggle();
            timer.reset(); // Reset timer
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        int MX = lastMouseX - panelX;
        int MY = lastMouseY - panelY;

        if (RenderUtils.isHovering(lastMouseX, lastMouseY, panelX, panelY, panelX + panelWidth, panelY + 20)) {
            this.panelX = mouseX;
            this.panelY = mouseY;
            // TODO 修复窗口位置
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    private void drawDocument(int mouseX, int mouseY, String document) {
        drawRect(mouseX + 5, mouseY, mouseX + fontRenderer.getStringWidth(document) + 10, mouseY + fontRenderer.FONT_HEIGHT, new Color(0, 0, 0, 30).getRGB());
        drawString(fontRenderer, document, mouseX + 10, mouseY, new Color(0, 255, 255, 60).getRGB());
    }
}
