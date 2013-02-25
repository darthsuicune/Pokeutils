package com.suicune.pokeutils.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.suicune.pokeutils.R;

public class PokeProvider extends ContentProvider {
	PokeDBOpenHelper mDbHelper;

	protected static final String POKEDEX_PATH = "pokedex";
	private static final int POKEDEX = 0;
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
	private static final int NATURE = 19;
	private static final int NATURE_ID = 20;

	static UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME, PokeContract.CONTENT_NAME
				+ "/" + POKEDEX_PATH, POKEDEX);
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
				PokeContract.PokemonAbilityDW.TABLE_NAME, POKEMON_ABILITY_DW);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonAbilityDW.TABLE_NAME + "/#",
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
				PokeContract.Natures.TABLE_NAME, NATURE);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.Natures.TABLE_NAME + "/#", NATURE_ID);
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
					+ PokeContract.PokemonAbilityDW.TABLE_NAME;
		case POKEMON_ABILITY_DW_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonAbilityDW.TABLE_NAME;

		case ABILITY:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Abilities.TABLE_NAME;
		case ABILITY_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Abilities.TABLE_NAME;
		case NATURE:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Natures.TABLE_NAME;
		case NATURE_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Natures.TABLE_NAME;
		case ATTACK:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Attacks.TABLE_NAME;
		case ATTACK_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.Attacks.TABLE_NAME;
		default:
			throw new IllegalArgumentException(getContext().getString(
					R.string.illegal_uri)
					+ ": " + uri.toString());
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String table = "";
		switch (sUriMatcher.match(uri)) {
		case POKEMON_NAME_ID:
		case POKEMON_NAME:
			table = PokeContract.PokemonName.TABLE_NAME;
			break;
		case POKEMON_BASE_STATS_ID:
		case POKEMON_BASE_STATS:
			table = PokeContract.PokemonBaseStats.TABLE_NAME;
			break;
		case POKEMON_TYPE_1_ID:
		case POKEMON_TYPE_1:
			table = PokeContract.PokemonType1.TABLE_NAME;
			break;
		case POKEMON_TYPE_2_ID:
		case POKEMON_TYPE_2:
			table = PokeContract.PokemonType2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_1_ID:
		case POKEMON_ABILITY_1:
			table = PokeContract.PokemonAbility1.TABLE_NAME;
			break;
		case POKEMON_ABILITY_2_ID:
		case POKEMON_ABILITY_2:
			table = PokeContract.PokemonAbility2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_DW_ID:
		case POKEMON_ABILITY_DW:
			table = PokeContract.PokemonAbilityDW.TABLE_NAME;
			break;
		case ABILITY_ID:
		case ABILITY:
			table = PokeContract.Abilities.TABLE_NAME;
			break;
		case NATURE_ID:
		case NATURE:
			table = PokeContract.Natures.TABLE_NAME;
			break;
		case ATTACK_ID:
		case ATTACK:
			table = PokeContract.Attacks.TABLE_NAME;
			break;
		}
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

		boolean distinct = false;
		String table = null;
		String groupBy = null;
		String having = null;
		String limit = null;

		switch (sUriMatcher.match(uri)) {
		case POKEDEX:
			
			break;
		
		case POKEMON_NAME_ID:
		case POKEMON_NAME:
			table = PokeContract.PokemonName.TABLE_NAME;
			break;
		case POKEMON_BASE_STATS_ID:
		case POKEMON_BASE_STATS:
			table = PokeContract.PokemonBaseStats.TABLE_NAME;
			break;
		case POKEMON_TYPE_1_ID:
		case POKEMON_TYPE_1:
			table = PokeContract.PokemonType1.TABLE_NAME;
			break;
		case POKEMON_TYPE_2_ID:
		case POKEMON_TYPE_2:
			table = PokeContract.PokemonType2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_1_ID:
		case POKEMON_ABILITY_1:
			table = PokeContract.PokemonAbility1.TABLE_NAME;
			break;
		case POKEMON_ABILITY_2_ID:
		case POKEMON_ABILITY_2:
			table = PokeContract.PokemonAbility2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_DW_ID:
		case POKEMON_ABILITY_DW:
			table = PokeContract.PokemonAbilityDW.TABLE_NAME;
			break;
		case ABILITY_ID:
		case ABILITY:
			table = PokeContract.Abilities.TABLE_NAME;
			break;
		case NATURE_ID:
		case NATURE:
			table = PokeContract.Natures.TABLE_NAME;
			break;
		case ATTACK_ID:
		case ATTACK:
			table = PokeContract.Attacks.TABLE_NAME;
			break;
		}
		Cursor cursor = mDbHelper.getReadableDatabase().query(distinct, table,
				projection, selection, selectionArgs, groupBy, having,
				sortOrder, limit);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String table = null;
		switch (sUriMatcher.match(uri)) {
		case POKEMON_NAME_ID:
		case POKEMON_NAME:
			table = PokeContract.PokemonName.TABLE_NAME;
			break;
		case POKEMON_BASE_STATS_ID:
		case POKEMON_BASE_STATS:
			table = PokeContract.PokemonBaseStats.TABLE_NAME;
			break;
		case POKEMON_TYPE_1_ID:
		case POKEMON_TYPE_1:
			table = PokeContract.PokemonType1.TABLE_NAME;
			break;
		case POKEMON_TYPE_2_ID:
		case POKEMON_TYPE_2:
			table = PokeContract.PokemonType2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_1_ID:
		case POKEMON_ABILITY_1:
			table = PokeContract.PokemonAbility1.TABLE_NAME;
			break;
		case POKEMON_ABILITY_2_ID:
		case POKEMON_ABILITY_2:
			table = PokeContract.PokemonAbility2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_DW_ID:
		case POKEMON_ABILITY_DW:
			table = PokeContract.PokemonAbilityDW.TABLE_NAME;
			break;
		case ABILITY_ID:
		case ABILITY:
			table = PokeContract.Abilities.TABLE_NAME;
			break;
		case NATURE_ID:
		case NATURE:
			table = PokeContract.Natures.TABLE_NAME;
			break;
		case ATTACK_ID:
		case ATTACK:
			table = PokeContract.Attacks.TABLE_NAME;
			break;
		}
		int count = mDbHelper.getWritableDatabase().update(table, values,
				selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = null;
		switch (sUriMatcher.match(uri)) {
		case POKEMON_NAME_ID:
		case POKEMON_NAME:
			table = PokeContract.PokemonName.TABLE_NAME;
			break;
		case POKEMON_BASE_STATS_ID:
		case POKEMON_BASE_STATS:
			table = PokeContract.PokemonBaseStats.TABLE_NAME;
			break;
		case POKEMON_TYPE_1_ID:
		case POKEMON_TYPE_1:
			table = PokeContract.PokemonType1.TABLE_NAME;
			break;
		case POKEMON_TYPE_2_ID:
		case POKEMON_TYPE_2:
			table = PokeContract.PokemonType2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_1_ID:
		case POKEMON_ABILITY_1:
			table = PokeContract.PokemonAbility1.TABLE_NAME;
			break;
		case POKEMON_ABILITY_2_ID:
		case POKEMON_ABILITY_2:
			table = PokeContract.PokemonAbility2.TABLE_NAME;
			break;
		case POKEMON_ABILITY_DW_ID:
		case POKEMON_ABILITY_DW:
			table = PokeContract.PokemonAbilityDW.TABLE_NAME;
			break;
		case ABILITY_ID:
		case ABILITY:
			table = PokeContract.Abilities.TABLE_NAME;
			break;
		case NATURE_ID:
		case NATURE:
			table = PokeContract.Natures.TABLE_NAME;
			break;
		case ATTACK_ID:
		case ATTACK:
			table = PokeContract.Attacks.TABLE_NAME;
			break;
		}
		int count = mDbHelper.getWritableDatabase().delete(table, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
