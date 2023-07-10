package org.cubewhy.lunarcn.gui.clickgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.files.configs.ModuleConfigFile;
import org.cubewhy.lunarcn.gui.notification.Notification;
import org.cubewhy.lunarcn.gui.elements.SwitchButton;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.module.ModuleCategory;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.utils.MSTimer;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ClickGui extends GuiScreen {

    private ModuleCategory currentCategory = null;
    private final MSTimer timer = new MSTimer();

    public ClickGui() {
        EventManager.register(this);
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
        int x = this.width / 4 - 40;
        int y = this.height / 4 + 35;

        int i = 0;
        for (Module module : ModuleManager.getInstance().getRegisteredModules()) {
            if (currentCategory != null && module.getModuleInfo().category() != currentCategory) {
                continue;
            }
            this.drawModule(module, x, y, i);

            x += fontRendererObj.getStringWidth(module.getModuleInfo().name()) + 20;
            if (x > this.width * 3 / 4) {
                x = this.width / 4 - 40;
                y += 40;
            }
            i++;
        }
    }

    private void drawModule(@NotNull Module module, int x, int y, int id) {
        RenderUtils.drawImage(module.getModuleImage(), x, y, 30, 30);
        this.buttonList.add(new SwitchButton(id, x, y + 30, module));
    }

    private void drawPanel() {
        RenderUtils.drawRoundedRect(this.width / 4 - 50, this.height / 4 - 50, this.width / 2 + 100, this.height / 2 + 100, 2, new Color(0, 0, 0, 50));
        this.drawString(fontRendererObj, Client.clientName, this.width / 4 + 20, this.height / 4, new Color(255, 255, 255).getRGB());
        RenderUtils.drawImage(Client.clientLogo, this.width / 4 - 40, this.height / 4 - 40, 55, 55);
        this.drawHorizontalLine(this.width / 4 - 50, this.width / 4 - 50 + this.width / 2 + 100, this.height / 4 + 30, new Color(255, 255, 255).getRGB());

        int x = this.width / 4 - 28;

        Color color = new Color(255, 255, 255, 40);

        if (currentCategory == null) {
            color = new Color(255, 200, 255);
        }

        drawString(fontRendererObj, "ALL", x, this.height / 4 + 15, color.getRGB());
        x += fontRendererObj.getStringWidth("ALL") + 50;

        for (ModuleCategory category : ModuleCategory.values()) {
            if (currentCategory == category) {
                color = new Color(255, 200, 255);
            } else {
                color = new Color(255, 255, 255, 40);
            }
            drawString(fontRendererObj, category.name().toUpperCase(), x, this.height / 4 + 15, color.getRGB());
            x += fontRendererObj.getStringWidth(category.name().toUpperCase()) + 50;
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
            Module module = button1.getBindModule();
            button1.toggle();
            timer.reset(); // Reset timer
        }
    }

    private void drawDocument(int mouseX, int mouseY, String document) {
        drawRect(mouseX + 5, mouseY, mouseX + fontRendererObj.getStringWidth(document) + 10, mouseY + fontRendererObj.FONT_HEIGHT, new Color(0, 0, 0, 30).getRGB());
        drawString(fontRendererObj, document, mouseX + 10, mouseY, new Color(0, 255, 255, 60).getRGB());
    }
}
