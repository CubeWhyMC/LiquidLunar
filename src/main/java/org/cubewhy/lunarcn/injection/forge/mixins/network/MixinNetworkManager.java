package org.cubewhy.lunarcn.injection.forge.mixins.network;


import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.cubewhy.lunarcn.utils.PacketUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo ci) {
        if (PacketUtils.INSTANCE.getPacketType(packet) != PacketUtils.PacketType.SERVERSIDE) {
            return;
        }

        new PacketEvent(packet, PacketEvent.Type.RECEIVE).callEvent();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
    public void sendPacket(Packet packet, CallbackInfo ci) {
        if (PacketUtils.INSTANCE.getPacketType(packet) != PacketUtils.PacketType.CLIENTSIDE) {
            return;
        }

        new PacketEvent(packet, PacketEvent.Type.SEND).callEvent();
    }
}
