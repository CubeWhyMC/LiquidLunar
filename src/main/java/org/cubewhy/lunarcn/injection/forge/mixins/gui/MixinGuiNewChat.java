package org.cubewhy.lunarcn.injection.forge.mixins.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.Logger;
import org.cubewhy.lunarcn.event.events.ChatEvent;
import org.cubewhy.lunarcn.gui.elements.CopyButton;
import org.cubewhy.lunarcn.module.ModuleManager;
import org.cubewhy.lunarcn.module.impl.client.ChatConfig;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat extends Gui {
    @Shadow @Final private static Logger logger;
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

    private ArrayList<CopyButton> copyButtons = new ArrayList<>();

    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("RETURN"))
    public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId, CallbackInfo ci) {
        new ChatEvent(chatComponent).call();
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
        List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, fontRenderer, false, false);
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

    /**
     * @author CubeWhy
     * @reason add copy button
     */
    @Overwrite
    public void drawChat(int updateCounter) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i = this.getLineCount();
            boolean flag = false;
            int j = 0;
            int k = this.drawnChatLines.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (k > 0) {
                if (this.getChatOpen()) {
                    flag = true;
                }

                float f1 = this.getChatScale();
                int l = MathHelper.ceiling_float_int((float) this.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(f1, f1, 1.0F);


                for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1) {
                    ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);

                    if (chatline != null) {
                        int j1 = updateCounter - chatline.getUpdatedCounter();

                        if (j1 < 200 || flag) {
                            double d0 = (double) j1 / 200.0D;
                            d0 = 1.0D - d0;
                            d0 = d0 * 10.0D;
                            d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
                            d0 = d0 * d0;
                            int l1 = (int) (255.0D * d0);

                            if (flag) {
                                l1 = 255;
                            }

                            l1 = (int) ((float) l1 * f);
                            ++j;

                            if (l1 > 3) {
                                int x = 0;
                                int y = -i1 * 9;
                                drawRect(x, y - 9, x + l + 4, y, l1 / 2 << 24);
                                String text = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                if (mc.ingameGUI.getChatGUI().getChatOpen() && ((ChatConfig) (ModuleManager.getInstance().getModule(ChatConfig.class))).getCopy().getValue()) {
                                    new CopyButton(chatline.getChatComponent().getUnformattedText()).drawButton(Mouse.getX(), Mouse.getY(), y - 8);
                                    // TODO why still copy the last message
                                }
                                this.mc.fontRendererObj.drawStringWithShadow(text, (float) x, (float) (y - 8), 16777215 + (l1 << 24));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag) {
                    int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int l2 = k * k2 + k;
                    int i3 = j * k2 + j;
                    int j3 = this.scrollPos * i3 / k;
                    int k1 = i3 * i3 / l2;

                    if (l2 != i3) {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
                        drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
                    }
                }

                GlStateManager.popMatrix();
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
