package org.cubewhy.lunarcn.cosmetic;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.utils.ClassUtils;

import java.lang.reflect.Constructor;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class CosmeticsManager {

    private static final CosmeticsManager instance = new CosmeticsManager();

    private RenderPlayer player;

    public CosmeticsManager() {
        EventManager.register(this);
    }

    public static CosmeticsManager getInstance() {
        return instance;
    }

    public void loadCosmetics(RenderPlayer player) {
        // TODO Add API get enabled cos
        this.player = player;
        ClassUtils.INSTANCE.resolvePackage(this.getClass().getPackage().getName() + ".impl", CosmeticsBase.class)
                .forEach(this::register);
    }

    private void register(Class<? extends CosmeticsBase> clazz) {
        logger.info("Loading cosmetic " + clazz.getName());
        try {
            Constructor<? extends CosmeticsBase> newClass = clazz.getDeclaredConstructor(RenderPlayer.class);
            register(newClass.newInstance(this.player));
        } catch (IllegalAccessException e) {
            register((CosmeticsBase) ClassUtils.INSTANCE.getObjectInstance(clazz));
        } catch (Throwable e) {
            logger.catching(e);
            logger.error("Can't load cosmetic " + clazz.getName());
        }
    }

    public void register(CosmeticsBase cosmetic) {
        this.player.addLayer(cosmetic);
    }


}
