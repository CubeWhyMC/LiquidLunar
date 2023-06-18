package org.cubewhy.lunarcn.injection.forge.mixins.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import org.cubewhy.lunarcn.event.events.ChatSentEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    public void sendChatMessage(String message, CallbackInfo ci) {
        new ChatSentEvent(message).callEvent();
    }
}
