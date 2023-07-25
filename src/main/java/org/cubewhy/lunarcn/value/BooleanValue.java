package org.cubewhy.lunarcn.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public class BooleanValue extends Value<Boolean> {

	public BooleanValue(String name, boolean value) {
		super(name, value);
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public @NotNull JsonElement toJson() {
		return new JsonPrimitive(value);
	}

	@Override
	public void fromJson(@NotNull JsonElement jsonElement) {
		value = jsonElement.getAsBoolean();
	}
}
