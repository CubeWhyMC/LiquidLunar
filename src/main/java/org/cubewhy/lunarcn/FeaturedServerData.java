package org.cubewhy.lunarcn;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;

public class FeaturedServerData extends ServerData {
    public static final ResourceLocation starIcon = new ResourceLocation("lunarcn/star.png");

    public FeaturedServerData(String name, String ip) {
        super(name, ip, false);
    }
}
