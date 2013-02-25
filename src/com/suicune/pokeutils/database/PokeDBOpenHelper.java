package com.suicune.pokeutils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PokeDBOpenHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "pokeutils";
	private static final int DB_VERSION = 1;

	private static final String CREATE = "CREATE TABLE ";
	private static final String KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
	private static final String TEXT = " TEXT, ";
	private static final String TEXT_END = " TEXT";

	public PokeDBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db.isReadOnly()) {
			db = getWritableDatabase();
		}

		db.execSQL(CREATE + PokeContract.PokemonName.TABLE_NAME + " ("
				+ PokeContract.PokemonName._ID + KEY
				+ PokeContract.PokemonName.NUMBER + TEXT
				+ PokeContract.PokemonName.FORM + TEXT
				+ PokeContract.PokemonName.NAME + TEXT_END
				+ ")");
		db.execSQL("CREATE TABLE " + PokeContract.PokemonBaseStats.TABLE_NAME
				+ " (" + PokeContract.PokemonBaseStats._ID + KEY
				+ PokeContract.PokemonBaseStats.NUMBER + TEXT
				+ PokeContract.PokemonBaseStats.FORM + TEXT
				+ PokeContract.PokemonBaseStats.BASE_ATT + TEXT
				+ PokeContract.PokemonBaseStats.BASE_DEF + TEXT
				+ PokeContract.PokemonBaseStats.BASE_HP + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SPATT + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SPDEF + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SPEED + TEXT_END
				+ ")");
		db.execSQL(CREATE + PokeContract.PokemonType1.TABLE_NAME + " ("
				+ PokeContract.PokemonType1._ID + KEY
				+ PokeContract.PokemonType1.NUMBER + TEXT
				+ PokeContract.PokemonType1.FORM + TEXT
				+ PokeContract.PokemonType1.TYPE + TEXT_END
				+ ")");
		db.execSQL(CREATE + PokeContract.PokemonType2.TABLE_NAME + " ("
				+ PokeContract.PokemonType2._ID + KEY
				+ PokeContract.PokemonType2.NUMBER + TEXT
				+ PokeContract.PokemonType2.FORM + TEXT
				+ PokeContract.PokemonType2.TYPE + TEXT_END
				+ ")");
		db.execSQL(CREATE + PokeContract.PokemonAbility1.TABLE_NAME + " ("
				+ PokeContract.PokemonAbility1._ID + KEY
				+ PokeContract.PokemonAbility1.NUMBER + TEXT
				+ PokeContract.PokemonAbility1.FORM + TEXT
				+ PokeContract.PokemonAbility1.ABILITY_1 + TEXT_END
				+ ")");
		db.execSQL(CREATE + PokeContract.PokemonAbility2.TABLE_NAME + " ("
				+ PokeContract.PokemonAbility2._ID + KEY
				+ PokeContract.PokemonAbility2.NUMBER + TEXT
				+ PokeContract.PokemonAbility2.FORM + TEXT
				+ PokeContract.PokemonAbility2.ABILITY_2 + TEXT_END
				+ ")");
		db.execSQL(CREATE + PokeContract.PokemonAbilityDW.TABLE_NAME + " ("
				+ PokeContract.PokemonAbilityDW._ID + KEY
				+ PokeContract.PokemonAbilityDW.NUMBER + TEXT
				+ PokeContract.PokemonAbilityDW.FORM + TEXT
				+ PokeContract.PokemonAbilityDW.ABILITY_DW + TEXT_END
				+ ")");

		db.execSQL("CREATE TABLE " + PokeContract.Natures.TABLE_NAME + " ("
				+ PokeContract.Natures._ID + KEY
				+ PokeContract.Natures.ID + TEXT
				+ PokeContract.Natures.NAME	+ TEXT
				+ PokeContract.Natures.STAT_DOWN + TEXT
				+ PokeContract.Natures.STAT_UP + TEXT_END
				+ ")");

		db.execSQL("CREATE TABLE " + PokeContract.Abilities.TABLE_NAME + " ("
				+ PokeContract.Abilities._ID + KEY
				+ PokeContract.Abilities.ID + TEXT
				+ PokeContract.Abilities.NAME + TEXT
				+ PokeContract.Abilities.DESCRIPTION + TEXT_END
				+ ")");

		db.execSQL("CREATE TABLE " + PokeContract.Attacks.TABLE_NAME + " ("
				+ PokeContract.Attacks._ID + KEY
				+ PokeContract.Attacks.NAME	+ TEXT_END
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
