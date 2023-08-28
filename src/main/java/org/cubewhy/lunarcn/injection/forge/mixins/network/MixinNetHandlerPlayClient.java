package org.cubewhy.lunarcn.injection.forge.mixins.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.world.Explosion;
import org.cubewhy.lunarcn.event.events.PacketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Shadow
    private Minecraft gameController;

    /**
     * @author CubeWhy
     * @reason packetEvent
     */
    @Overwrite
    public void handleExplosion(S27PacketExplosion packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, (NetHandlerPlayClient) (Object) this, this.gameController);
        Explosion explosion = new Explosion(this.gameController.theWorld, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
        explosion.doExplosionB(true);
        // convert it to velocity packet
        // ONLY when it's a valid explosion (in affected range)
        if (!(Math.abs(packetIn.func_149149_c() * 8000.0) < 0.0001 && Math.abs(packetIn.func_149144_d() * 8000.0) < 0.0001 && Math.abs(packetIn.func_149147_e() * 8000.0) < 0.0001)) {
            S12PacketEntityVelocity packet = new S12PacketEntityVelocity(this.gameController.thePlayer.getEntityId(),
                    (this.gameController.thePlayer.motionX + packetIn.func_149149_c()) * 8000.0,
                    (this.gameController.thePlayer.motionY + packetIn.func_149144_d()) * 8000.0,
                    (this.gameController.thePlayer.motionZ + packetIn.func_149147_e()) * 8000.0);
            new PacketEvent(packet, PacketEvent.Type.RECEIVE).call();
        }
    }
}
