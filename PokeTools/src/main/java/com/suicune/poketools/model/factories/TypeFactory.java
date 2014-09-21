package com.suicune.poketools.model.factories;

import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.gen6.Gen6Type;

/**
 * Created by denis on 20.09.14.
 */
public class TypeFactory {
	public static Type createType(int gen, int typeCode) {
		switch (gen) {
			case 6:
				return createGen6Type(typeCode);
			default:
				return null;
		}
	}

	private static Type createGen6Type(int typeCode) {
		return Gen6Type.getType(typeCode);
	}


}
