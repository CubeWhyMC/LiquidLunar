package org.cubewhy.lunarcn.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

public class ListValue extends Value {
    public String[] values;
    private String name;
    private String currentValue;

    public ListValue(String name, String[] values) {
        super(name, values);
        this.values = values;
        this.name = name;
        this.currentValue = values[1];
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getValue() {
        return currentValue;
    }

    @Override
    public void setValue(Object value) {
        this.currentValue = (String) value;
    }

    @Override
    public JsonElement toJson() {
        JsonArray json = new JsonArray();
        for (String value :
                values) {
            json.add(new JsonPrimitive(value));
        }
        return json;
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        ArrayList<String> items = new ArrayList<>();
        for (JsonElement value : jsonArray) {
            items.add(value.getAsString());
        }
        this.values = items.toArray(new String[0]);
    }
}
