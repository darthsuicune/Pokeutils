package com.suicune.pokeutils.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class PokeContract {

	protected static final String CONTENT_NAME = "com.suicune.pokeutils.database.PokeProvider";

	public static class Pokedex {
		public static final Uri CONTENT_POKEDEX = Uri.parse("content://"
				+ CONTENT_NAME + "/" + PokeProvider.POKEDEX_PATH);

		public static final String ABILITY_1_NAME = "abilityname1";
		public static final String ABILITY_2_NAME = "abilityname2";
		public static final String ABILITY_DW_NAME = "abilitynamedw";
		public static final String ABILITY_1_DESCRIPTION = "abilitydesc1";
		public static final String ABILITY_2_DESCRIPTION = "abilitydesc2";
		public static final String ABILITY_DW_DESCRIPTION = "abilitydescdw";

		private Pokedex() {
		}
	}

	public static class PokemonName implements BaseColumns {
		public static final String TABLE_NAME = "pokemonname";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_NAME = Uri.parse("content://"
				+ CONTENT_NAME + "/" + TABLE_NAME);

		public static final String NAME = "pokemonname";
		public static final String FORM = "pokemonform";
		public static final String NUMBER = "pokemonnumber";

		private PokemonName() {
		}
	}

	public static class PokemonType1 implements BaseColumns {
		public static final String TABLE_NAME = "pokemontypeone";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_TYPE_1 = Uri.parse("content://"
				+ CONTENT_NAME + "/" + TABLE_NAME);

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String TYPE = "pokemontypeone";

		private PokemonType1() {
		}
	}

	public static class PokemonType2 implements BaseColumns {
		public static final String TABLE_NAME = "pokemontypetwo";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_TYPE_2 = Uri.parse("content://"
				+ CONTENT_NAME + "/" + TABLE_NAME);

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String TYPE = "pokemontypetwo";

		private PokemonType2() {
		}
	}

	public static class PokemonBaseStats implements BaseColumns {
		public static final String TABLE_NAME = "pokemonbasestats";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_BASE_STATS = Uri
				.parse("content://" + CONTENT_NAME + "/" + TABLE_NAME);

		private PokemonBaseStats() {
		}

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String BASE_HP = "pokemonbasehp";
		public static final String BASE_ATT = "pokemonbaseatt";
		public static final String BASE_DEF = "pokemonbasedef";
		public static final String BASE_SPATT = "pokemonbasespatt";
		public static final String BASE_SPDEF = "pokemonbasespdef";
		public static final String BASE_SPEED = "pokemonbasespeed";
	}

	public static class PokemonAbility1 implements BaseColumns {
		public static final String TABLE_NAME = "pokemonabilityone";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_ABILITY_1 = Uri
				.parse("content://" + CONTENT_NAME + "/" + TABLE_NAME);

		private PokemonAbility1() {
		}

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String ABILITY_1 = "pokemonabilityone";
	}

	public static class PokemonAbility2 implements BaseColumns {
		public static final String TABLE_NAME = "pokemonabilitytwo";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_ABILITY_2 = Uri
				.parse("content://" + CONTENT_NAME + "/" + TABLE_NAME);

		private PokemonAbility2() {
		}

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String ABILITY_2 = "pokemonabilitytwo";
	}

	public static class PokemonAbilityDW implements BaseColumns {
		public static final String TABLE_NAME = "pokemonabilitydw";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_ABILITY_DW = Uri
				.parse("content://" + CONTENT_NAME + "/" + TABLE_NAME);

		private PokemonAbilityDW() {
		}

		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String ABILITY_DW = "pokemonabilitydw";
	}

	public static class PokemonAttacks implements BaseColumns {
		public static final String TABLE_NAME = "pokemonattacks";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_POKEMON_ATTACKS = Uri
				.parse("content://" + CONTENT_NAME + "/" + TABLE_NAME);
		
		public static final String NUMBER = "pokemonnumber";
		public static final String FORM = "pokemonform";
		public static final String ATTACK_ID = "attackid";
	}

	public static class Attacks implements BaseColumns {
		public static final String TABLE_NAME = "attacks";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_ATTACK = Uri.parse("content://"
				+ CONTENT_NAME + "/" + TABLE_NAME);

		public static final String PP = "attackpp";
		public static final String POWER = "attackpower";
		public static final String ACCURACY = "attackaccuracy";
		public static final String PRIORITY = "attackpriority";
		public static final String TYPE = "attacktype";
		public static final String CLASS = "attackclass";
		public static final String GENERATION = "attackgeneration";
	}

	public static class Abilities implements BaseColumns {
		public static final String TABLE_NAME = "abilities";
		public static final String DEFAULT_ORDER = _ID + " DESC";

		public static final Uri CONTENT_ABILITY = Uri.parse("content://"
				+ CONTENT_NAME + "/" + TABLE_NAME);

		public static final String DESCRIPTION = "abilitydescription";
	}
}
