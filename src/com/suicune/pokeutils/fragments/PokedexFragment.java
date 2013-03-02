package com.suicune.pokeutils.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.TextView;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;

public class PokedexFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final int LOADER_POKEMON = 1;

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
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(LOADER_POKEMON, null, this);
	}

	private void prepareAdapter() {
		String[] from = { PokeContract.PokemonAbility1.ABILITY_1,
				PokeContract.PokemonAbility2.ABILITY_2,
				PokeContract.PokemonAbilityDW.ABILITY_DW,
				PokeContract.PokemonName.NAME, PokeContract.PokemonType1.TYPE,
				PokeContract.PokemonType2.TYPE };
		int[] to = { R.id.pokedex_entry_pokemon_ability_1,
				R.id.pokedex_entry_pokemon_ability_2,
				R.id.pokedex_entry_pokemon_ability_dw,
				R.id.pokedex_entry_pokemon_name,
				R.id.pokedex_entry_pokemon_type_1,
				R.id.pokedex_entry_pokemon_type_2 };
		mAdapter = new PokemonAdapter(getActivity(), R.layout.pokedex_entry,
				null, from, to, 0);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		switch (id) {
		case LOADER_POKEMON:
			loader = new CursorLoader(getActivity(),
					PokeContract.CONTENT_POKEDEX, null, null, null, null);
			break;
		}
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_POKEMON:
			mAdapter.swapCursor(cursor);
			break;
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	public class PokemonAdapter extends SimpleCursorAdapter {
		LayoutInflater mInflater;
		Uri mUri = PokeContract.Abilities.CONTENT_ABILITY;

		public PokemonAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to, int flags) {
			super(context, layout, c, from, to, flags);
			mInflater = (LayoutInflater) getActivity().getSystemService(
					Activity.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			View row = super.getView(position, convertView, container);
			if (row == null) {
				row = mInflater.inflate(R.layout.pokedex_entry, container,
						false);
			}
			TextView type2 = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_type_2);
			TextView ability1 = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_ability_1);
			TextView ability2 = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_ability_2);
			TextView abilityDW = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_ability_dw);

			if (type2.getText().toString().equals("0")) {
				type2.setText("-");
			}

			String selection = PokeContract.Abilities.ID + "=?";
			String[] abilities1 = { ability1.getText().toString() };
			String[] abilities2 = { ability2.getText().toString() };
			String[] abilitiesDW = { abilityDW.getText().toString() };

			Cursor c1 = getActivity().getContentResolver().query(mUri, null,
					selection, abilities1, null);
			Cursor c2 = getActivity().getContentResolver().query(mUri, null,
					selection, abilities2, null);
			Cursor cdw = getActivity().getContentResolver().query(mUri, null,
					selection, abilitiesDW, null);
			if (c1.getCount() > 0) {
				c1.moveToFirst();
				ability1.setText(c1.getString(c1
						.getColumnIndex(PokeContract.Abilities.NAME)));
			}
			if (c2.getCount() > 0) {
				c2.moveToFirst();
				ability2.setText(c2.getString(c2
						.getColumnIndex(PokeContract.Abilities.NAME)));
			}
			if (cdw.getCount() > 0) {
				cdw.moveToFirst();
				abilityDW.setText(cdw.getString(cdw
						.getColumnIndex(PokeContract.Abilities.NAME)));
			}
			c1.close();
			c2.close();
			cdw.close();
			return row;
		}
	}
}
