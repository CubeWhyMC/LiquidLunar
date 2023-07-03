package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.gui.Notification;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
    @Shadow
    public Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (HudManager.getInstance().notificationDisplayList.size() > 0) {
            Notification n = HudManager.getInstance().notificationDisplayList.get(0);

            n.drawNotification();
            if (n.timer.hasTimePassed(n.showTime)) {
                HudManager.getInstance().notificationDisplayList.remove(n);
            }
        }
    }

    /**
     * @author CubeWhy
     * @reason Unicode input fix
     */
    @Overwrite
    public void handleKeyboardInput() {
        char eventChar = Keyboard.getEventCharacter();
        int eventKey = Keyboard.getEventKey();
        if (Keyboard.getEventKeyState() || (eventChar > ' ' && eventKey == 0)) {
            this.keyTyped(eventChar, eventKey);
        }
        this.mc.dispatchKeypresses();
    }

    @Shadow
    protected abstract void keyTyped(char eventCharacter, int eventKey);
}
