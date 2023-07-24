package org.cubewhy.lunarcn.injection.forge.mixins.client;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.event.events.ScreenChangeEvent;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.event.events.WorldEvent;
import org.cubewhy.lunarcn.files.configs.ClientConfigFile;
import org.cubewhy.lunarcn.gui.SplashProgress;
import org.cubewhy.lunarcn.utils.FileUtils;
import org.cubewhy.lunarcn.utils.ImageUtils;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

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

    @Inject(method = "startGame", at = @At("RETURN"))
    public void startGameReturn(CallbackInfo ci) throws IOException {
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
     * @reason 设置窗口icon
     */
    @Overwrite
    private void setWindowIcon() {
        try {
            BufferedImage image = ImageIO.read(FileUtils.getFile(Client.clientLogo));
            ByteBuffer bytebuffer = ImageUtils.readImageToBuffer(ImageUtils.resizeImage(image, 16, 16));
            Display.setIcon(new ByteBuffer[]{
                    bytebuffer,
                    ImageUtils.readImageToBuffer(image)
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "displayCrashReport", at = @At(value = "INVOKE", target = "Lnet/minecraft/crash/CrashReport;getFile()Ljava/io/File;"))
    public void displayCrashReport(CrashReport crashReportIn, CallbackInfo ci) {
        String message = crashReportIn.getCauseStackTraceOrString();
        JOptionPane.showMessageDialog(null, "Game crashed!\n如果你看到这个窗口就说明游戏崩溃了\n给dev提供log可能会帮助你解决问题\n" + message, "oops, game crashed!", JOptionPane.ERROR_MESSAGE);
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
        new ScreenChangeEvent(guiScreenIn).callEvent();
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    public void loadWorld(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        new WorldEvent(worldClientIn).callEvent(); // call worldEvent
    }
}
