package com.suicune.poketools.model.factories;

import com.suicune.poketools.model.Pokemon;

/**
 * Created by lapuente on 17.09.14.
 */
public class PokemonFactory {
	public static Pokemon createPokemon(int gen, int dexNumber, int form) {
		switch(gen) {
			case 6:
				return createGen6Pokemon(dexNumber, form);
			default:
				return null;
		}
	}
	private static Pokemon createGen6Pokemon(int dexNumber, int form) {
		//TODO: Create pokemon
		return null;
	}
}
