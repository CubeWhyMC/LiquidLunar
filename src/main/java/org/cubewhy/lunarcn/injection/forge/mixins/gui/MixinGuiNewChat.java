package org.cubewhy.lunarcn.injection.forge.mixins.gui;


import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import org.cubewhy.lunarcn.event.events.ChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {
    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("RETURN"))
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId, CallbackInfo ci) {
        new ChatEvent(chatComponent).callEvent();
    }
}
