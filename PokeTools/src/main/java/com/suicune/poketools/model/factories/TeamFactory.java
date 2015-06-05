package com.suicune.poketools.model.factories;

import com.suicune.poketools.model.PokemonTeam;
import com.suicune.poketools.model.gen6.Gen6Team;

public class TeamFactory {
	public static PokemonTeam create(int gen) {
		switch(gen) {
			case 6:
				return createGen6Team();
			default:
				return null;
		}
	}

	private static PokemonTeam createGen6Team() {
		return new Gen6Team();
	}
}
