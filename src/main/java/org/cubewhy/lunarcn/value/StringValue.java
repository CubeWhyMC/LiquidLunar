package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class StringValue extends Value<String> {

	public StringValue(String name, String value) {
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
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public JsonElement toJson() {
		return new JsonPrimitive((String) value);
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		value = jsonElement.getAsString();
	}
}
