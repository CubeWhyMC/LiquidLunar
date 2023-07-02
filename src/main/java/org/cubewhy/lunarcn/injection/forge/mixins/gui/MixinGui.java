package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.Render2DEvent;
import org.cubewhy.lunarcn.gui.Notification;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
}
