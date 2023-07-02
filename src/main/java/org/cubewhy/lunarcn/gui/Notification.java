package org.cubewhy.lunarcn.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.utils.MSTimer;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class Notification {

    public final String text;
    public final Type type;
    public final long showTime;
    public final MSTimer timer = new MSTimer();

    public int x = mc.displayWidth;
    public int y = mc.displayHeight - 50;

    /**
     * 创建一个通知
     * @param text 要显示的文字
     * @param type 通知类型
     * @param showTime 显示时间
     * */

    public Notification(String text, Type type, long showTime) {
        this.text = text;
        this.type = type;
        this.showTime = showTime;

        this.timer.reset();
    }

    /**
     * 创建一个通知
     * 默认时间为3000L (3s)
     * @param text 要显示的内容
     * @param type 通知类型
     * */
    public Notification(String text, Type type) {
        this(text, type, 3000L);
    }

    /**
     * 创建一个通知
     * 默认时间为3000L (3s)
     * 默认通知类型为Type.INFO (信息)
     * @param text 要显示的内容
     * */
    public Notification(String text) {
        this(text, Type.INFO);
    }

    /**
     * 绘制通知
     * */

    public void drawNotification() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 进入动画
        int textPixel = mc.fontRendererObj.getStringWidth(this.text);
        if (x > mc.displayWidth - textPixel - 30 && this.timer.timePassed() <= showTime) {
            x--;
        } else {
            x++; // 退出动画
        }

        // 绘制内容
        RenderUtils.drawRoundedRect(x, y,
                textPixel + 30, mc.fontRendererObj.FONT_HEIGHT * 2 + 25,
                2, new Color(0, 0, 0, 60)); // 画背景
        RenderUtils.drawString(this.type.name(), x + 30, y, true); // 画通知类型
        RenderUtils.drawString(this.text, x + 30, y + mc.fontRendererObj.FONT_HEIGHT, true); // 画文字
        RenderUtils.drawImage(this.type.icon, x, y, 25, 25); // 画图标
    }

    public enum Type {
        INFO("lunarcn/icons/info.png"),
        ERROR("lunarcn/icons/error.png"),
        WARNING("lunarcn/icons/warning.png"),
        DEBUG("lunarcn/icons/debug.png");

        public final ResourceLocation icon;

        Type(String pathToIcon) {
            this.icon = new ResourceLocation(pathToIcon);
        }
    }
}
