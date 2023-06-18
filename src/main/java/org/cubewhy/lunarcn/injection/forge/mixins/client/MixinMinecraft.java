package org.cubewhy.lunarcn.injection.forge.mixins.client;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At("HEAD"))
    public void startGameHead(CallbackInfo ci) {
        Client.getInstance().onInit(); // init game
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    public void startGameReturn(CallbackInfo ci) {
        Client.getInstance().onStart();
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER))
    public void step1(CallbackInfo ci) {
        SplashProgress.setProgress(2, "textures");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.AFTER))
    public void step2(CallbackInfo ci) {
        SplashProgress.setProgress(3, "Gui");
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        Client.getInstance().onStop();
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    public void runTick(CallbackInfo ci) {
        new TickEvent().callEvent();
    }

    /**
     * @author CubeWhy
     * @reason 使用自定义加载屏幕
     */
    @Overwrite
    public void drawSplashScreen(TextureManager textureManager) {
        SplashProgress.drawSplash(textureManager);
    }
}
