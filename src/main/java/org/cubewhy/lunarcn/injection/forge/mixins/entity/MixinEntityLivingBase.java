package org.cubewhy.lunarcn.injection.forge.mixins.entity;

import net.minecraft.entity.EntityLivingBase;
import org.cubewhy.lunarcn.event.events.JumpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {
    @Inject(method = "jump", at = @At("HEAD"))
    public void jump(CallbackInfo ci) {
        new JumpEvent().call();
    }
}
