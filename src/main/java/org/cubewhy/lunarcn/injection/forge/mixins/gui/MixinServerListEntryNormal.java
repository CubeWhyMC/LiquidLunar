package org.cubewhy.lunarcn.injection.forge.mixins.gui;


import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.FeaturedServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

@Mixin(ServerListEntryNormal.class)
public class MixinServerListEntryNormal {
    @Final
    @Shadow
    private ServerData server;

    @Inject(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;listFormattedStringToWidth(Ljava/lang/String;I)Ljava/util/List;"))
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo ci) {
        boolean isFeaturedServer = this.server instanceof FeaturedServerData;
        if (isFeaturedServer) {
            liquidLunarForge$drawImageStar(x, y, false, FeaturedServerData.starIcon);
        }
    }

    @Inject(method = "mousePressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;connectToSelected()V"))
    public void mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_, CallbackInfoReturnable<Boolean> cir) {
        // disconnect first
        if (mc.theWorld != null) {
            mc.theWorld.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
        }
    }

    @Unique
    private void liquidLunarForge$drawImageStar(int x, int y, boolean lower, ResourceLocation starIcon) {
        mc.getTextureManager().bindTexture(starIcon);
        Gui.drawModalRectWithCustomSizedTexture(x - 16, lower ? y + 16 : y, 0.00F, 0.00F, 16, 16, 16, 16);
    }
}
