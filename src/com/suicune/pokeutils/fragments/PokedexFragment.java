package com.suicune.pokeutils.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.Types;
import com.suicune.pokeutils.database.PokeContract;

public class PokedexFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final int LOADER_POKEMON = 1;

	private SimpleCursorAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		return inflater.inflate(R.layout.pokedex, container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(getActivity().findViewById(android.R.id.list) == null){
			return;
		}
		prepareAdapter();
		setListAdapter(mAdapter);
		getLoaderManager().restartLoader(LOADER_POKEMON, null, this);
	}

	private void prepareAdapter() {
		String[] from = { PokeContract.PokemonAbility1.ABILITY_1,
				PokeContract.PokemonAbility2.ABILITY_2,
				PokeContract.PokemonAbilityDW.ABILITY_DW,
				PokeContract.PokemonName.NUMBER, PokeContract.PokemonName.NAME,
				PokeContract.PokemonType1.TYPE, PokeContract.PokemonType2.TYPE };
		int[] to = { R.id.pokedex_entry_pokemon_ability_1,
				R.id.pokedex_entry_pokemon_ability_2,
				R.id.pokedex_entry_pokemon_ability_dw,
				R.id.pokedex_entry_pokemon_number,
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
					PokeContract.Pokedex.CONTENT_POKEDEX, null, null, null,
					null);
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
		String[] mAbilities;

		public PokemonAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to, int flags) {
			super(context, layout, c, from, to, flags);
			mInflater = (LayoutInflater) getActivity().getSystemService(
					Activity.LAYOUT_INFLATER_SERVICE);
			mAbilities = getResources().getStringArray(R.array.abilities);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			View row = super.getView(position, convertView, container);
			if (row == null) {
				row = mInflater.inflate(R.layout.pokedex_entry, container,
						false);
			}
			TextView type1View = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_type_1);
			TextView type2View = (TextView) row
					.findViewById(R.id.pokedex_entry_pokemon_type_2);
			TextView ability1View = (TextView) row.findViewById(R.id.pokedex_entry_pokemon_ability_1);
			TextView ability2View = (TextView) row.findViewById(R.id.pokedex_entry_pokemon_ability_2);
			TextView abilityDwView = (TextView) row.findViewById(R.id.pokedex_entry_pokemon_ability_dw);

			int type1 = Types.getTypeName(Integer.parseInt(type1View.getText()
					.toString()));
			type1View.setText((type1 == 0) ? "-" : getString(type1));
			int type2 = Types.getTypeName(Integer.parseInt(type2View.getText()
					.toString()));
			type2View.setText((type2 == 0) ? "-" : getString(type2));
			
			ability1View.setText(mAbilities[Integer.parseInt(ability1View.getText().toString())]);
			ability2View.setText(mAbilities[Integer.parseInt(ability2View.getText().toString())]);
			abilityDwView.setText(mAbilities[Integer.parseInt(abilityDwView.getText().toString())]);
			return row;
		}
	}
}
