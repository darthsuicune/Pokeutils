package com.suicune.pokeutils.fragments;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;

public class PokedexFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private SimpleCursorAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.pokedex, container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		prepareAdapter();
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
	}

	private void prepareAdapter() {
		String[] from = {
				PokeContract.Abilities.NAME,
				PokeContract.PokemonName.NAME,
				PokeContract.PokemonType1.TYPE,
				PokeContract.PokemonType2.TYPE
		};
		int[] to = {
				R.id.pokedex_entry_pokemon_ability_1,
				R.id.pokedex_entry_pokemon_ability_2,
				R.id.pokedex_entry_pokemon_ability_dw,
				R.id.pokedex_entry_pokemon_name,
				R.id.pokedex_entry_pokemon_type_1,
				R.id.pokedex_entry_pokemon_type_2
		};
		mAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.pokedex_entry, null, from, to, 0);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = new CursorLoader(getActivity(),
				PokeContract.CONTENT_POKEDEX, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		String string = DatabaseUtils.dumpCursorToString(cursor);
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
