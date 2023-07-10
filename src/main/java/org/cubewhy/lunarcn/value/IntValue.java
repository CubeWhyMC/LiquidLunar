package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntValue extends Value<Integer> {
	public final int maxValue;
	public final int minValue;


	public IntValue(String name, int value) {
		this(name, value, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public IntValue(String name, int value, int maxValue,  int minValue) {
		super(name, value);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public JsonElement toJson() {
		return new JsonPrimitive((int) value);
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		value = jsonElement.getAsInt();
	}
}
