package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.cubewhy.lunarcn.event.events.JoinServerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {
    @Inject(method = "connect", at = @At("RETURN"))
    private void connect(String ip, int port, CallbackInfo ci) {
        new JoinServerEvent(ip, port).call(); // 加入服务器事件
    }
}
