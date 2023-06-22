package org.cubewhy.lunarcn.injection.forge.mixins.gui;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import org.cubewhy.lunarcn.FeaturedServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer {
    @Shadow
    private ServerList savedServerList;

    @Shadow
    private GuiButton btnEditServer;
    @Shadow
    private GuiButton btnDeleteServer;

    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        mc.fontRendererObj.drawString("You are playing on " + mc.getSession().getUsername(), 20, 20, new Color(255, 255, 255).getRGB());
    }

    /**
     * @author CubeWhy
     * @reason canMoveUp
     */
    @Overwrite
    public boolean func_175392_a(ServerListEntryNormal serverListEntryNormal, int index) {
        int serverCount = this.savedServerList.countServers();
        if (serverCount == 1) {
            return index > 0;
        }
        if (index - 1 >= 0 && this.savedServerList.getServerData(index - 1) instanceof FeaturedServerData) {
            return false;
        }
        return index > 0;
    }

    /**
     * @author CubeWhy
     * @reason canMoveDown
     */
    @Overwrite
    public boolean func_175394_b(ServerListEntryNormal serverListEntryNormal, int index) {
        if (this.savedServerList.getServerData(index) instanceof FeaturedServerData) {
            return false;
        }
        return index < this.savedServerList.countServers() - 1;
    }

    /**
     * @author CubeWhy
     * @reason selectServer
     */
    @Inject(method = "selectServer", at = @At(value = "RETURN"))
    public void selectServer(int index, CallbackInfo ci) {
        if (index == -1) {
            return;
        }
        if (savedServerList.getServerData(index) instanceof FeaturedServerData) {
            this.btnEditServer.enabled = false;
            this.btnDeleteServer.enabled = false;
        }
    }
}
