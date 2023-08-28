package org.cubewhy.lunarcn.value;

import org.lwjgl.input.Keyboard;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class KeyValue extends IntValue {
    public KeyValue(String name, int value) {
        super(name, value);
    }

    public KeyValue(String name, int value, int maxValue, int minValue) {
        super(name, value, maxValue, minValue);
    }

    /**
     * Get key state
     *
     * @return state of the key
     * */
    public boolean isPressed() {
        return Keyboard.isKeyDown(this.value);
    }
}
