package org.cubewhy.lunarcn.gui.notification;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;
import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class Notification {
    public final Type type;

    private final String title;
    private final String message;
    private final long fadedIn;
    private final long fadeOut;
    private final long end;
    private long start;
    private long i;

    /**
     * 创建一个通知
     *
     * @param title    题目
     * @param message  消息
     * @param type     通知类型
     * @param showTime 显示时间
     */

    public Notification(String title, String message, Type type, int showTime) {
        // Initialize the text, type, and showTime variables
        this.title = title;
        this.message = message;
        this.type = type;
        fadedIn = 200L * showTime;
        fadeOut = fadedIn + 500L * showTime;
        end = fadeOut + fadedIn;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    private long getTime() {
        return System.currentTimeMillis() - start;
    }

    public void render() {
        ScaledResolution res = new ScaledResolution(mc);
        double offset;
        int width = 120;
        int height = 30;
        long time = getTime();

        if (time < fadedIn) {
            // in
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            // out
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        i = time / (fadedIn + fadeOut);

        Color color = new Color(0, 0, 0, 60);
        Color color1;

        color1 = new Color(0, 26, 169);

        if (type == Type.INFO) {
            color1 = new Color(0, 26, 169);
        } else if (type == Type.WARNING) {
            color1 = new Color(204, 193, 0);
        } else if (type == Type.DEBUG) {
            color1 = new Color(0, 0, 100);
        } else if (type == Type.ERROR) {
            color1 = new Color(204, 0, 18);
            int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
            color = new Color(i, 0, 0, 220);
        }

        RenderUtils.drawRoundedRect((int) (res.getScaledWidth() - offset), res.getScaledHeight() - 5 - height, res.getScaledWidth(), res.getScaledHeight() - 5, 2, color);
//        RenderUtils.drawRoundedRect((int) (res.getScaledWidth() - offset), res.getScaledHeight() - 5 - height, (int) (res.getScaledWidth() - offset + 4), res.getScaledHeight() - 5, 2, color1);

        RenderUtils.drawImage(this.type.icon, (int) (res.getScaledWidth() - offset + 10), res.getScaledHeight() - 5 - height, 25, 25);

        fontRenderer.drawString(title, (int) (res.getScaledWidth() - offset + 28), res.getScaledHeight() - 2 - height, -1);
        fontRenderer.drawString(message, (int) (res.getScaledWidth() - offset + 28), res.getScaledHeight() - 15, -1);

        RenderUtils.drawLine(res.getScaledWidth(), (int) (res.getScaledWidth() - i), res.getScaledHeight() - 7, 1, new Color(0, 26, 169).getRGB(), false);
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
