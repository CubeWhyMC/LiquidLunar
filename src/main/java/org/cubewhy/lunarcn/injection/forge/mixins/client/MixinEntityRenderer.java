package org.cubewhy.lunarcn.injection.forge.mixins.client;

import net.minecraft.client.renderer.EntityRenderer;
import org.cubewhy.lunarcn.event.events.RenderEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Inject(method = "updateCameraAndRender", at = @At("RETURN"))
    public void updateCameraAndRender(CallbackInfo ci) {
        new RenderEvent().callEvent();
    }
}
