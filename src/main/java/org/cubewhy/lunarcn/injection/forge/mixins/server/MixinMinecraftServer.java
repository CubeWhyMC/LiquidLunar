package org.cubewhy.lunarcn.injection.forge.mixins.server;

import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Shadow
    protected abstract void setUserMessage(String message);

    @Shadow public abstract String getUserMessage();

//    /**
//     * @author CubeWhy
//     * @reason 显示世界加载进度
//     * */
//    @Inject(method = "outputPercentRemaining", at = @At("RETURN"))
//    protected void outputPercentRemaining(String message, int percent, CallbackInfo ci) {
//        this.setUserMessage(message + " " +  percent + " %");
//    }
}
