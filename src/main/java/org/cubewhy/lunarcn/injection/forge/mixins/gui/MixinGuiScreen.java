package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    @Shadow
    public Minecraft mc = Minecraft.getMinecraft();
}
