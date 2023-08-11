package org.cubewhy.lunarcn.module.impl.render.mobends.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//import me.yuxiangll.jigsaw.client.Utils.render.mobends.client.model.ModelRendererBends;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.client.model.entity.ModelBendsZombie;
//import me.yuxiangll.jigsaw.client.Utils.render.mobends.util.SmoothVector3f;
import net.minecraft.client.Minecraft;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.model.ModelRendererBends;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.model.entity.ModelBendsZombie;
import org.cubewhy.lunarcn.module.impl.render.mobends.util.SmoothVector3f;

public class ZombieData extends EntityData{
	public static List<ZombieData> dataList = new ArrayList<ZombieData>();
	
	public ModelRendererBends head;
    public ModelRendererBends headwear;
    public ModelRendererBends body;
    public ModelRendererBends rightArm;
    public ModelRendererBends leftArm;
    public ModelRendererBends rightLeg;
    public ModelRendererBends leftLeg;
    public ModelRendererBends ears;
    public ModelRendererBends cloak;

    public ModelRendererBends rightForeArm;
    public ModelRendererBends leftForeArm;
    public ModelRendererBends rightForeLeg;
    public ModelRendererBends leftForeLeg;
	
    public SmoothVector3f renderOffset = new SmoothVector3f();
    public SmoothVector3f renderRotation = new SmoothVector3f();
    
    public int currentWalkingState = 0;
    public float ticksBeforeStateChange = 0;
    
	public ZombieData(int argEntityID) {
		super(argEntityID);
	}
	
	public void syncModelInfo(ModelBendsZombie argModel){
		if(this.head == null) this.head = new ModelRendererBends(argModel); this.head.sync((ModelRendererBends)argModel.bipedHead);
		if(this.headwear == null) this.headwear = new ModelRendererBends(argModel); this.headwear.sync((ModelRendererBends)argModel.bipedHeadwear);
		if(this.body == null) this.body = new ModelRendererBends(argModel); this.body.sync((ModelRendererBends)argModel.bipedBody);
		if(this.rightArm == null) this.rightArm = new ModelRendererBends(argModel); this.rightArm.sync((ModelRendererBends)argModel.bipedRightArm);
		if(this.leftArm == null) this.leftArm = new ModelRendererBends(argModel); this.leftArm.sync((ModelRendererBends)argModel.bipedLeftArm);
		if(this.rightLeg == null) this.rightLeg = new ModelRendererBends(argModel); this.rightLeg.sync((ModelRendererBends)argModel.bipedRightLeg);
		if(this.leftLeg == null) this.leftLeg = new ModelRendererBends(argModel); this.leftLeg.sync((ModelRendererBends)argModel.bipedLeftLeg);
		if(this.rightForeArm == null) this.rightForeArm = new ModelRendererBends(argModel); this.rightForeArm.sync((ModelRendererBends)argModel.bipedRightForeArm);
		if(this.leftForeArm == null) this.leftForeArm = new ModelRendererBends(argModel); this.leftForeArm.sync((ModelRendererBends)argModel.bipedLeftForeArm);
		if(this.rightForeLeg == null) this.rightForeLeg = new ModelRendererBends(argModel); this.rightForeLeg.sync((ModelRendererBends)argModel.bipedRightForeLeg);
		if(this.leftForeLeg == null) this.leftForeLeg = new ModelRendererBends(argModel); this.leftForeLeg.sync((ModelRendererBends)argModel.bipedLeftForeLeg);
		
		this.renderOffset.set(argModel.renderOffset);
		this.renderRotation.set(argModel.renderRotation);
	}
	
	public static void add(ZombieData argData){
		dataList.add((ZombieData) argData);
	}
	
	public static ZombieData get(int argEntityID){
		for(int i = 0;i < dataList.size();i++){
			if(dataList.get(i).entityID == argEntityID){
				return dataList.get(i);
			}
		}
		
		ZombieData newData = new ZombieData(argEntityID);
		
		if(Minecraft.getMinecraft().theWorld.getEntityByID(argEntityID) != null){
			dataList.add((ZombieData) newData);
		}
		
		return newData;
	}

	@Override
	public void update(float argPartialTicks) {
		super.update(argPartialTicks);
		
		this.ticksBeforeStateChange-=argPartialTicks;
		
		if(this.ticksBeforeStateChange <= 0){
			Random random = new Random();
			this.currentWalkingState = random.nextInt(2);
			this.ticksBeforeStateChange=80+random.nextInt(20);
		}
	}
	
	@Override
	public void onLiftoff(){
		super.onLiftoff();
	}
}