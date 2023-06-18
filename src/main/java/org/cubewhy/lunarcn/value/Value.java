package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;

public abstract class Value<T> {
	protected String name;
	protected T value;

	public Value(String name, T value) {
		setName(name);
		setValue(value);
	}

	public abstract String getName();

	public abstract void setName(String name);

	public abstract T getValue();

	public abstract void setValue(T value);

	public abstract JsonElement toJson();

	public abstract void fromJson(JsonElement jsonElement);
}
