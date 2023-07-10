package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
    @Shadow
    public Minecraft mc = Minecraft.getMinecraft();

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
