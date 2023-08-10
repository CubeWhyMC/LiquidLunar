package org.cubewhy.lunarcn.injection.forge.mixins.Renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.event.events.JumpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.cubewhy.lunarcn.module.impl.Render.MobendsMod.onRenderLivingEvent;

/**
 * @author yuxiangll
 * @package org.cubewhy.lunarcn.injection.forge.mixins.Renderer
 * don't mind
 * @date 2023/8/10 21:42
 */
@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity extends RendererLivingEntity {

    public MixinRendererLivingEntity(RenderManager p_i46156_1_, ModelBase p_i46156_2_, float p_i46156_3_) {
        super(p_i46156_1_, p_i46156_2_, p_i46156_3_);
    }

    @Inject(method = "doRender", at = @At("HEAD"))
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (onRenderLivingEvent(this, entity, x, y, z, entityYaw, partialTicks)){
            return;
        }

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
