package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class KeyValue extends Value<Integer> {

    public KeyValue(String name, Integer value) {
        super(name, value);
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        value = jsonElement.getAsInt();
    }
}
