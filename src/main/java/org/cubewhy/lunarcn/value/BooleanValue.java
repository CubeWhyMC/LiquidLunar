package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanValue extends Value {

	public BooleanValue(String name, boolean value) {
		super(name, value);
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
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public JsonElement toJson() {
		return new JsonPrimitive((boolean) value);
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		value = jsonElement.getAsBoolean();
	}
}
