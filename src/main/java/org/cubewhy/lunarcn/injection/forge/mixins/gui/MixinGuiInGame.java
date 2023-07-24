package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.cubewhy.lunarcn.event.events.Render2DEvent;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.module.impl.render.OverlaySettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiInGame {
    @Inject(method = "renderTooltip", at = @At("RETURN"))
    protected void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        new Render2DEvent().callEvent();
    }

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    protected void renderPumpkinOverlay(ScaledResolution scaledRes, CallbackInfo ci) {
        OverlaySettings overlayModule = (OverlaySettings) ModuleManager.getInstance().getModule(OverlaySettings.class);
        if (!overlayModule.getPumpkin().getValue()) {
            ci.cancel();
        }
    }
}
