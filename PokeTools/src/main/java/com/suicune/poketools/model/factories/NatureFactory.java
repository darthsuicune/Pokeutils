package com.suicune.poketools.model.factories;

import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.gen6.Gen6Nature;

/**
 * Created by denis on 15.11.14.
 */
public class NatureFactory {
	public static Nature get(int gen, int index) {
		switch (gen) {
			case 6:
			default:
				return Gen6Nature.values()[index];
		}
	}

	public static Nature getDefault(int gen) {
		switch (gen) {
			case 6:
			default:
				return Gen6Nature.HARDY;
		}
	}
}
