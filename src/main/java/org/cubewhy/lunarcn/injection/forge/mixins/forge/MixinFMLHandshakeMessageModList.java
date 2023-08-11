package org.cubewhy.lunarcn.injection.forge.mixins.forge;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.cubewhy.lunarcn.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

@Mixin(FMLHandshakeMessage.ModList.class)
public class MixinFMLHandshakeMessageModList {
    @Shadow(remap = false)
    private Map<String, String> modTags;

    // anti forge
    @Inject(method = "toBytes", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void toBytes(ByteBuf buffer, CallbackInfo callbackInfo) {
        if (mc.isSingleplayer()) {
            // single-player didn't verify modList, LOL
            return;
        }

        callbackInfo.cancel();

        ArrayList<Map.Entry<String, String>> shownTags = new ArrayList<>();
        for (Map.Entry<String, String> modTag : this.modTags.entrySet()) {
            boolean hidden = ForgeMod.modId.equalsIgnoreCase(modTag.getKey());
            // hide LiquidLunar mod (some server ban us)
            if (hidden) {
                logger.info(String.format("HideModList: %s %s", modTag.getKey(), modTag.getValue()));
            } else {
                shownTags.add(modTag);
            }
        }

        ByteBufUtils.writeVarInt(buffer, shownTags.size(), 2);

        for (Map.Entry<String, String> modTag : shownTags) {
            logger.info(String.format("SendModList: %s %s", modTag.getKey(), modTag.getValue()));
            ByteBufUtils.writeUTF8String(buffer, modTag.getKey());
            ByteBufUtils.writeUTF8String(buffer, modTag.getValue());
        }

    }
}
