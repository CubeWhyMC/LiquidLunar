package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DoubleValue extends Value<Double> {
    public final double maxValue;
    public final double minValue;


    public DoubleValue(String name, Double value, Double maxValue, Double minValue) {
        this.name = name;
        this.value = value;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public DoubleValue(String name, Double value) {
        this(name, value, Double.MAX_VALUE, Double.MIN_VALUE);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((double) value);
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        value = jsonElement.getAsDouble();
    }
}
