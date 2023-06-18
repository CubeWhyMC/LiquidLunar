package org.cubewhy.lunarcn.injection.forge.mixins.forge;


import net.minecraft.client.Minecraft;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(targets="net.minecraftforge.fml.client.SplashProgress$3", remap=false)
public class MixinSplashProgressRunnable {
    @Inject(method="run()V", at=@At(value="HEAD"), remap=false, cancellable=true)
    private void run(@NotNull CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        SplashProgress.drawSplash();
    }
}
