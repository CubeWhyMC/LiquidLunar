package org.cubewhy.lunarcn.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

public class StringValues extends Value<String> {
    public String[] values;
    private String currentValue;

    public StringValues(String name, String[] values, String defaultValue) {
        super(name, defaultValue);
        this.values = values;
        this.name = name;
        this.currentValue = values[0];
    }

    public StringValues(String name, String[] values) {
        this(name, values, values[0]);
    }

    @Override
    public String getValue() {
        return currentValue;
    }

    @Override
    public void setValue(String value) {
        this.currentValue = value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(currentValue);
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        this.currentValue =  jsonElement.getAsString();
    }
}
