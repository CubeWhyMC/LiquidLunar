package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.gui.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {
    @Inject(method = "initGui", at = @At("RETURN"))
    public void initGui(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(114514, this.width / 2 - 100, this.height / 4 + 144 - 16, "ServerSwitcher"));
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void actionPerformed(@NotNull GuiButton button, CallbackInfo ci) {
        if (button.id == 114514) {
            // server switcher
            mc.displayGuiScreen(new GuiMultiplayer(mc.currentScreen)); // display gui multiplayer
        }
    }

//    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z"), cancellable = true)
//    public void confirmDisconnect(GuiButton button, CallbackInfo ci) {
//        final boolean[] state = new boolean[1];
//        GuiYesNo screen = new GuiYesNo((b, i) -> state[0] = b, "Back to mainMenu?", "", 0);
//
//        mc.displayGuiScreen(screen);
//
//
//        if (!state[0]) {
//            mc.displayGuiScreen(null);
//            ci.cancel();
//        }
//    }
}
