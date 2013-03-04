package com.suicune.pokeutils.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;

public class DamageCalcFragment extends Fragment implements
		LoaderCallbacks<Cursor>, TextWatcher, CursorToStringConverter {
	private static final int LOADER_AUTO_COMPLETE = 1;
	protected static final int LOADER_ATTACKS = 2;

	protected static final String ARG_POKEMON_ID = "pokemonId";

	private AutoCompleteTextView mPokemon1View;
	private AutoCompleteTextView mPokemon2View;
	private EditText mLevel1View;
	private EditText mLevel2View;
	private Spinner mAttackView;
	private EditText mAttackBaseDamageView;

	private SimpleCursorAdapter mPokemon1Adapter;
	private SimpleCursorAdapter mPokemon2Adapter;

	private SimpleCursorAdapter mAttacksAdapter;

	private String mPokemonName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		return inflater
				.inflate(R.layout.damage_calc_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setViews();
	}

	private void setViews() {
		mLevel1View = (EditText) getActivity().findViewById(
				R.id.damage_calc_level1);
		mLevel1View.setText("100");
		mLevel2View = (EditText) getActivity().findViewById(
				R.id.damage_calc_level2);
		mLevel2View.setText("100");
		prepareAutoCompleteViews();
		prepareAttackViews();
	}

	private void prepareAutoCompleteViews() {
		mPokemon1View = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_1);
		mPokemon2View = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_2);
		setAutoCompleteAdapters();
		mPokemon1View.setAdapter(mPokemon1Adapter);
		mPokemon2View.setAdapter(mPokemon2Adapter);

		mPokemon1View.addTextChangedListener(this);
		mPokemon2View.addTextChangedListener(this);

		mPokemon1View.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putLong(ARG_POKEMON_ID, id);
				getLoaderManager().restartLoader(LOADER_ATTACKS, args,
						DamageCalcFragment.this);
			}
		});
	}

	private void setAutoCompleteAdapters() {
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mPokemon1Adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mPokemon2Adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);

		mPokemon1Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mPokemon2Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		mPokemon1Adapter.setCursorToStringConverter(this);
		mPokemon2Adapter.setCursorToStringConverter(this);
	}

	private void prepareAttackViews() {
		mAttackView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attack);
		mAttackBaseDamageView = (EditText) getActivity().findViewById(
				R.id.damage_calc_base_damage);

		setSpinnerAdapter();
		mAttackView.setAdapter(mAttacksAdapter);
		mAttackView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				setAttackParameters(id);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
	}

	private void setSpinnerAdapter() {
		String[] from = { PokeContract.Attacks.NAME };
		int[] to = { android.R.id.text1 };
		mAttacksAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mAttacksAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
	}

	private void setAttackParameters(long attack) {

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		switch (id) {
		case LOADER_AUTO_COMPLETE:
			String autoCompleteSelection = PokeContract.PokemonName.NAME
					+ " LIKE ?";
			String[] autoCompleteSelectionArgs = { "%" + mPokemonName + "%" };
			loader = new CursorLoader(getActivity(),
					PokeContract.PokemonName.CONTENT_POKEMON_NAME, null,
					autoCompleteSelection, autoCompleteSelectionArgs, null);
			break;
		case LOADER_ATTACKS:
			// String attacksSelection = PokeContract.PokemonAttacks + "=?";
			String attacksSelection = "";
			String[] attacksSelectionArgs = {};
			loader = new CursorLoader(getActivity(),
					PokeContract.Attacks.CONTENT_ATTACK, null,
					attacksSelection, attacksSelectionArgs, null);
			break;
		}
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_AUTO_COMPLETE:
			mPokemon1Adapter.swapCursor(cursor);
			mPokemon2Adapter.swapCursor(cursor);
			break;
		case LOADER_ATTACKS:
			mAttacksAdapter.swapCursor(cursor);
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_AUTO_COMPLETE:
			mPokemon1Adapter.swapCursor(null);
			mPokemon2Adapter.swapCursor(null);
			break;
		case LOADER_ATTACKS:
			mAttacksAdapter.swapCursor(null);
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (!s.equals("")) {
			mPokemonName = s.toString();
			getLoaderManager().restartLoader(LOADER_AUTO_COMPLETE, null, this);
		} else {
			mPokemonName = "";
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public CharSequence convertToString(Cursor c) {
		return c.getString(c
				.getColumnIndexOrThrow(PokeContract.PokemonName.NAME));
	}
}
