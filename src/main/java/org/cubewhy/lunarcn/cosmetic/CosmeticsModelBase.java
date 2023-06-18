package org.cubewhy.lunarcn.cosmetic;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.jetbrains.annotations.NotNull;

public class CosmeticsModelBase extends ModelBase {

    protected final ModelBiped playerModel;

    public CosmeticsModelBase(@NotNull RenderPlayer player) {
        this.playerModel = player.getMainModel();
    }
}
