package org.cubewhy.lunarcn.injection.forge.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.cubewhy.lunarcn.event.events.RenderEvent;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.module.impl.render.FreeLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Inject(method = "updateCameraAndRender", at = @At("RETURN"))
    public void updateCameraAndRender(CallbackInfo ci) {
        new RenderEvent().call();
    }

    @Redirect(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z", ordinal = 1))
    public boolean redirectMouse(Minecraft instance) {
        // do override mouse, if no need, do vanilla action
        return mc.inGameHasFocus && ((FreeLook) ModuleManager.getInstance().getModule(FreeLook.class)).overrideMouse();
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    public float pitch(Entity instance) {
        return ((FreeLook) ModuleManager.getInstance().getModule(FreeLook.class)).getCameraPitch();
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    public float yaw(Entity instance) {
        return ((FreeLook) ModuleManager.getInstance().getModule(FreeLook.class)).getCameraYaw();
    }
}
