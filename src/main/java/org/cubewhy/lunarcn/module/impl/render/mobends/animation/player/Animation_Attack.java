package org.cubewhy.lunarcn.module.impl.render.mobends.animation.player;


//import me.yuxiangll.jigsaw.client.Utils.render.mobends.animation.Animation;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.client.model.entity.ModelBendsPlayer;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.Data_Player;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.EntityData;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.pack.BendsPack;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.cubewhy.lunarcn.module.impl.render.mobends.animation.Animation;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.model.entity.ModelBendsPlayer;
import org.cubewhy.lunarcn.module.impl.render.mobends.data.PlayerData;
import org.cubewhy.lunarcn.module.impl.render.mobends.data.EntityData;
import org.cubewhy.lunarcn.module.impl.render.mobends.pack.BendsPack;

public class Animation_Attack extends Animation {
	public String getName() {
		return "attack";
	}

	@Override
	public void animate(EntityLivingBase argEntity, ModelBase argModel, EntityData argData) {
		ModelBendsPlayer model = (ModelBendsPlayer) argModel;
		PlayerData data = (PlayerData) argData;
		EntityPlayer player = (EntityPlayer) argEntity;

		if (player.getCurrentEquippedItem() != null) {
			if (data.ticksAfterPunch < 10) {
				if (data.currentAttack == 1) {
					Animation_Attack_Combo0.animate((EntityPlayer) argEntity,model,data);
					BendsPack.animate(model,"player","attack");
					BendsPack.animate(model,"player","attack_0");
				} else if (data.currentAttack == 2) {
					Animation_Attack_Combo1.animate((EntityPlayer) argEntity,model,data);
					BendsPack.animate(model,"player","attack");
					BendsPack.animate(model,"player","attack_1");
				} else if (data.currentAttack == 3) {
					Animation_Attack_Combo2.animate((EntityPlayer) argEntity,model,data);
					BendsPack.animate(model,"player","attack");
					BendsPack.animate(model,"player","attack_2");
				}
			} else if (data.ticksAfterPunch < 60) {
				Animation_Attack_Stance.animate((EntityPlayer) argEntity,model,data);
				BendsPack.animate(model,"player","attack_stance");
			}
		} else {
			if (data.ticksAfterPunch < 10) {
				Animation_Attack_Punch.animate((EntityPlayer) argEntity,model,data);
				BendsPack.animate(model,"player","attack");
				BendsPack.animate(model,"player","punch");
			} else if (data.ticksAfterPunch < 60) {
				Animation_Attack_PunchStance.animate((EntityPlayer) argEntity,model,data);
				BendsPack.animate(model,"player","punch_stance");
			}
		}
	}
}
