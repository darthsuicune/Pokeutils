package com.suicune.pokeutils.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.suicune.pokeutils.R;

public class PokeProvider extends ContentProvider {
	PokeDBOpenHelper mDbHelper;

	protected static final String POKEDEX_PATH = "pokedex";
	private static final int POKEDEX = 100;
	private static final int POKEMON_NAME = 1;
	private static final int POKEMON_NAME_ID = 2;
	private static final int POKEMON_BASE_STATS = 3;
	private static final int POKEMON_BASE_STATS_ID = 4;
	private static final int POKEMON_TYPE_1 = 5;
	private static final int POKEMON_TYPE_1_ID = 6;
	private static final int POKEMON_TYPE_2 = 7;
	private static final int POKEMON_TYPE_2_ID = 8;
	private static final int POKEMON_ABILITY_1 = 9;
	private static final int POKEMON_ABILITY_1_ID = 10;
	private static final int POKEMON_ABILITY_2 = 11;
	private static final int POKEMON_ABILITY_2_ID = 12;
	private static final int POKEMON_ABILITY_DW = 13;
	private static final int POKEMON_ABILITY_DW_ID = 14;
	private static final int ATTACK = 15;
	private static final int ATTACK_ID = 16;
	private static final int ABILITY = 17;
	private static final int ABILITY_ID = 18;
	private static final int POKEMON_ATTACK = 19;
	private static final int POKEMON_ATTACK_ID = 20;

	static UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME, POKEDEX_PATH, POKEDEX);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonName.TABLE_NAME, POKEMON_NAME);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonName.TABLE_NAME + "/#", POKEMON_NAME_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonBaseStats.TABLE_NAME, POKEMON_BASE_STATS);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonBaseStats.TABLE_NAME + "/#",
				POKEMON_BASE_STATS_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonType1.TABLE_NAME, POKEMON_TYPE_1);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonType1.TABLE_NAME + "/#", POKEMON_TYPE_1_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonType2.TABLE_NAME, POKEMON_TYPE_2);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonType2.TABLE_NAME + "/#", POKEMON_TYPE_2_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbility1.TABLE_NAME, POKEMON_ABILITY_1);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbility1.TABLE_NAME + "/#",
				POKEMON_ABILITY_1_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbility2.TABLE_NAME, POKEMON_ABILITY_2);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbility2.TABLE_NAME + "/#",
				POKEMON_ABILITY_2_ID);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbilityHidden.TABLE_NAME, POKEMON_ABILITY_DW);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbilityHidden.TABLE_NAME + "/#",
				POKEMON_ABILITY_DW_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.Attacks.TABLE_NAME, ATTACK);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.Attacks.TABLE_NAME + "/#", ATTACK_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.Abilities.TABLE_NAME, ABILITY);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.Abilities.TABLE_NAME + "/#", ABILITY_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAttacks.TABLE_NAME, POKEMON_ATTACK);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAttacks.TABLE_NAME + "/#",
				POKEMON_ATTACK_ID);
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new PokeDBOpenHelper(getContext());
		return mDbHelper != null;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {

		case POKEDEX:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "." + POKEDEX_PATH;
		case POKEMON_NAME:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonName.TABLE_NAME;
		case POKEMON_NAME_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonName.TABLE_NAME;
		case POKEMON_BASE_STATS:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonBaseStats.TABLE_NAME;
		case POKEMON_BASE_STATS_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonBaseStats.TABLE_NAME;
		case POKEMON_TYPE_1:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonType1.TABLE_NAME;
		case POKEMON_TYPE_1_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonType1.TABLE_NAME;
		case POKEMON_TYPE_2:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonType2.TABLE_NAME;
		case POKEMON_TYPE_2_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonType2.TABLE_NAME;
		case POKEMON_ABILITY_1:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbility1.TABLE_NAME;
		case POKEMON_ABILITY_1_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbility1.TABLE_NAME;
		case POKEMON_ABILITY_2:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbility2.TABLE_NAME;
		case POKEMON_ABILITY_2_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbility2.TABLE_NAME;
		case POKEMON_ABILITY_DW:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbilityHidden.TABLE_NAME;
		case POKEMON_ABILITY_DW_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbilityHidden.TABLE_NAME;

		case ABILITY:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Abilities.TABLE_NAME;
		case ABILITY_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Abilities.TABLE_NAME;
		case ATTACK:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Attacks.TABLE_NAME;
		case ATTACK_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Attacks.TABLE_NAME;
		case POKEMON_ATTACK:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAttacks.TABLE_NAME;
		case POKEMON_ATTACK_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAttacks.TABLE_NAME;
		default:
			throw new IllegalArgumentException(getContext().getString(
					R.string.illegal_uri)
					+ ": " + uri.toString());
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String table = getTable(uri);
		long id = mDbHelper.getWritableDatabase().insert(table, null, values);
		Uri result = null;
		if (id != -1) {
			result = ContentUris.withAppendedId(uri, id);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		getContext().getContentResolver().notifyChange(result, null);
		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		if (sUriMatcher.match(uri) == POKEDEX) {
			return queryPokedex(projection, selection, selectionArgs, sortOrder);
		} else if (sUriMatcher.match(uri) == POKEMON_ATTACK) {
			return queryAttack(projection, selection, selectionArgs, sortOrder);
		}

		boolean distinct = false;
		String table = getTable(uri);
		String groupBy = null;
		String having = null;
		String limit = null;
		Cursor cursor = mDbHelper.getReadableDatabase().query(distinct, table,
				projection, selection, selectionArgs, groupBy, having,
				sortOrder, limit);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	private Cursor queryAttack(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		builder.setTables(PokeContract.PokemonAttacks.TABLE_NAME + " JOIN "
				+ PokeContract.Attacks.TABLE_NAME + " ON "
				+ PokeContract.PokemonAttacks.TABLE_NAME + "."
				+ PokeContract.PokemonAttacks.ATTACK_ID + "="
				+ PokeContract.Attacks.TABLE_NAME + "."
				+ PokeContract.Attacks._ID);

		if (sortOrder == null) {
			sortOrder = PokeContract.Attacks.POWER + " DESC";
		}

		return builder.query(mDbHelper.getReadableDatabase(), projection,
				selection, selectionArgs, null, null, sortOrder);
	}

	private Cursor queryPokedex(String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		builder.setTables(PokeContract.PokemonName.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonBaseStats.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonAbility1.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonAbility2.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonAbilityHidden.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonType1.TABLE_NAME
				+ " NATURAL JOIN " + PokeContract.PokemonType2.TABLE_NAME
				+ " JOIN " + PokeContract.Abilities.TABLE_NAME
				+ " AS A1 ON A1." + PokeContract.Abilities._ID + "="
				+ PokeContract.PokemonAbility1.ABILITY_1 + " JOIN "
				+ PokeContract.Abilities.TABLE_NAME + " AS A2 ON A2."
				+ PokeContract.Abilities._ID + "="
				+ PokeContract.PokemonAbility2.ABILITY_2 + " JOIN "
				+ PokeContract.Abilities.TABLE_NAME + " AS ADW ON ADW."
				+ PokeContract.Abilities._ID + "="
				+ PokeContract.PokemonAbilityHidden.ABILITY_DW);

		if (sortOrder == null) {
			sortOrder = PokeContract.PokemonName.TABLE_NAME + "."
					+ PokeContract.PokemonName._ID + " ASC";
		}
		if (projection == null) {
			String[] myProjection = {
					PokeContract.PokemonName.TABLE_NAME + "."
							+ PokeContract.PokemonName._ID,
					PokeContract.PokemonName.NAME,
					PokeContract.PokemonName.TABLE_NAME + "."
							+ PokeContract.PokemonName.NUMBER,
					PokeContract.PokemonName.TABLE_NAME + "."
							+ PokeContract.PokemonName.FORM,
					PokeContract.PokemonBaseStats.BASE_HP,
					PokeContract.PokemonBaseStats.BASE_ATT,
					PokeContract.PokemonBaseStats.BASE_DEF,
					PokeContract.PokemonBaseStats.BASE_SP_ATT,
					PokeContract.PokemonBaseStats.BASE_SP_DEF,
					PokeContract.PokemonBaseStats.BASE_SPEED,
					PokeContract.PokemonAbility1.ABILITY_1,
					PokeContract.PokemonAbility2.ABILITY_2,
					PokeContract.PokemonAbilityHidden.ABILITY_DW,
					PokeContract.PokemonType1.TYPE,
					PokeContract.PokemonType2.TYPE,
					"A1." + PokeContract.Abilities.DESCRIPTION + " AS "
							+ PokeContract.Pokedex.ABILITY_1_DESCRIPTION,
					"A2." + PokeContract.Abilities.DESCRIPTION + " AS "
							+ PokeContract.Pokedex.ABILITY_2_DESCRIPTION,
					"ADW." + PokeContract.Abilities.DESCRIPTION + " AS "
							+ PokeContract.Pokedex.ABILITY_HIDDEN_DESCRIPTION};
			return builder.query(mDbHelper.getReadableDatabase(), myProjection,
					selection, selectionArgs, null, null, sortOrder);
		} else {
			return builder.query(mDbHelper.getReadableDatabase(), projection,
					selection, selectionArgs, null, null, sortOrder);
		}

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String table = getTable(uri);
		int count = mDbHelper.getWritableDatabase().update(table, values,
				selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = getTable(uri);

		int count = mDbHelper.getWritableDatabase().delete(table, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	private String getTable(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case POKEMON_NAME_ID:
		case POKEMON_NAME:
			return PokeContract.PokemonName.TABLE_NAME;
		case POKEMON_BASE_STATS_ID:
		case POKEMON_BASE_STATS:
			return PokeContract.PokemonBaseStats.TABLE_NAME;
		case POKEMON_TYPE_1_ID:
		case POKEMON_TYPE_1:
			return PokeContract.PokemonType1.TABLE_NAME;
		case POKEMON_TYPE_2_ID:
		case POKEMON_TYPE_2:
			return PokeContract.PokemonType2.TABLE_NAME;
		case POKEMON_ABILITY_1_ID:
		case POKEMON_ABILITY_1:
			return PokeContract.PokemonAbility1.TABLE_NAME;
		case POKEMON_ABILITY_2_ID:
		case POKEMON_ABILITY_2:
			return PokeContract.PokemonAbility2.TABLE_NAME;
		case POKEMON_ABILITY_DW_ID:
		case POKEMON_ABILITY_DW:
			return PokeContract.PokemonAbilityHidden.TABLE_NAME;
		case ABILITY_ID:
		case ABILITY:
			return PokeContract.Abilities.TABLE_NAME;
		case ATTACK_ID:
		case ATTACK:
			return PokeContract.Attacks.TABLE_NAME;
		case POKEMON_ATTACK_ID:
		case POKEMON_ATTACK:
			return PokeContract.PokemonAttacks.TABLE_NAME;
		}
		return null;
	}
}
