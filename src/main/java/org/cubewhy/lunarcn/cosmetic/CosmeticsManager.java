package org.cubewhy.lunarcn.cosmetic;

import net.minecraft.client.renderer.entity.RenderPlayer;
import org.cubewhy.lunarcn.utils.ClassUtils;

import java.lang.reflect.Constructor;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;

public class CosmeticsManager {

    private static final CosmeticsManager instance = new CosmeticsManager();

    private RenderPlayer player;

    public static CosmeticsManager getInstance() {
        return instance;
    }

    public void loadCosmetics(RenderPlayer player) {
        this.player = player;
        ClassUtils.INSTANCE.resolvePackage(this.getClass().getPackage().getName() + ".cosmetics", CosmeticsBase.class)
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
            e.printStackTrace();
            logger.error("Can't load cosmetic " + clazz.getName());
        }
    }

    private void register(CosmeticsBase cosmetic) {
        this.player.addLayer(cosmetic);
    }


}
