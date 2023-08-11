package org.cubewhy.lunarcn.module.impl.render.mobends.pack;


//import me.yuxiangll.jigsaw.client.Utils.render.mobends.data.EntityData;

import org.cubewhy.lunarcn.module.impl.render.mobends.data.EntityData;

public class BendsVar {
    public static EntityData tempData;

    public static float getGlobalVar(String name) {
        if (name.equalsIgnoreCase("ticks")) {
            if (tempData == null)
                return 0;
            return tempData.ticks;
        } else if (name.equalsIgnoreCase("ticksAfterPunch")) {
            if (tempData == null)
                return 0;
            return tempData.ticksAfterPunch;
        }
        return Float.POSITIVE_INFINITY;
    }
}
