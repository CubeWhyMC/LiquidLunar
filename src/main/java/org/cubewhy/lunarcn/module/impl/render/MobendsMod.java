package org.cubewhy.lunarcn.module.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.Render3DEvent;
import org.cubewhy.lunarcn.event.events.TickEvent;
import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.module.ModuleCategory;
import org.cubewhy.lunarcn.module.ModuleInfo;
import org.cubewhy.lunarcn.module.impl.render.mobends.AnimatedEntity;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.renderer.entity.RenderBendsPlayer;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.renderer.entity.RenderBendsSpider;
import org.cubewhy.lunarcn.module.impl.render.mobends.client.renderer.entity.RenderBendsZombie;
import org.cubewhy.lunarcn.module.impl.render.mobends.data.PlayerData;
import org.cubewhy.lunarcn.module.impl.render.mobends.data.SpiderData;
import org.cubewhy.lunarcn.module.impl.render.mobends.data.ZombieData;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author yuxiangll
 * @package org.cubewhy.lunarcn.module.impl.Render
 * don't mind
 * @date 2023/8/10 21:35
 */
@ModuleInfo(name = "Mobends", description = "Make you look better", category = ModuleCategory.RENDER)
public class MobendsMod extends Module {
    public static float partialTicks = 0.0f;
    public static float ticks = 0.0f;
    public static float ticksPerFrame = 0.0f;
    public static boolean flag = false;
    Minecraft mc = Minecraft.getMinecraft();

    public static boolean onRenderLivingEvent(RendererLivingEntity renderer, EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!flag || renderer instanceof RenderBendsPlayer || renderer instanceof RenderBendsZombie || renderer instanceof RenderBendsSpider) {
            return false;
        }

        //System.out.println(entity);
        AnimatedEntity animatedEntity = AnimatedEntity.getByEntity(entity);
        //System.out.println(animatedEntity);
        if (animatedEntity != null && (entity instanceof EntityPlayer || (entity instanceof EntityZombie) || (entity instanceof EntitySpider))) {
            //System.out.println("Test");
            if (entity instanceof EntityPlayer) {
                AbstractClientPlayer player = (AbstractClientPlayer) entity;
                //AnimatedEntity.playerRenderer.doRender(player, x, y, z, entityYaw, partialTicks);
                //System.out.println("Renddddddd");
                AnimatedEntity.getPlayerRenderer(player).doRender(player, x, y, z, entityYaw, partialTicks);

            } else if (entity instanceof EntityZombie) {
                EntityZombie zombie = (EntityZombie) entity;
                AnimatedEntity.zombieRenderer.doRender(zombie, x, y, z, entityYaw, partialTicks);
            } else {
                EntitySpider spider = (EntitySpider) entity;
                AnimatedEntity.spiderRenderer.doRender(spider, x, y, z, entityYaw, partialTicks);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEnabled() {
        flag = true;
    }

    @Override
    public void onDisabled() {
        flag = false;
    }

    @EventTarget
    public void onRender(Render3DEvent event) {

        if (mc.theWorld == null) {
            return;
        }

        float partialTicks = event.getpartialTicks();

        for (int i = 0; i < PlayerData.dataList.size(); i++) {
            PlayerData.dataList.get(i).update(partialTicks);
        }

        for (int i = 0; i < ZombieData.dataList.size(); i++) {
            ZombieData.dataList.get(i).update(partialTicks);
        }

        for (int i = 0; i < SpiderData.dataList.size(); i++) {
            SpiderData.dataList.get(i).update(partialTicks);
        }
        if (mc.thePlayer != null) {
            float newTicks = mc.thePlayer.ticksExisted + partialTicks;
            if (!(mc.theWorld.isRemote && mc.isGamePaused())) {
                ticksPerFrame = Math.min(Math.max(0F, newTicks - ticks), 1F);
                ticks = newTicks;
            } else {
                ticksPerFrame = 0F;
            }
        }

    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (mc.theWorld == null) {
            return;
        }

        for (int i = 0; i < PlayerData.dataList.size(); i++) {
            PlayerData data = PlayerData.dataList.get(i);
            Entity entity = mc.theWorld.getEntityByID(data.entityID);
            if (entity != null) {
                if (!data.entityType.equalsIgnoreCase(entity.getName())) {
                    PlayerData.dataList.remove(data);
                    PlayerData.add(new PlayerData(entity.getEntityId()));
                    //BendsLogger.log("Reset entity",BendsLogger.DEBUG);
                } else {

                    data.motion_prev.set(data.motion);

                    data.motion.x = (float) entity.posX - data.position.x;
                    data.motion.y = (float) entity.posY - data.position.y;
                    data.motion.z = (float) entity.posZ - data.position.z;

                    data.position = new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
                }
            } else {
                PlayerData.dataList.remove(data);
                //BendsLogger.log("No entity",BendsLogger.DEBUG);
            }
        }

        for (int i = 0; i < ZombieData.dataList.size(); i++) {
            ZombieData data = ZombieData.dataList.get(i);
            Entity entity = mc.theWorld.getEntityByID(data.entityID);
            if (entity != null) {
                if (!data.entityType.equalsIgnoreCase(entity.getName())) {
                    ZombieData.dataList.remove(data);
                    ZombieData.add(new ZombieData(entity.getEntityId()));
                    //BendsLogger.log("Reset entity",BendsLogger.DEBUG);
                } else {

                    data.motion_prev.set(data.motion);

                    data.motion.x = (float) entity.posX - data.position.x;
                    data.motion.y = (float) entity.posY - data.position.y;
                    data.motion.z = (float) entity.posZ - data.position.z;

                    data.position = new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
                }
            } else {
                ZombieData.dataList.remove(data);
                //BendsLogger.log("No entity",BendsLogger.DEBUG);
            }
        }

        for (int i = 0; i < SpiderData.dataList.size(); i++) {
            SpiderData data = SpiderData.dataList.get(i);
            Entity entity = mc.theWorld.getEntityByID(data.entityID);
            if (entity != null) {
                if (!data.entityType.equalsIgnoreCase(entity.getName())) {
                    SpiderData.dataList.remove(data);
                    SpiderData.add(new SpiderData(entity.getEntityId()));
                    //BendsLogger.log("Reset entity",BendsLogger.DEBUG);
                } else {

                    data.motion_prev.set(data.motion);

                    data.motion.x = (float) entity.posX - data.position.x;
                    data.motion.y = (float) entity.posY - data.position.y;
                    data.motion.z = (float) entity.posZ - data.position.z;

                    data.position = new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
                }
            } else {
                SpiderData.dataList.remove(data);
                //BendsLogger.log("No entity",BendsLogger.DEBUG);
            }
        }
    }
}
