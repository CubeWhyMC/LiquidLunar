package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class MixinGuiContainer extends GuiScreen {
    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        // TODO Modify the size after turning on a font with a letter
        RenderUtils.drawImage(Client.clientLogo, width - 50, height - 25, 25, 25);
    }
}
