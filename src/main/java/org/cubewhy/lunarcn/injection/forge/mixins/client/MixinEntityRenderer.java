package org.cubewhy.lunarcn.injection.forge.mixins.client;

import net.minecraft.client.renderer.EntityRenderer;
import org.cubewhy.lunarcn.event.events.Render3DEvent;
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
    @Inject(method = "renderWorldPass",at =@At("RETURN"))
    public void renderWorldPass(int p_renderWorldPass_1_, float p_renderWorldPass_2_, long p_renderWorldPass_3_, CallbackInfo ci) {
        new Render3DEvent(p_renderWorldPass_2_).callEvent();
    }
}
