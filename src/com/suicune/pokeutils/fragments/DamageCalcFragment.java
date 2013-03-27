package com.suicune.pokeutils.fragments;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.suicune.pokeutils.Attack;
import com.suicune.pokeutils.Natures;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.TeamPokemon;
import com.suicune.pokeutils.database.PokeContract;
import com.suicune.pokeutils.tools.DamageCalcTools;

public class DamageCalcFragment extends Fragment implements
		LoaderCallbacks<Cursor>, TextWatcher, CursorToStringConverter,
		OnItemSelectedListener, OnItemClickListener {
	private static final int LOADER_ATTACKS = 1;
	private static final int LOADER_ATTACKING_POKEMON_AUTO_COMPLETE = 2;
	private static final int LOADER_DEFENDING_POKEMON_AUTO_COMPLETE = 3;
	private static final int LOADER_ATTACKING_POKEMON = 4;
	private static final int LOADER_DEFENDING_POKEMON = 5;
	private static final int LOADER_ATTACK = 6;

	private static final String ARG_ATTACKER_MODIFIER = "attackModififerPosition";
	private static final String ARG_DEFENDER_MODIFIER = "defenseModififerPosition";
	private static final String ARG_ATTACKER_NATURE = "attackerNature";
	private static final String ARG_DEFENDER_NATURE = "defenderNature";
	private static final String ARG_ATTACKER_ID = "attackerId";
	private static final String ARG_DEFENDER_ID = "defenderId";
	private static final String ARG_POKEMON_ID = "pokemonId";
	private static final String ARG_ATTACK_ID = "attackId";
	private static final String ARG_ATTACKER_ABILITY = "attackerAbility";
	private static final String ARG_DEFENDER_ABILITY = "defenderAbility";

	// Attacker views and constants
	private TeamPokemon mAttacker;
	private AutoCompleteTextView mAttackerView;
	private Spinner mAttackerNatureView;
	private int mAttackerNature = 0;
	private EditText mAttackerLevelView;
	private TextView mAttackerHpView;
	private TextView mAttackerAttView;
	private TextView mAttackerSpAttView;
	private TextView mAttackerSpeedView;
	private Spinner mAttackerAbilityView;
	private int mAttackerAbilityPosition = 0;
	private Spinner mAttackerItemView;
	private int mAttackerItemPosition = 0;
	private Spinner mAttackerModifierView;
	private int mAttackerModifierPosition = 6;

	// Defender views and constants
	private TeamPokemon mDefender;
	private AutoCompleteTextView mDefenderView;
	private Spinner mDefenderNatureView;
	private int mDefenderNature = 0;
	private EditText mDefenderLevelView;
	private TextView mDefenderHpView;
	private TextView mDefenderDefView;
	private TextView mDefenderSpDefView;
	private TextView mDefenderSpeedView;
	private Spinner mDefenderAbilityView;
	private int mDefenderAbilityPosition = 0;
	private Spinner mDefenderItemView;
	private int mDefenderItemPosition = 0;

	private Attack mAttack;
	private Spinner mAttackView;
	private EditText mAttackBaseDamageView;
	private int mAttackChoicePosition = -1;

	private Spinner mDefenderModifierView;
	private int mDefenderModifierPosition = 6;

	private TextView mResultView;

	private SimpleCursorAdapter mAttackerAdapter;
	private SimpleCursorAdapter mDefenderAdapter;
	private AttacksAdapter mAttacksAdapter;
	private ArrayAdapter<String> mAttackerAbilityAdapter;
	private ArrayAdapter<String> mDefenderAbilityAdapter;

	private String mPokemonName;

	private boolean isAttacker;

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
		if (getActivity().findViewById(R.id.damage_calc_attack) == null) {
			return;
		}

		if (savedInstanceState != null) {
			mAttackerModifierPosition = savedInstanceState
					.getInt(ARG_ATTACKER_MODIFIER);
			mDefenderModifierPosition = savedInstanceState
					.getInt(ARG_DEFENDER_MODIFIER);
			mAttackerNature = savedInstanceState.getInt(ARG_ATTACKER_NATURE);
			mDefenderNature = savedInstanceState.getInt(ARG_DEFENDER_NATURE);
			mAttackerAbilityPosition = savedInstanceState
					.getInt(ARG_ATTACKER_ABILITY);
			mDefenderAbilityPosition = savedInstanceState
					.getInt(ARG_DEFENDER_ABILITY);
			if (savedInstanceState.containsKey(ARG_ATTACKER_ID)) {
				Bundle args = new Bundle();
				args.putLong(ARG_POKEMON_ID,
						savedInstanceState.getLong(ARG_ATTACKER_ID));
				getLoaderManager().restartLoader(LOADER_ATTACKING_POKEMON,
						args, this);
			}
			if (savedInstanceState.containsKey(ARG_DEFENDER_ID)) {
				Bundle args = new Bundle();
				args.putLong(ARG_POKEMON_ID,
						savedInstanceState.getLong(ARG_DEFENDER_ID));
				getLoaderManager().restartLoader(LOADER_DEFENDING_POKEMON,
						args, this);
			}
			if (savedInstanceState.containsKey(ARG_ATTACK_ID)) {
				mAttackChoicePosition = savedInstanceState
						.getInt(ARG_ATTACK_ID);
			}
		}
		setViews();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_ATTACKER_MODIFIER, mAttackerModifierPosition);
		outState.putInt(ARG_DEFENDER_MODIFIER, mDefenderModifierPosition);
		outState.putInt(ARG_ATTACKER_NATURE, mAttackerNature);
		outState.putInt(ARG_DEFENDER_NATURE, mDefenderNature);
		outState.putInt(ARG_ATTACKER_ABILITY, mAttackerAbilityPosition);
		outState.putInt(ARG_DEFENDER_ABILITY, mDefenderAbilityPosition);
		if (mAttacker != null) {
			outState.putLong(ARG_ATTACKER_ID, mAttacker.mId);
		}
		if (mDefender != null) {
			outState.putLong(ARG_DEFENDER_ID, mDefender.mId);
		}
		if (mAttack != null) {
			outState.putInt(ARG_ATTACK_ID,
					mAttackView.getSelectedItemPosition());
		}
	}

	// VIEW RELATED METHODS ON START

	private void setViews() {
		prepareAttackerViews();
		prepareAttackViews();
		prepareDefenderViews();
		prepareOtherModifierViews();

		mResultView = (TextView) getActivity().findViewById(
				R.id.damage_calc_damage);
	}

	private void prepareAttackerViews() {
		prepareAttackerAutoCompleteView();
		prepareAttackerLevelView();
		prepareAttackerNatureView();
		prepareAttackerBaseStatsViews();
		prepareAttackerAbilityView();
		prepareAttackerItemView();
	}

	private void prepareAttackerAutoCompleteView() {
		mAttackerView = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_1);

		// Prepare the adapter
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mAttackerAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mAttackerAdapter.setCursorToStringConverter(this);

		// Set the adapter and different options.
		mAttackerView.setAdapter(mAttackerAdapter);
		mAttackerView.addTextChangedListener(this);
		mAttackerView.setOnItemClickListener(this);
	}

	private void prepareAttackerLevelView() {
		mAttackerLevelView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_level);
		mAttackerLevelView.setText("100");
		mAttackerLevelView.addTextChangedListener(this);
	}

	private void prepareAttackerNatureView() {
		mAttackerNatureView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_nature);

		mAttackerNatureView.setAdapter(getNaturesAdapter());
		mAttackerNatureView.setOnItemSelectedListener(this);
		mAttackerNatureView.setSelection(mAttackerNature);
	}

	private void prepareAttackerBaseStatsViews() {
		mAttackerHpView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_hp);
		mAttackerAttView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_att);
		mAttackerSpAttView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_sp_att);
		mAttackerSpeedView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_speed);

		mAttackerHpView.addTextChangedListener(this);
		mAttackerAttView.addTextChangedListener(this);
		mAttackerSpAttView.addTextChangedListener(this);
		mAttackerSpeedView.addTextChangedListener(this);

		mAttackerModifierView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attack_modifier);

		mAttackerModifierView.setSelection(mAttackerModifierPosition);
		mAttackerModifierView.setOnItemSelectedListener(this);
	}

	private void prepareAttackerAbilityView() {
		mAttackerAbilityView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_ability);

		mAttackerAbilityView.setOnItemSelectedListener(this);
	}

	private void prepareAttackerItemView() {
		mAttackerItemView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_item);

		// TODO: Add items to the equation, yey
	}

	private void prepareAttackViews() {
		mAttackView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attack);
		mAttackBaseDamageView = (EditText) getActivity().findViewById(
				R.id.damage_calc_base_damage);

		setAttackSpinnerAdapter();
		mAttackView.setAdapter(mAttacksAdapter);
		mAttackView.setOnItemSelectedListener(this);
		mAttackBaseDamageView.addTextChangedListener(this);
	}

	private void prepareDefenderViews() {
		prepareDefenderAutoCompleteView();
		prepareDefenderLevelView();
		prepareDefenderNatureView();
		prepareDefenderBaseStatsViews();
		prepareDefenderAbilityView();
		prepareDefenderItemView();
	}

	private void prepareDefenderAutoCompleteView() {
		mDefenderView = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_2);

		// Prepare the adapter
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mDefenderAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mDefenderAdapter.setCursorToStringConverter(this);

		// Set the adapter and different options.
		mDefenderView.setAdapter(mDefenderAdapter);
		mDefenderView.addTextChangedListener(this);
		mDefenderView.setOnItemClickListener(this);
	}

	private void prepareDefenderLevelView() {
		mDefenderLevelView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_level);
		mDefenderLevelView.setText("100");
		mDefenderLevelView.addTextChangedListener(this);
	}

	private void prepareDefenderNatureView() {
		mDefenderNatureView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_nature);

		mDefenderNatureView.setAdapter(getNaturesAdapter());
		mDefenderNatureView.setOnItemSelectedListener(this);
		mDefenderNatureView.setSelection(mDefenderNature);
	}

	private void prepareDefenderBaseStatsViews() {
		mDefenderHpView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_hp);
		mDefenderDefView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_def);
		mDefenderSpDefView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_sp_def);
		mDefenderSpeedView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_speed);

		mDefenderHpView.addTextChangedListener(this);
		mDefenderDefView.addTextChangedListener(this);
		mDefenderSpDefView.addTextChangedListener(this);
		mDefenderSpeedView.addTextChangedListener(this);

		mDefenderModifierView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defense_modifier);

		mDefenderModifierView.setSelection(mDefenderModifierPosition);
		mDefenderModifierView.setOnItemSelectedListener(this);
	}

	private void prepareDefenderAbilityView() {
		mDefenderAbilityView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_ability);

		mDefenderAbilityView.setOnItemSelectedListener(this);
	}

	private void prepareDefenderItemView() {
		mDefenderItemView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_item);

		// TODO: Add items to the equation, yey
	}

	private void prepareOtherModifierViews() {
		// TODO: Add stuff.
	}

	public SpinnerAdapter getNaturesAdapter() {
		String[] natures = new String[Natures.NATURES_COUNT];
		for (int i = 0; i < Natures.NATURES_COUNT; i++) {
			natures[i] = getString(Natures.getNatureName(i));
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				natures);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	private void setAttackSpinnerAdapter() {
		String[] from = { PokeContract.Attacks._ID };
		int[] to = { android.R.id.text1 };
		mAttacksAdapter = new AttacksAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null, from, to, 0);
		mAttacksAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	private void setAbilityAdapter(boolean isAttacker) {
		String[] pokemonAbilities = new String[3];
		String[] abilityNames = getResources()
				.getStringArray(R.array.abilities);
		if (isAttacker) {
			if (mAttacker == null) {
				return;
			}
			if (mAttacker.mAbility2 != 0) {
				pokemonAbilities[0] = abilityNames[mAttacker.mAbility1];
				pokemonAbilities[1] = abilityNames[mAttacker.mAbility2];
				pokemonAbilities[2] = abilityNames[mAttacker.mAbilityDw];
			} else {
				pokemonAbilities[0] = abilityNames[mAttacker.mAbility1];
				pokemonAbilities[1] = "-";
				pokemonAbilities[2] = abilityNames[mAttacker.mAbilityDw];

			}
			mAttackerAbilityAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, android.R.id.text1,
					pokemonAbilities);

			mAttackerAbilityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			mAttackerAbilityView.setAdapter(mAttackerAbilityAdapter);
			mAttackerAbilityView.setSelection(mAttackerAbilityPosition);
		} else {
			if (mDefender == null) {
				return;
			}
			if (mDefender.mAbility2 != 0) {
				pokemonAbilities[0] = abilityNames[mDefender.mAbility1];
				pokemonAbilities[1] = abilityNames[mDefender.mAbility2];
				pokemonAbilities[2] = abilityNames[mDefender.mAbilityDw];
			} else {
				pokemonAbilities[0] = abilityNames[mDefender.mAbility1];
				pokemonAbilities[1] = "-";
				pokemonAbilities[2] = abilityNames[mDefender.mAbilityDw];

			}
			mDefenderAbilityAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, android.R.id.text1,
					pokemonAbilities);
			mDefenderAbilityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			mDefenderAbilityView.setAdapter(mDefenderAbilityAdapter);
			mDefenderAbilityView.setSelection(mDefenderAbilityPosition);
		}
	}

	// INFORMATION LOADING

	private void loadAttack(long attack) {
		Bundle args = new Bundle();
		args.putLong(ARG_ATTACK_ID, attack);
		getLoaderManager().restartLoader(LOADER_ATTACK, args, this);
	}

	private void setAttackParameters() {
		mAttackBaseDamageView.setText("" + mAttack.mPower);
	}

	private void setAttackerStats() {
		if (mAttacker == null) {
			return;
		}
		mAttacker.setStats();
		mAttacker.mStatsModifier[TeamPokemon.INDEX_ATT] = DamageCalcTools
				.getStatModifier(mAttackerModifierPosition);
		mAttacker.mStatsModifier[TeamPokemon.INDEX_SP_ATT] = DamageCalcTools
				.getStatModifier(mAttackerModifierPosition);

		mAttackerHpView.setText("" + mAttacker.mStats[TeamPokemon.INDEX_HP]);
		mAttackerAttView.setText("" + mAttacker.mStats[TeamPokemon.INDEX_ATT]);
		mAttackerSpAttView.setText(""
				+ mAttacker.mStats[TeamPokemon.INDEX_SP_ATT]);
		mAttackerSpeedView.setText(""
				+ mAttacker.mStats[TeamPokemon.INDEX_SPEED]);
		setAbilityAdapter(true);
	}

	private void setDefenderStats() {
		if (mDefender == null) {
			return;
		}
		mDefender.setStats();
		mDefenderHpView.setText("" + mDefender.mStats[TeamPokemon.INDEX_HP]);
		mDefenderDefView.setText("" + mDefender.mStats[TeamPokemon.INDEX_DEF]);
		mDefenderSpDefView.setText(""
				+ mDefender.mStats[TeamPokemon.INDEX_SP_DEF]);
		mDefenderSpeedView.setText(""
				+ mDefender.mStats[TeamPokemon.INDEX_SPEED]);
		mDefender.mStatsModifier[TeamPokemon.INDEX_DEF] = DamageCalcTools
				.getStatModifier(mDefenderModifierPosition);
		mDefender.mStatsModifier[TeamPokemon.INDEX_SP_DEF] = DamageCalcTools
				.getStatModifier(mDefenderModifierPosition);
		setAbilityAdapter(false);
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.equals("")) {
			return;
		}
		if (!s.equals("")) {
			if (s.hashCode() == mAttackerView.getText().hashCode()) {
				mPokemonName = s.toString();

				isAttacker = true;
				getLoaderManager().restartLoader(
						LOADER_ATTACKING_POKEMON_AUTO_COMPLETE, null, this);
				return;
			} else if (s.hashCode() == mDefenderView.getText().hashCode()) {
				mPokemonName = s.toString();

				isAttacker = false;
				getLoaderManager().restartLoader(
						LOADER_DEFENDING_POKEMON_AUTO_COMPLETE, null, this);
				return;
			}
		}
		try {
			if(s.hashCode() == mAttackBaseDamageView.getText().hashCode()){
				if(mAttack != null){
					mAttack.mPower = Integer.parseInt(s.toString());
				}
			}else if (s.hashCode() == mAttackerLevelView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mLevel = Integer.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderLevelView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mLevel = Integer.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerHpView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.INDEX_HP] = Integer.parseInt(s
							.toString());
				}
			} else if (s.hashCode() == mAttackerAttView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.INDEX_ATT] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerSpAttView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.INDEX_SP_ATT] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerSpeedView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.INDEX_SPEED] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderHpView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.INDEX_HP] = Integer.parseInt(s
							.toString());
				}
			} else if (s.hashCode() == mDefenderDefView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.INDEX_DEF] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderSpDefView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.INDEX_SP_DEF] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderSpeedView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.INDEX_SPEED] = Integer
							.parseInt(s.toString());
				}
			}
			calculateDamage();
		} catch (NumberFormatException e) {
			return;
		}
	}

	private void calculateDamage() {
		if (mAttacker == null || mDefender == null || mAttack == null
				|| TextUtils.isEmpty(mAttackerLevelView.getText())
				|| TextUtils.isEmpty(mDefenderLevelView.getText())
				|| TextUtils.isEmpty(mAttackBaseDamageView.getText())) {
			return;
		}

		if (mAttack.mAttackClass == Attack.CLASS_OTHER) {
			mResultView.setText(R.string.no_damage);
			return;
		}

		String totalDamage = DamageCalcTools.calculateDamageTotal(mAttacker,
				mDefender, mAttack);
		String porcentDamage = DamageCalcTools.calculateDamagePorcent(
				mAttacker, mDefender, mAttack);
		mResultView.setText(getString(R.string.damage_calc_damage) + " "
				+ totalDamage + " (" + porcentDamage + ")");
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		switch (id) {
		case LOADER_ATTACKS:
			int form = 0;
			if (args != null && args.containsKey(ARG_ATTACKER_ID)) {
				form = 0;
			} else {
				form = mAttacker.mForm;
			}
			String attacksSelection = PokeContract.PokemonAttacks.NUMBER
					+ "=? AND " + PokeContract.PokemonAttacks.FORM + "=? AND "
					+ PokeContract.Attacks.POWER + " > 0";
			String[] attacksSelectionArgs = {
					Integer.toString(mAttacker.mNumber), Integer.toString(form) };
			loader = new CursorLoader(getActivity(),
					PokeContract.PokemonAttacks.CONTENT_POKEMON_ATTACKS, null,
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
			String attackSelection = PokeContract.Attacks._ID + "=?";
			String[] attackSelectionArgs = { Long.toString(args
					.getLong(ARG_ATTACK_ID)) };
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
			if (cursor.getCount() == 0) {
				Bundle args = new Bundle();
				args.putBoolean(ARG_ATTACKER_ID, true);
				getLoaderManager().restartLoader(LOADER_ATTACKS, args, this);
				return;
			}
			mAttacksAdapter.swapCursor(cursor);
			if (mAttackChoicePosition > -1) {
				mAttackView.setSelection(mAttackChoicePosition);
			}
			break;
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
			mAttackerAdapter.swapCursor(cursor);
			break;
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			mDefenderAdapter.swapCursor(cursor);
			break;
		case LOADER_ATTACKING_POKEMON:
			if (cursor.moveToFirst()) {
				mAttacker = new TeamPokemon(cursor);
				mAttacker.mLevel = Integer.parseInt(mAttackerLevelView
						.getText().toString());
				mAttacker.mNature = mAttackerNatureView
						.getSelectedItemPosition();
				getLoaderManager().restartLoader(LOADER_ATTACKS, null, this);
				setAttackerStats();
			}
			break;
		case LOADER_DEFENDING_POKEMON:
			if (cursor.moveToFirst()) {
				mDefender = new TeamPokemon(cursor);
				mDefender.mLevel = Integer.parseInt(mDefenderLevelView
						.getText().toString());
				mDefender.mNature = mDefenderNatureView
						.getSelectedItemPosition();
				setDefenderStats();
			}
			break;
		case LOADER_ATTACK:
			if (cursor.moveToFirst()) {
				mAttack = new Attack(cursor);
			}
			setAttackParameters();
			break;
		}
		calculateDamage();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch (loader.getId()) {
		case LOADER_ATTACKS:
			mAttacksAdapter.swapCursor(null);
			break;
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
			mAttackerAdapter.swapCursor(null);
			break;
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			mDefenderAdapter.swapCursor(null);
			break;
		}
	}

	@Override
	public CharSequence convertToString(Cursor c) {
		return c.getString(c
				.getColumnIndexOrThrow(PokeContract.PokemonName.NAME));
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		switch (adapterView.getId()) {
		case R.id.damage_calc_attack_modifier:
			mAttackerModifierPosition = position;
			if (mAttacker != null) {
				mAttacker.mStatsModifier[TeamPokemon.INDEX_ATT] = DamageCalcTools
						.getStatModifier(position);
				mAttacker.mStatsModifier[TeamPokemon.INDEX_SP_ATT] = DamageCalcTools
						.getStatModifier(position);
			}
			break;
		case R.id.damage_calc_defense_modifier:
			mDefenderModifierPosition = position;
			if (mDefender != null) {
				mDefender.mStatsModifier[TeamPokemon.INDEX_DEF] = DamageCalcTools
						.getStatModifier(position);
				mDefender.mStatsModifier[TeamPokemon.INDEX_SP_DEF] = DamageCalcTools
						.getStatModifier(position);
			}
			break;
		case R.id.damage_calc_attack:
			mAttackChoicePosition = position;
			loadAttack(id);
			break;
		case R.id.damage_calc_attacker_nature:
			mAttackerNature = position;
			if (mAttacker != null) {
				mAttacker.mNature = position;
				setAttackerStats();
			}
			break;
		case R.id.damage_calc_defender_nature:
			mDefenderNature = position;
			if (mDefender != null) {
				mDefender.mNature = position;
				setDefenderStats();
			}
			break;
		case R.id.damage_calc_attacker_ability:
			mAttackerAbilityPosition = position;
			if (mAttacker != null) {
				switch (position) {
				case 0:
					mAttacker.mSelectedAbility = mAttacker.mAbility1;
					break;
				case 1:
					mAttacker.mSelectedAbility = (mAttacker.mAbility2 != 0) ? mAttacker.mAbility2
							: mAttacker.mAbility1;
					break;
				case 2:
					mAttacker.mSelectedAbility = (mAttacker.mAbilityDw != 0) ? mAttacker.mAbilityDw
							: mAttacker.mAbility1;
					break;
				}
			}
			break;
		case R.id.damage_calc_defender_ability:
			mDefenderAbilityPosition = position;
			if (mDefender != null) {
				switch (position) {
				case 0:
					mDefender.mSelectedAbility = mDefender.mAbility1;
					break;
				case 1:
					mDefender.mSelectedAbility = (mDefender.mAbility2 != 0) ? mDefender.mAbility2
							: mDefender.mAbility1;
					break;
				case 2:
					mDefender.mSelectedAbility = (mDefender.mAbilityDw != 0) ? mDefender.mAbilityDw
							: mDefender.mAbility1;
					break;
				}
			}
			break;
		default:
			break;
		}
		calculateDamage();

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_POKEMON_ID, id);
		if (isAttacker) {
			getLoaderManager().restartLoader(LOADER_ATTACKING_POKEMON, args,
					DamageCalcFragment.this);
		} else {

			getLoaderManager().restartLoader(LOADER_DEFENDING_POKEMON, args,
					DamageCalcFragment.this);
		}
	}

	public class AttacksAdapter extends SimpleCursorAdapter {

		public AttacksAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to, int flags) {
			super(context, layout, c, from, to, flags);
		}

		@Override
		public void setViewText(TextView v, String text) {
			v.setText(getResources().getStringArray(R.array.moves)[Integer
					.parseInt(text)]);
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
	public void onNothingSelected(AdapterView<?> adapterView) {
	}
}