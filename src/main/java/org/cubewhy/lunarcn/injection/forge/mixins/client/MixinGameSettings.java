package org.cubewhy.lunarcn.injection.forge.mixins.client;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(GameSettings.class)
public class MixinGameSettings {
}
