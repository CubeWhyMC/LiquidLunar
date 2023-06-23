package org.cubewhy.lunarcn.cosmetic.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.cosmetic.CosmeticsBase;
import org.cubewhy.lunarcn.cosmetic.CosmeticsController;
import org.cubewhy.lunarcn.cosmetic.CosmeticsModelBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class TopHat extends CosmeticsBase {

    private final ResourceLocation texture = new ResourceLocation("lunarcn/hat.png");
    private final Model model;

    public TopHat(RenderPlayer player) {
        super(player);
        model = new Model(player);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
        if (CosmeticsController.shouldRenderTopHat(player)) {
            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(texture);

            if (player.isSneaking()) {
                GL11.glTranslated(0, 0.225D, 0);
            }

            float[] color = CosmeticsController.getTopHatColor();
            GL11.glColor3d(color[0], color[1], color[2]);
            model.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
            GL11.glColor3f(1, 1, 1);
            GL11.glPopMatrix();
        }
    }

    private static class Model extends CosmeticsModelBase {
        private ModelRenderer rim;
        private ModelRenderer pointy;


        public Model(@NotNull RenderPlayer player) {
            super(player);
            rim = new ModelRenderer(player.mainModel, 0, 0);
            rim.addBox(-5.5f, -9f, -5.5f, 11, 2, 11);
            pointy = new ModelRenderer(player.getMainModel(), 0, 13);
            pointy.addBox(-3.5F, -17, -3.5F, 7, 8, 7);
        }

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
            rim.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            rim.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            rim.rotationPointX = 0.0F;
            rim.rotationPointY = 0.0F;
            rim.render(scale);

            pointy.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            pointy.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            pointy.rotationPointX = 0.0F;
            pointy.rotationPointY = 0.0F;
            pointy.render(scale);
        }
    }
}
