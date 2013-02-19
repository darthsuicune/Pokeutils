package com.suicune.pokeutils.database;

import android.net.Uri;

public final class PokeContract {

	protected static final String CONTENT_NAME = "com.suicune.pokeutils.database.PokeProvider";
	public static final Uri CONTENT_POKEMON = Uri.parse("content://" + CONTENT_NAME + "/" + PokemonTable.TABLE_NAME);
	public static final Uri CONTENT_ATTACK = Uri.parse("content://" + CONTENT_NAME + "/" + AttacksTable.TABLE_NAME);
	public static final Uri CONTENT_ABILITY = Uri.parse("content://" + CONTENT_NAME + "/" + AbilitiesTable.TABLE_NAME);
	public static final Uri CONTENT_NATURE = Uri.parse("content://" + CONTENT_NAME + "/" + NaturesTable.TABLE_NAME);

	public static final String DB_NAME = "pokeutils";
	public static final int DB_VERSION = 1;

	public static class PokemonTable {
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
		
		public static final String TABLE_NAME = "pokemon";
		public static final int _COUNT = 16;
		public static final String DEFAULT_ORDER = _ID + " DESC";
	}

	public static class AttacksTable {
		public static final String _ID = "_id";
		public static final String ATTACK_NAME = "attackname";
		public static final String ATTACK_PP = "attackpp";
		public static final String ATTACK_POWER = "attackpower";
		public static final String ATTACK_TARGET = "attacktarget";
		public static final String ATTACK_ACCURACY = "attackaccuracy";
		public static final String ATTACK_DESCRIPTION = "attackdescription";

		public static final String TABLE_NAME = "attacks";
		public static final int _COUNT = 7;
		public static final String DEFAULT_ORDER = _ID + " DESC";
	}

	public static class AbilitiesTable {
		public static final String _ID = "_id";
		public static final String ABILITY_NAME = "abilityname";
		public static final String ABILITY_DESCRIPTION = "abilitydescription";

		public static final String TABLE_NAME = "abilities";
		public static final int _COUNT = 3;
		public static final String DEFAULT_ORDER = _ID + " DESC";
	}

	public static class NaturesTable {
		public static final String _ID = "_id";
		public static final String NATURE_NAME = "naturename";
		public static final String STAT_UP = "statup";
		public static final String STAT_DOWN = "statdown";
		
		public static final String TABLE_NAME = "natures";
		public static final int _COUNT = 4;
		public static final String DEFAULT_ORDER = _ID + " DESC";
	}
}
