package org.cubewhy.lunarcn.gui.hud;

import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class ScreenPosition {
    private double x, y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public void setRelative(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAbsolute(int x, int y) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.x = (double) x / scaledResolution.getScaledWidth();
        this.y = (double) y / scaledResolution.getScaledHeight();
    }

    public int getAbsoluteX() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        return (int) (x * scaledResolution.getScaledWidth());
    }

    public int getAbsoluteY() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        return (int) (y * scaledResolution.getScaledHeight());
    }

    public double getRelativeX() {
        return x;
    }

    public double getRelativeY() {
        return y;
    }
}
