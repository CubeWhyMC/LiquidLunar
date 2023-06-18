package me.hobbyshop.lunar.ui;

import me.hobbyshop.lunar.font.FontUtil;
import me.hobbyshop.lunar.ui.buttons.QuitButton;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.gui.altmanager.LoginScreen;
import org.cubewhy.lunarcn.gui.elements.ImageButton;
import org.cubewhy.lunarcn.gui.elements.LunarButton;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.utils.GitUtils;
import org.cubewhy.lunarcn.utils.MicrosoftAccountUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;

public class LunarMainMenu extends GuiMainMenu {

    private ResourceLocation logo;

    private LunarButton btnSinglePlayer;
    private LunarButton btnMultiplayer;

    private ImageButton btnClientOptions;
    private ImageButton btnCosmetics;
    private ImageButton btnMinecraftOptions;
    private ImageButton btnLanguage;

    private QuitButton btnQuit;

    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{
            new ResourceLocation("lunar/panorama/panorama_0.png"),
            new ResourceLocation("lunar/panorama/panorama_1.png"),
            new ResourceLocation("lunar/panorama/panorama_2.png"),
            new ResourceLocation("lunar/panorama/panorama_3.png"),
            new ResourceLocation("lunar/panorama/panorama_4.png"),
            new ResourceLocation("lunar/panorama/panorama_5.png")
    };

    private static int panoramaTimer;
    private ResourceLocation backgroundTexture;
    private LunarButton btnAltManager;

    @Override
    public void initGui() {
//        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", new DynamicTexture(256, 256));

        this.logo = new ResourceLocation("lunarcn/logo.png");

        this.btnSinglePlayer = new LunarButton("S I N G L E P L A Y E R", this.width / 2 - 66, this.height / 2);
        this.btnMultiplayer = new LunarButton("M U L T I P L A Y E R", this.width / 2 - 66, this.height / 2 + 15);
        this.btnAltManager = new LunarButton("A L T M A N A G E R", 10, 10); // TODO 实现altmanager

        int yPos = this.height - 20;
        this.btnClientOptions = new ImageButton("SETTINGS", new ResourceLocation("lunar/icons/lunar.png"), this.width / 2 - 30, yPos);
        this.btnCosmetics = new ImageButton("COSMETICS", new ResourceLocation("lunar/icons/cosmetics.png"), this.width / 2 - 15, yPos);
        this.btnMinecraftOptions = new ImageButton("MINECRAFT SETTINGS", new ResourceLocation("lunar/icons/cog.png"), this.width / 2, yPos);
        this.btnLanguage = new ImageButton("LANGUAGE", new ResourceLocation("lunar/icons/globe.png"), this.width / 2 + 15, yPos);

        this.btnQuit = new QuitButton(this.width - 17, 7);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.btnSinglePlayer.hoverFade > 0) {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (this.btnMultiplayer.hoverFade > 0) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (this.btnQuit.hoverFade > 0) {
            mc.shutdown();
        }
        if (this.btnMinecraftOptions.hoverFade > 0) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if (this.btnLanguage.hoverFade > 0) {
            mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (this.btnClientOptions.hoverFade > 0) {
            HudManager.getInstance().openConfigScreen();
        }
        if (this.btnAltManager.hoverFade > 0) {
            mc.displayGuiScreen(new LoginScreen());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
//        super.renderSkybox(mouseX, mouseY, partialTicks);
        drawDefaultBackground(); // TODO fix this
        GlStateManager.enableAlpha();

        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(logo);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 25, this.height / 2 - 68, 0, 0, 49, 49, 49, 49);

        FontUtil.TITLE.getFont().drawCenteredString(Client.clientName, (float) this.width / 2 - 0.25F, this.height / 2 - 18, new Color(30, 30, 30, 70).getRGB());
        FontUtil.TITLE.getFont().drawCenteredString(Client.clientName, (float) this.width / 2, (float) this.height / 2 - 19, -1);

        this.btnSinglePlayer.drawButton(mouseX, mouseY);
        this.btnMultiplayer.drawButton(mouseX, mouseY);

        this.btnAltManager.drawButton(mouseX, mouseY);

        this.btnClientOptions.drawButton(mouseX, mouseY);
        this.btnCosmetics.drawButton(mouseX, mouseY);
        this.btnMinecraftOptions.drawButton(mouseX, mouseY);
        this.btnLanguage.drawButton(mouseX, mouseY);

        this.btnQuit.drawButton(mouseX, mouseY);

        String s = "Copyright Mojang Studios. Do not distribute!";
        FontUtil.TEXT.getFont().drawString(Client.clientName + Client.clientVersion + "(" + GitUtils.gitBranch + "/" + GitUtils.gitInfo.getProperty("git.commit.id.abbrev") + ") | Minecraft 1.8.9", 7, this.height - 11, new Color(255, 255, 255, 100).getRGB());
        FontUtil.TEXT.getFont().drawString(s, this.width - FontUtil.TEXT.getFont().getWidth(s) - 6, this.height - 11, new Color(255, 255, 255, 100).getRGB());

    }

    @Override
    public void updateScreen() {
        ++panoramaTimer;
        super.updateScreen();
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;

        for (int j = 0; j < i * i; ++j) {
            GlStateManager.pushMatrix();
            float f = ((float) (j % i) / (float) i - 0.5F) / 64.0F;
            float f1 = ((float) (j / i) / (float) i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float) panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float) panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k) {
                GlStateManager.pushMatrix();

                if (k == 1) {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3) {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5) {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0F;
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }
}
