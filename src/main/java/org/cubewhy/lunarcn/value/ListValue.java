package org.cubewhy.lunarcn.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListValue extends Value<String[]> {

    @Override
    public String[] getValue() {
        return value;
    }

    @Override
    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public JsonElement toJson() {
        JsonArray arr = new JsonArray();
        for (String v : value) {
            arr.add(new JsonPrimitive(v));
        }
        return arr;
    }

    @Override
    public void fromJson(@NotNull JsonElement jsonElement) {
        ArrayList<String> values = new ArrayList<>();
        for (JsonElement v : jsonElement.getAsJsonArray()) {
            values.add(v.getAsString());
        }
        this.value = values.toArray(new String[0]);
    }
}
