package com.suicune.pokeutils.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.suicune.pokeutils.R;

public class PokeProvider extends ContentProvider {
	PokeDBOpenHelper mDbHelper;

	private static final int POKEMON = 1;
	private static final int POKEMON_ID = 2;
	private static final int ATTACK = 3;
	private static final int ATTACK_ID = 4;
	private static final int ABILITY = 5;
	private static final int ABILITY_ID = 6;
	private static final int NATURE = 7;
	private static final int NATURE_ID = 8;

	static UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonTable.TABLE_NAME, POKEMON);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.PokemonTable.TABLE_NAME + "/#", POKEMON_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.AttacksTable.TABLE_NAME, ATTACK);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.AttacksTable.TABLE_NAME + "/#", ATTACK_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.AbilitiesTable.TABLE_NAME, ABILITY);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.AbilitiesTable.TABLE_NAME + "/#", ABILITY_ID);

		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.NaturesTable.TABLE_NAME, NATURE);
		sUriMatcher.addURI(PokeContract.CONTENT_NAME,
				PokeContract.NaturesTable.TABLE_NAME + "/#", NATURE_ID);
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new PokeDBOpenHelper(getContext());
		return mDbHelper != null;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {

		case POKEMON:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonTable.TABLE_NAME;
		case POKEMON_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.PokemonTable.TABLE_NAME;
		case ABILITY:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.AbilitiesTable.TABLE_NAME;
		case ABILITY_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.AbilitiesTable.TABLE_NAME;
		case NATURE:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.NaturesTable.TABLE_NAME;
		case NATURE_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.NaturesTable.TABLE_NAME;
		case ATTACK:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.AttacksTable.TABLE_NAME;
		case ATTACK_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE
					+ PokeContract.CONTENT_NAME + "."
					+ PokeContract.AttacksTable.TABLE_NAME;
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
		case POKEMON:
			table = PokeContract.PokemonTable.TABLE_NAME;
		case POKEMON_ID:
			break;
		case ABILITY:
			table = PokeContract.AbilitiesTable.TABLE_NAME;
		case ABILITY_ID:
			break;
		case NATURE:
			table = PokeContract.NaturesTable.TABLE_NAME;
		case NATURE_ID:
			break;
		case ATTACK:
			table = PokeContract.AttacksTable.TABLE_NAME;
		case ATTACK_ID:
			break;
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long id = db.insert(table, null, values);
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

		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		boolean distinct = false;
		String table = null;
		String groupBy = null;
		String having = null;
		String limit = null;

		switch (sUriMatcher.match(uri)) {
		case POKEMON:
			table = PokeContract.PokemonTable.TABLE_NAME;
		case POKEMON_ID:
			break;
		case ABILITY:
			table = PokeContract.AbilitiesTable.TABLE_NAME;
		case ABILITY_ID:
			break;
		case NATURE:
			table = PokeContract.NaturesTable.TABLE_NAME;
		case NATURE_ID:
			break;
		case ATTACK:
			table = PokeContract.AttacksTable.TABLE_NAME;
		case ATTACK_ID:
			break;
		}
		Cursor cursor = db.query(distinct, table, projection, selection,
				selectionArgs, groupBy, having, sortOrder, limit);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String table = null;
		switch (sUriMatcher.match(uri)) {
		case POKEMON:
			table = PokeContract.PokemonTable.TABLE_NAME;
		case POKEMON_ID:
			break;
		case ABILITY:
			table = PokeContract.AbilitiesTable.TABLE_NAME;
		case ABILITY_ID:
			break;
		case NATURE:
			table = PokeContract.NaturesTable.TABLE_NAME;
		case NATURE_ID:
			break;
		case ATTACK:
			table = PokeContract.AttacksTable.TABLE_NAME;
		case ATTACK_ID:
			break;
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int count = db.update(table, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = null;
		switch (sUriMatcher.match(uri)) {
		case POKEMON:
			table = PokeContract.PokemonTable.TABLE_NAME;
		case POKEMON_ID:
			break;
		case ABILITY:
			table = PokeContract.AbilitiesTable.TABLE_NAME;
		case ABILITY_ID:
			break;
		case NATURE:
			table = PokeContract.NaturesTable.TABLE_NAME;
		case NATURE_ID:
			break;
		case ATTACK:
			table = PokeContract.AttacksTable.TABLE_NAME;
		case ATTACK_ID:
			break;
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int count = db.delete(table, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
