package com.suicune.pokeutils.database;

import android.net.Uri;

public final class PokeContract {

	public static final String CONTENT_NAME = "";
	public static final Uri CONTENT_POKEMON = Uri.parse("");
	public static final Uri CONTENT_ATTACK = Uri.parse("");
	public static final Uri CONTENT_ABILITY = Uri.parse("");

	public static final String DB_NAME = "pokeutils";
	public static final int DB_VERSION = 1;

	public static class PokemonTable {
		public static final String TABLE_NAME = "pokemon";
		public static final int _COUNT = 16;
		public static final String DEFAULT_ORDER = "";

		public static final String _ID = "_id";
		public static final String POKEMON_NAME = "pokename";
		public static final String POKEMON_NUMBER = "pokedex";
		public static final String TYPE_1 = "typeone";
		public static final String TYPE_2 = "typetwo";
		public static final String ABILITY_1 = "abilityone";
		public static final String ABILITY_2 = "abilitytwo";
		public static final String ABILITY_DW = "abilitydw";
		public static final String BASE_STAT_HP = "basehp";
		public static final String BASE_STAT_ATT = "baseatt";
		public static final String BASE_STAT_DEF = "basedef";
		public static final String BASE_STAT_SPATT = "basespatt";
		public static final String BASE_STAT_SPDEF = "basespdef";
		public static final String BASE_STAT_SPEED = "basespeed";
		public static final String BASE_EV_AMOUNT = "evamount";
		public static final String BASE_EV_TYPE = "evtype";
	}

	public static class AttacksTable {

	}

	public static class AbilitiesTable {

	}

	public static class NaturesTable {
		public static final String _ID = "_id";
		public static final String NATURE_NAME = "nature";
		public static final String STAT_UP = "statup";
		public static final String STAT_DOWN = "statdown";
	}
}
