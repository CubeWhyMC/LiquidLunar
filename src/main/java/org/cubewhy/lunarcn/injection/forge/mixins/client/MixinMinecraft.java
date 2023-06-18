package org.cubewhy.lunarcn.injection.forge.mixins.client;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.files.ClientConfigFile;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class)
abstract public class MixinMinecraft {
    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    public boolean skipRenderWorld;

    @Shadow
    public int leftClickCounter;

    @Shadow
    public MovingObjectPosition objectMouseOver;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public EffectRenderer effectRenderer;

    @Shadow
    public PlayerControllerMP playerController;

    @Shadow
    public int rightClickDelayTimer;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    @Final
    public File mcDataDir;

    @Shadow
    public int displayWidth;

    @Shadow
    public int displayHeight;
    @Shadow
    private boolean fullscreen;
    @Shadow
    public GuiIngame ingameGUI;
    @Shadow
    private SoundHandler mcSoundHandler;

    @Shadow
    public abstract void setIngameNotInFocus();

    @Shadow
    public abstract void setIngameFocus();

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

    /**
     * @author CubeWhy
     * @reason 自定义屏幕
     */
    @Overwrite
    public void displayGuiScreen(GuiScreen guiScreenIn) {
        if (guiScreenIn == null && this.theWorld == null || guiScreenIn instanceof GuiMainMenu) {
            guiScreenIn = ClientConfigFile.getInstance().getMenuStyle();
        } else if (guiScreenIn == null && this.thePlayer.getHealth() <= 0.0F) {
            guiScreenIn = new GuiGameOver();
        }

        GuiScreen old = this.currentScreen;
        GuiOpenEvent event = new GuiOpenEvent(guiScreenIn);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            guiScreenIn = event.gui;
            if (old != null && guiScreenIn != old) {
                old.onGuiClosed();
            }

            if (guiScreenIn instanceof GuiMainMenu) {
                this.gameSettings.showDebugInfo = false;
                this.ingameGUI.getChatGUI().clearChatMessages();
            }

            this.currentScreen = guiScreenIn;
            if (guiScreenIn != null) {
                this.setIngameNotInFocus();
                ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                guiScreenIn.setWorldAndResolution(Minecraft.getMinecraft(), i, j);
                this.skipRenderWorld = false;
            } else {
                this.mcSoundHandler.resumeSounds();
                this.setIngameFocus();
            }

        }
    }
}
