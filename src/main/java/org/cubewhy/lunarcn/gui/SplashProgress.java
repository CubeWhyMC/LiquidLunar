package org.cubewhy.lunarcn.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.utils.UnicodeFontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class SplashProgress {
    private static final int MAX = 4;
    private static int progress = 0;
    private static String currentText = "";
    private static ResourceLocation splash;
    private static UnicodeFontRenderer unicodeFontRenderer;

    public static void update() {
        if (Minecraft.getMinecraft() == null || mc.getLanguageManager() == null) {
            return;
        }

        drawSplash(mc.getTextureManager());
    }

    public static void setProgress(int giveProgress, String giveText) {
        progress = giveProgress;
        currentText = giveText;
        update();
    }

    public static void setProgress(String giveText) {
        progress ++;
        currentText = giveText;
        update();
    }

    public static void drawSplash(TextureManager textureManager) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaleFactor = scaledResolution.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) scaledResolution.getScaledWidth(), (double) scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        if (splash == null) {
            splash = new ResourceLocation(Client.splashImage);
        }

        textureManager.bindTexture(splash);

        GlStateManager.resetColor();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920, 1080);
        drawProgress();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);

        mc.updateDisplay();
    }

    private static void drawProgress() {
        if (mc.gameSettings == null || mc.getTextureManager() == null) {
            return;
        }

        if (unicodeFontRenderer == null) {
            unicodeFontRenderer = UnicodeFontRenderer.getFontOnPC("微软雅黑", 20);
        }

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        double nProgress = (double) progress;
        double calculation = (nProgress / MAX) * scaledResolution.getScaledWidth();

        Gui.drawRect(0, scaledResolution.getScaledHeight() - 35, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0, 0, 0, 50).getRGB());

        GlStateManager.resetColor();
        resetTextureState();

        unicodeFontRenderer.drawString(currentText, 20, scaledResolution.getScaledHeight() - 20, new Color(255, 255, 255).getRGB());

        String indexText = progress + "/" + MAX;
        unicodeFontRenderer.drawString(indexText, scaledResolution.getScaledWidth() - 20 - unicodeFontRenderer.getStringWidth(indexText), scaledResolution.getScaledWidth() - 25, new Color(254, 228, 1).getRGB());

        GlStateManager.resetColor();
        resetTextureState();

        Gui.drawRect(0, scaledResolution.getScaledHeight() - 2, (int) calculation, scaledResolution.getScaledHeight(), new Color(149, 201, 144).getRGB());

        Gui.drawRect(0, scaledResolution.getScaledHeight() - 2, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0, 0, 0, 10).getRGB());
    }

    private static void resetTextureState() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
    }
}