package org.cubewhy.lunarcn.injection.forge.mixins.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author yuxiangll
 * @package org.cubewhy.lunarcn.injection.forge.mixins.Renderer
 * don't mind
 * @date 2023/8/10 21:42
 */
@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity {

//    @Inject(method = "doRender(Lnet/minecraft/entity/Entity;DDDFF)V", at = @At("HEAD"))
//    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        if (onRenderLivingEvent(, entity, x, y, z, entityYaw, partialTicks)){
//
//        }
//    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
