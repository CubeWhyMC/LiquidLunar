package org.cubewhy.lunarcn.injection.forge.mixins.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.SplashProgress;
import org.cubewhy.lunarcn.Client;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

@Mixin(value = SplashProgress.class, remap = false)
public abstract class MixinSplashProgress {

    /**
     * @author CubeWhy
     * @reason 重写启动屏幕
     */
    @Overwrite(remap=false)
    public static void start() {
        Client.getInstance().onInit();
    }
}
