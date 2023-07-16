package org.cubewhy.lunarcn.utils;

import net.minecraft.util.MouseHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class MouseUtils {
    public static int getX() {
        return Mouse.getX();
    }

    public static int getY() {
        return Mouse.getY();
    }

    public static int getEventButton() {
        return Mouse.getEventButton();
    }

    public static int getDX() {
        return Mouse.getDX();
    }

    public static int getDY() {
        return Mouse.getDY();
    }

    public static void setCursor(Cursor cursor) throws LWJGLException {
        Mouse.setNativeCursor(cursor);
    }

    public static int getDWheel() {
        return Mouse.getDWheel();
    }

    public static boolean getEventButtonState() {
        return Mouse.getEventButtonState();
    }

    public static MouseHelper getMouseHelper() {
        return mc.mouseHelper;
    }
}
