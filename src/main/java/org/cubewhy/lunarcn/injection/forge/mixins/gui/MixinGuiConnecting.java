package org.cubewhy.lunarcn.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.cubewhy.lunarcn.event.events.JoinServerEvent;
import org.cubewhy.lunarcn.utils.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting extends GuiScreen {
    @Inject(method = "connect", at = @At("RETURN"))
    private void connect(String ip, int port, CallbackInfo ci) {
        new JoinServerEvent(ip, port).callEvent(); // 加入服务器事件
    }

    /**
     * @author CubeWhy
     * @reason 重写加载画面
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        RenderUtils.drawLoadingCircle((float) this.width / 2, (float) this.height / 4 + 70);

        String ip = "Unknown";

        final ServerData serverData = mc.getCurrentServerData();
        if(serverData != null)
            ip = serverData.serverIP;

        this.drawCenteredString(fontRenderer, "Connecting to", this.width / 2, this.height / 4 + 110, new Color(255, 255, 255).getRGB());
        this.drawCenteredString(fontRenderer, ip, this.width / 2, this.height / 4 + 120, new Color(82, 129, 251).getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
