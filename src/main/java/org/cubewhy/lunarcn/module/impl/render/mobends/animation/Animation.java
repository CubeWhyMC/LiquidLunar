package org.cubewhy.lunarcn.module.impl.Render.mobends.animation;

//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.EntityData;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import org.cubewhy.lunarcn.module.impl.Render.mobends.data.EntityData;

public abstract class Animation {
	public abstract void animate(EntityLivingBase argEntity, ModelBase argModel, EntityData argData);
	public abstract String getName();
}
