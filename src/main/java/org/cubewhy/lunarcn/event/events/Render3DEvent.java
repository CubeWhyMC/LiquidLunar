package org.cubewhy.lunarcn.event.events;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.cubewhy.lunarcn.event.Event;

/**
 * @author yuxiangll
 * @package org.cubewhy.lunarcn.event.events
 * don't mind
 * @date 2023/8/10 21:52
 */
public class Render3DEvent extends Event {
    private float partialTicks;
    public Render3DEvent(float partialTicks){
        this.partialTicks = partialTicks;
    }
    public float getpartialTicks(){
        return partialTicks;
    }
}
