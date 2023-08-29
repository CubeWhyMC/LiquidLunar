package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;

public abstract class Value<T> {
	protected String name;
	protected T value;

	public Value() {
	}

	public Value(String name, T value) {
		setName(name);
		setValue(value);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public abstract JsonElement toJson();

	public abstract void fromJson(JsonElement jsonElement);
}
