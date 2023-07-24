package org.cubewhy.lunarcn.injection.forge.mixins.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.cubewhy.lunarcn.event.events.ChatEvent;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.module.impl.dev.ChatConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat extends Gui {
    @Final
    @Shadow
    private List<ChatLine> drawnChatLines;
    @Shadow
    private boolean isScrolled;
    @Shadow
    private int scrollPos;
    @Shadow
    @Final
    private List<ChatLine> chatLines;
    @Final
    @Shadow
    private Minecraft mc;

    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("RETURN"))
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId, CallbackInfo ci) {
        new ChatEvent(chatComponent).callEvent();
    }

    /**
     * @author CubeWhy
     * @reason 修改聊天记录上限
     */
    @Overwrite
    private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
        int limit = ((ChatConfig) ModuleManager.getInstance().getModule(ChatConfig.class)).getLimit().getValue();
        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }

        int i = MathHelper.floor_float((float) this.getChatWidth() / this.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i,fontRenderer, false, false);
        boolean flag = this.getChatOpen();

        for (IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }

            this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
        }

        while (this.drawnChatLines.size() > limit) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }

        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

            while (this.chatLines.size() > limit) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    @Shadow
    public abstract void scroll(int i);

    @Shadow
    public abstract void deleteChatLine(int chatLineId);

    @Shadow
    public abstract boolean getChatOpen();

    @Shadow
    public abstract float getChatScale();

    @Shadow
    public abstract int getChatWidth();

    @Shadow
    public abstract int getLineCount();
}
