package org.cubewhy.lunarcn.module.impl.Render.mobends.animation.zombie;


//import me.yuxiangll.jigsaw.client.Utils.render.mobends.animation.Animation;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.client.model.ModelRendererBends;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.client.model.entity.ModelBendsZombie;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.Data_Zombie;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.EntityData;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import org.cubewhy.lunarcn.module.impl.Render.mobends.animation.Animation;
import org.cubewhy.lunarcn.module.impl.Render.mobends.client.model.ModelRendererBends;
import org.cubewhy.lunarcn.module.impl.Render.mobends.client.model.entity.ModelBendsZombie;
import org.cubewhy.lunarcn.module.impl.Render.mobends.data.Data_Zombie;
import org.cubewhy.lunarcn.module.impl.Render.mobends.data.EntityData;

public class Animation_Stand extends Animation {
	
	public String getName(){
		return "stand";
	}

	@Override
	public void animate(EntityLivingBase argEntity, ModelBase argModel, EntityData argData) {
		EntityZombie zombie = (EntityZombie) argEntity;
		ModelBendsZombie model = (ModelBendsZombie) argModel;
		Data_Zombie data = (Data_Zombie) argData;
		
		model.renderOffset.setSmoothY(-3.0f);
		
		((ModelRendererBends)model.bipedBody).rotation.setSmoothX(30.0f,0.3f);
		
		((ModelRendererBends)model.bipedRightArm).rotation.setSmoothX(-30.0f,0.3f);
		((ModelRendererBends)model.bipedLeftArm).rotation.setSmoothX(-30.0f,0.3f);
		
		((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothZ(10f,0.3f);
		((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothZ(-10f,0.3f);
		
		((ModelRendererBends)model.bipedRightLeg).rotation.setSmoothX(-20,0.3f);
		((ModelRendererBends)model.bipedLeftLeg).rotation.setSmoothX(-20,0.3f);
		
		((ModelRendererBends)model.bipedRightForeLeg).rotation.setSmoothX(25,0.3f);
		((ModelRendererBends)model.bipedLeftForeLeg).rotation.setSmoothX(25,0.3f);
		
		((ModelRendererBends)model.bipedHead).rotation.setX(model.headRotationX-30);
		((ModelRendererBends)model.bipedHead).rotation.setY(model.headRotationY);
	}
}
