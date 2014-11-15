package com.suicune.poketools.model.factories;

import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.gen6.Gen6Type;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by denis on 20.09.14.
 */
public class TypeFactory {
	public static Type createType(int gen, int typeCode) {
		switch (gen) {
			case 6:
			default:
				return createGen6Type(typeCode);
		}
	}

	private static Type createGen6Type(int typeCode) {
		return Gen6Type.getType(typeCode);
	}

	public static Type[] fromJSON(int gen, JSONArray array) throws JSONException {
		switch (gen) {
			case 6:
			default:
				return createGen6TypeArray(array);
		}
	}

	private static Type[] createGen6TypeArray(JSONArray typeArray) throws JSONException {
		Type[] types = new Type[2];
		types[0] = TypeFactory.createType(6, typeArray.getInt(0));
		types[1] = TypeFactory.createType(6, typeArray.getInt(1));
		return types;
	}

	public static Type getDefault(int gen) {
		switch (gen) {
			case 6:
			default:
				return Gen6Type.NONE;
		}
	}
}
