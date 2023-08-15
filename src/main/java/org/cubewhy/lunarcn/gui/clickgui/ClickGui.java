package org.cubewhy.lunarcn.gui.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.files.configs.ModuleConfigFile;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.utils.MinecraftInstance;

public abstract class ClickGui extends GuiScreen {
    private int panelX;
    private int panelY;
    private int panelWidth;
    private int panelHeight;
    protected final Minecraft mc = MinecraftInstance.mc; // the Minecraft instance

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Draw the main panel
     * */
    public abstract void drawPanel(int mouseX, int mouseY);

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground(); // render the bg
        this.drawPanel(mouseX, mouseY); // render the panel
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        for (Module module : ModuleManager.getInstance().getRegisteredModules()) {
            ModuleConfigFile.getInstance().setModuleConfig(module); // Auto save module settings
        }
        EventManager.unregister(this);
    }
}
