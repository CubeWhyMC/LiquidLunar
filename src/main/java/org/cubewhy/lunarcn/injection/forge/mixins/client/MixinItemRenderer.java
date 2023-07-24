package org.cubewhy.lunarcn.injection.forge.mixins.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.module.impl.render.OverlaySettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Redirect(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    public void renderFireInFirstPerson(float x, float y, float z) {
        double fireHeight = ((OverlaySettings) ModuleManager.getInstance().getModule(OverlaySettings.class)).getFireHeight().getValue();
        GlStateManager.translate(x, y - fireHeight / 100F, z);
    }
}
