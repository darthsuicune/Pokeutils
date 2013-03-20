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
import android.text.TextUtils;
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
import android.widget.TextView;

import com.suicune.pokeutils.Attack;
import com.suicune.pokeutils.Pokemon;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.Types;
import com.suicune.pokeutils.database.PokeContract;
import com.suicune.pokeutils.tools.DamageCalcTools;

public class DamageCalcFragment extends Fragment implements
		LoaderCallbacks<Cursor>, TextWatcher, CursorToStringConverter,
		OnItemSelectedListener {
	private static final int LOADER_ATTACKS = 1;
	private static final int LOADER_ATTACKING_POKEMON_AUTO_COMPLETE = 2;
	private static final int LOADER_DEFENDING_POKEMON_AUTO_COMPLETE = 3;
	private static final int LOADER_ATTACKING_POKEMON = 4;
	private static final int LOADER_DEFENDING_POKEMON = 5;
	private static final int LOADER_ATTACK = 6;

	private static final String ARG_POKEMON_ID = "pokemonId";
	private static final String ARG_ATTACK_ID = "attackId";

	private Pokemon mAttackingPokemon;
	private Pokemon mDefendingPokemon;

	private AutoCompleteTextView mAttackingPokemonView;
	private AutoCompleteTextView mDefendingPokemonView;
	private EditText mLevelView;
	private Spinner mAttackView;
	private EditText mAttackBaseDamageView;
	private Spinner mAttackModifierView;
	private int mAttackModifierPosition = 6;
	private Spinner mDefenseModifierView;
	private int mDefenseModifierPosition = 6;

	private TextView mResultView;

	private SimpleCursorAdapter mAttackingPokemonAdapter;
	private SimpleCursorAdapter mDefendingPokemonAdapter;
	private SimpleCursorAdapter mAttacksAdapter;

	private String mPokemonName;

	private Attack mAttack;

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
		if (savedInstanceState != null) {
			mAttackModifierPosition = savedInstanceState
					.getInt("attack modififer position");
			mDefenseModifierPosition = savedInstanceState
					.getInt("defense modifier position");
		}
		setViews();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("attack modififer position", mAttackModifierPosition);
		outState.putInt("defense modifier position", mDefenseModifierPosition);
	}

	private void setViews() {
		mLevelView = (EditText) getActivity().findViewById(
				R.id.damage_calc_level1);
		if(mLevelView == null){
			return;
		}
		mLevelView.setText("100");
		mResultView = (TextView) getActivity().findViewById(
				R.id.damage_calc_damage);
		prepareAutoCompleteViews();
		prepareAttackViews();
		prepareDefenseViews();

		mAttackingPokemonView.addTextChangedListener(this);
		mDefendingPokemonView.addTextChangedListener(this);
		mLevelView.addTextChangedListener(this);
		mAttackBaseDamageView.addTextChangedListener(this);
	}

	private void prepareAutoCompleteViews() {
		mAttackingPokemonView = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.damage_calc_pokemon_1);
		mDefendingPokemonView = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.damage_calc_pokemon_2);
		setAutoCompleteAdapters();

		mAttackingPokemonView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putLong(ARG_POKEMON_ID, id);
				getLoaderManager().restartLoader(LOADER_ATTACKING_POKEMON,
						args, DamageCalcFragment.this);
			}
		});
		mDefendingPokemonView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putLong(ARG_POKEMON_ID, id);
				getLoaderManager().restartLoader(LOADER_DEFENDING_POKEMON,
						args, DamageCalcFragment.this);
			}
		});
	}

	private void setAutoCompleteAdapters() {
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mAttackingPokemonAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mDefendingPokemonAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);

		mAttackingPokemonAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mDefendingPokemonAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		mAttackingPokemonAdapter.setCursorToStringConverter(this);
		mDefendingPokemonAdapter.setCursorToStringConverter(this);

		mAttackingPokemonView.setAdapter(mAttackingPokemonAdapter);
		mDefendingPokemonView.setAdapter(mDefendingPokemonAdapter);
	}

	private void prepareAttackViews() {
		mAttackView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attack);
		mAttackBaseDamageView = (EditText) getActivity().findViewById(
				R.id.damage_calc_base_damage);

		mAttackModifierView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attack_modifier);
		mAttackModifierView.setSelection(mAttackModifierPosition);
		mAttackModifierView.setOnItemSelectedListener(this);

		setSpinnerAdapter();
		mAttackView.setAdapter(mAttacksAdapter);
		mAttackView.setOnItemSelectedListener(this);
	}

	private void prepareDefenseViews() {
		mDefenseModifierView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defense_modifier);
		mDefenseModifierView.setSelection(mDefenseModifierPosition);
		mDefenseModifierView.setOnItemSelectedListener(this);
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
	public void afterTextChanged(Editable s) {
		if (!s.equals("")) {
			mPokemonName = s.toString();
			if (s.hashCode() == mAttackingPokemonView.getText().hashCode()) {
				getLoaderManager().restartLoader(
						LOADER_ATTACKING_POKEMON_AUTO_COMPLETE, null, this);
			} else if (s.hashCode() == mDefendingPokemonView.getText()
					.hashCode()) {
				getLoaderManager().restartLoader(
						LOADER_DEFENDING_POKEMON_AUTO_COMPLETE, null, this);
			}
		} else {
			mPokemonName = "";
		}
		if ((mAttackingPokemon != null)
				&& (mDefendingPokemon != null)
				&& (!TextUtils.isEmpty(mAttackBaseDamageView.getText()
						.toString()))) {
			calculateDamage();
		}
	}

	private void calculateDamage() {
		if (TextUtils.isEmpty(mLevelView.getText())) {
			return;
		}

		int attackerLevel = Integer.parseInt(mLevelView.getText().toString());

		// TODO replace attacker stat
		int pokemonAttackStat = 300;
		double attackLevelModifier = DamageCalcTools
				.getStatModifier(mAttackModifierPosition);
		// int attackingType = mAttack.mType;
		int attackingType = Types.ELECTRIC;

		// TODO replace defender stats
		int pokemonDefenseStat = 300;
		int pokemonHP = 400;
		double defenseLevelModifier = DamageCalcTools.TEMP_MODIFIER_0_LEVEL;
		int defendingType1 = mDefendingPokemon.mType1;
		int defendingType2 = mDefendingPokemon.mType2;

		double typeModifier = DamageCalcTools.getTypeModifier(attackingType,
				defendingType1, defendingType2);
		int attackBasePower = Integer.parseInt(mAttackBaseDamageView.getText()
				.toString());

		boolean hasStab = true;
		// boolean hasStab = ((mAttackingPokemon.mType1 == mAttack.mType) ||
		// (mAttackingPokemon.mType2 == mAttack.mType));

		String totalDamage = DamageCalcTools.calculateDamageTotal(
				pokemonAttackStat, attackerLevel, attackBasePower,
				pokemonDefenseStat, typeModifier, attackLevelModifier,
				defenseLevelModifier, hasStab);
		String porcentDamage = DamageCalcTools.calculateDamagePorcent(
				pokemonAttackStat, attackerLevel, attackBasePower,
				pokemonDefenseStat, pokemonHP, typeModifier,
				attackLevelModifier, defenseLevelModifier, hasStab);
		mResultView.setText(totalDamage + " (" + porcentDamage + ")");
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		switch (id) {
		case LOADER_ATTACKS:
			String attacksSelection = PokeContract.PokemonAttacks.NUMBER
					+ "=? AND " + PokeContract.PokemonAttacks.FORM + "=?";
			String[] attacksSelectionArgs = {
					Integer.toString(mAttackingPokemon.mNumber),
					Integer.toString(mAttackingPokemon.mForm) };
			loader = new CursorLoader(getActivity(),
					PokeContract.Attacks.CONTENT_ATTACK, null,
					attacksSelection, attacksSelectionArgs, null);
			break;
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			String autoCompleteSelection = PokeContract.PokemonName.NAME
					+ " LIKE ?";
			String[] autoCompleteSelectionArgs = { "%" + mPokemonName + "%" };
			loader = new CursorLoader(getActivity(),
					PokeContract.PokemonName.CONTENT_POKEMON_NAME, null,
					autoCompleteSelection, autoCompleteSelectionArgs, null);
			break;
		case LOADER_ATTACKING_POKEMON:
		case LOADER_DEFENDING_POKEMON:
			String pokemonSelection = PokeContract.PokemonName.TABLE_NAME + "."
					+ PokeContract.PokemonName._ID + "=?";
			String[] pokemonSelectionArgs = { Long.toString(args
					.getLong(ARG_POKEMON_ID)) };
			loader = new CursorLoader(getActivity(),
					PokeContract.Pokedex.CONTENT_POKEDEX, null,
					pokemonSelection, pokemonSelectionArgs, null);
			break;
		case LOADER_ATTACK:
			String attackSelection = PokeContract.Attacks.NAME + "=?";
			String[] attackSelectionArgs = { args.getString(ARG_ATTACK_ID) };
			loader = new CursorLoader(getActivity(),
					PokeContract.Attacks.CONTENT_ATTACK, null, attackSelection,
					attackSelectionArgs, null);
			break;
		}

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch (loader.getId()) {
		case LOADER_ATTACKS:
			mAttacksAdapter.swapCursor(cursor);
			break;
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
			mAttackingPokemonAdapter.swapCursor(cursor);
			break;
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			mDefendingPokemonAdapter.swapCursor(cursor);
			break;
		case LOADER_ATTACKING_POKEMON:
			if (cursor.moveToFirst()) {
				mAttackingPokemon = new Pokemon(cursor);
				getLoaderManager().restartLoader(LOADER_ATTACKS, null, this);
			}
			break;
		case LOADER_DEFENDING_POKEMON:
			if (cursor.moveToFirst()) {
				mDefendingPokemon = new Pokemon(cursor);
				getLoaderManager().restartLoader(LOADER_ATTACKS, null, this);
			}
			break;
		case LOADER_ATTACK:
			if (cursor.moveToFirst()) {
				mAttack = new Attack(cursor);
			}
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_ATTACKS:
			mAttacksAdapter.swapCursor(null);
			break;
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
			mAttackingPokemonAdapter.swapCursor(null);
			break;
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			mDefendingPokemonAdapter.swapCursor(null);
			break;
		}
	}

	@Override
	public CharSequence convertToString(Cursor c) {
		return c.getString(c
				.getColumnIndexOrThrow(PokeContract.PokemonName.NAME));
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		switch (adapterView.getId()) {
		case R.id.damage_calc_attack_modifier:
			mAttackModifierPosition = position;
			break;
		case R.id.damage_calc_defense_modifier:
			mDefenseModifierPosition = position;
			break;
		case R.id.damage_calc_attack:
			setAttackParameters(id);
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}
}
