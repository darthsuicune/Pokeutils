package com.suicune.pokeutils.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.suicune.pokeutils.R;

public class PokeDBOpenHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "pokeutils";
	private static final int DB_VERSION = 1;

	private static final String CREATE = "CREATE TABLE ";
	private static final String KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
	private static final String TEXT = " TEXT, ";
	private static final String INTEGER = " INTEGER, ";
	private static final String TEXT_END = " TEXT)";

	private Context mContext;

	public PokeDBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		mContext = context;
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
				+ PokeContract.PokemonName.NAME + TEXT_END);

		db.execSQL("CREATE TABLE " + PokeContract.PokemonBaseStats.TABLE_NAME
				+ " (" + PokeContract.PokemonBaseStats._ID + KEY
				+ PokeContract.PokemonBaseStats.NUMBER + TEXT
				+ PokeContract.PokemonBaseStats.FORM + TEXT
				+ PokeContract.PokemonBaseStats.BASE_ATT + TEXT
				+ PokeContract.PokemonBaseStats.BASE_DEF + TEXT
				+ PokeContract.PokemonBaseStats.BASE_HP + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SP_ATT + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SP_DEF + TEXT
				+ PokeContract.PokemonBaseStats.BASE_SPEED + TEXT_END);

		db.execSQL(CREATE + PokeContract.PokemonType1.TABLE_NAME + " ("
				+ PokeContract.PokemonType1._ID + KEY
				+ PokeContract.PokemonType1.NUMBER + TEXT
				+ PokeContract.PokemonType1.FORM + TEXT
				+ PokeContract.PokemonType1.TYPE + TEXT_END);

		db.execSQL(CREATE + PokeContract.PokemonType2.TABLE_NAME + " ("
				+ PokeContract.PokemonType2._ID + KEY
				+ PokeContract.PokemonType2.NUMBER + TEXT
				+ PokeContract.PokemonType2.FORM + TEXT
				+ PokeContract.PokemonType2.TYPE + TEXT_END);

		db.execSQL(CREATE + PokeContract.PokemonAbility1.TABLE_NAME + " ("
				+ PokeContract.PokemonAbility1._ID + KEY
				+ PokeContract.PokemonAbility1.NUMBER + TEXT
				+ PokeContract.PokemonAbility1.FORM + TEXT
				+ PokeContract.PokemonAbility1.ABILITY_1 + TEXT_END);

		db.execSQL(CREATE + PokeContract.PokemonAbility2.TABLE_NAME + " ("
				+ PokeContract.PokemonAbility2._ID + KEY
				+ PokeContract.PokemonAbility2.NUMBER + TEXT
				+ PokeContract.PokemonAbility2.FORM + TEXT
				+ PokeContract.PokemonAbility2.ABILITY_2 + TEXT_END);

		db.execSQL(CREATE + PokeContract.PokemonAbilityDW.TABLE_NAME + " ("
				+ PokeContract.PokemonAbilityDW._ID + KEY
				+ PokeContract.PokemonAbilityDW.NUMBER + TEXT
				+ PokeContract.PokemonAbilityDW.FORM + TEXT
				+ PokeContract.PokemonAbilityDW.ABILITY_DW + TEXT_END);

		db.execSQL("CREATE TABLE " + PokeContract.Abilities.TABLE_NAME + " ("
				+ PokeContract.Abilities._ID + KEY
				+ PokeContract.Abilities.DESCRIPTION + TEXT_END);

		db.execSQL("CREATE TABLE " + PokeContract.Attacks.TABLE_NAME + " ("
				+ PokeContract.Attacks._ID + KEY 
				+ PokeContract.Attacks.PP + TEXT 
				+ PokeContract.Attacks.POWER + INTEGER
				+ PokeContract.Attacks.ACCURACY + TEXT
				+ PokeContract.Attacks.CLASS + TEXT
				+ PokeContract.Attacks.GENERATION + TEXT
				+ PokeContract.Attacks.PRIORITY + TEXT
				+ PokeContract.Attacks.TYPE + TEXT_END);

		db.execSQL("CREATE TABLE " + PokeContract.PokemonAttacks.TABLE_NAME
				+ " (" + PokeContract.PokemonAttacks._ID + KEY
				+ PokeContract.PokemonAttacks.ATTACK_ID + TEXT
				+ PokeContract.PokemonAttacks.NUMBER + TEXT
				+ PokeContract.PokemonAttacks.FORM + TEXT_END);

		long time = System.currentTimeMillis();
		populateDb(db);
		time = System.currentTimeMillis() - time;
		Log.d("DATABASE POPULATION FINISHED", "" + time);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void populateDb(SQLiteDatabase db) {
		insertPokemon(db);
		insertAbilities(db);
		insertAttacks(db);
		insertPokemonAttacks(db);
	}

	/**
	 * This method retrieves the data from the context, parses it into a
	 * insertable format and inserts it into the database
	 * 
	 * @param db
	 */
	private void insertPokemon(SQLiteDatabase db) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				mContext.getResources().openRawResource(R.raw.pokemon)));
		doInsertLine(db, reader);
	}

	private void insertAbilities(SQLiteDatabase db) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				mContext.getResources().openRawResource(R.raw.abilities)));
		doInsertLine(db, reader);
	}

	private void insertAttacks(SQLiteDatabase db) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				mContext.getResources().openRawResource(R.raw.attacks)));
		doInsertLine(db, reader);
	}

	private void insertPokemonAttacks(SQLiteDatabase db) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				mContext.getResources().openRawResource(R.raw.pokemonattacks)));
		doInsertLine(db, reader);
	}

	private void doInsertLine(SQLiteDatabase db, BufferedReader reader) {
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (!TextUtils.isEmpty(line)) {
					db.execSQL(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
