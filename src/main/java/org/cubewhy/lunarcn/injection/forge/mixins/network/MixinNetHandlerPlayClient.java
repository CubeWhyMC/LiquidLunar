package org.cubewhy.lunarcn.injection.forge.mixins.network;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "handleExplosion", at = @At("RETURN"))
    public void handleExplosion(S27PacketExplosion packetIn, CallbackInfo ci) {
        new PacketEvent(packetIn, PacketEvent.Type.RECEIVE).callEvent();
    }
}
