package org.cubewhy.lunarcn.cosmetic;

import net.minecraft.client.entity.AbstractClientPlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CosmeticsController {
    public static boolean shouldRenderTopHat(AbstractClientPlayer player) {
        return false;
    }

    @Contract(value = " -> new", pure = true)
    public static float @NotNull [] getTopHatColor() {
        return new float[]{1, 0, 0};
    }
}
