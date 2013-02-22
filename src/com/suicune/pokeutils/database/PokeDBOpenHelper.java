package com.suicune.pokeutils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PokeDBOpenHelper extends SQLiteOpenHelper {
	private static final String key = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
	private static final String text = " TEXT, ";
	private static final String textEnd = " TEXT";

	public PokeDBOpenHelper(Context context) {
		super(context, PokeContract.DB_NAME, null, PokeContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db.isReadOnly()) {
			db = getWritableDatabase();
		}

		db.execSQL("CREATE TABLE " + PokeContract.PokemonTable.TABLE_NAME
				+ " (" 
				+ PokeContract.PokemonTable._ID + key
				+ PokeContract.PokemonTable.ABILITY_1 + text
				+ PokeContract.PokemonTable.ABILITY_2 + text
				+ PokeContract.PokemonTable.ABILITY_DW + text
				+ PokeContract.PokemonTable.BASE_EV_AMOUNT + text
				+ PokeContract.PokemonTable.BASE_EV_TYPE + text
				+ PokeContract.PokemonTable.BASE_STAT_ATT + text
				+ PokeContract.PokemonTable.BASE_STAT_DEF + text
				+ PokeContract.PokemonTable.BASE_STAT_HP + text
				+ PokeContract.PokemonTable.BASE_STAT_SPATT + text
				+ PokeContract.PokemonTable.BASE_STAT_SPDEF + text
				+ PokeContract.PokemonTable.BASE_STAT_SPEED + text
				+ PokeContract.PokemonTable.POKEMON_NAME + text
				+ PokeContract.PokemonTable.POKEMON_NUMBER + text
				+ PokeContract.PokemonTable.POKEMON_FORM + text
				+ PokeContract.PokemonTable.TYPE_1 + text
				+ PokeContract.PokemonTable.TYPE_2 + textEnd
				+ ")");

		db.execSQL("CREATE TABLE " + PokeContract.NaturesTable.TABLE_NAME
				+ " (" 
				+ PokeContract.NaturesTable._ID + key
				+ PokeContract.NaturesTable.NATURE_NAME + text
				+ PokeContract.NaturesTable.STAT_DOWN + text
				+ PokeContract.NaturesTable.STAT_UP + textEnd
				+ ")");
		
		db.execSQL("CREATE TABLE " + PokeContract.AbilitiesTable.TABLE_NAME
				+ " (" 
				+ PokeContract.AbilitiesTable._ID + key 
				+ PokeContract.AbilitiesTable.ABILITY_NAME + text
				+ PokeContract.AbilitiesTable.ABILITY_DESCRIPTION + textEnd
				+ ")");
		
		db.execSQL("CREATE TABLE " + PokeContract.AttacksTable.TABLE_NAME
				+ " (" 
				+ PokeContract.AttacksTable._ID + key 
				+ PokeContract.AttacksTable.ATTACK_NAME + textEnd
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
