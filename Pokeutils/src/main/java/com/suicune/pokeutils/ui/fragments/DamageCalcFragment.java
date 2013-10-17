package com.suicune.pokeutils.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.app.*;
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

	private static final String ARG_ATTACKER_ID = "attackerId";
	private static final String ARG_DEFENDER_ID = "defenderId";
	private static final String ARG_POKEMON_ID = "pokemonId";
	private static final String ARG_ATTACK_ID = "attackId";

	// Attacker views and constants
	private TeamPokemon mAttacker;
	private AutoCompleteTextView mAttackerView;
	private Spinner mAttackerNatureView;
	private EditText mAttackerLevelView;
	private TextView mAttackerHpView;
	private TextView mAttackerAttView;
	private TextView mAttackerSpAttView;
	private TextView mAttackerSpeedView;
	private Spinner mAttackerAbilityView;
	private Spinner mAttackerItemView;
	private Spinner mAttackerModifierView;

	// Defender views and constants
	private TeamPokemon mDefender;
	private AutoCompleteTextView mDefenderView;
	private Spinner mDefenderNatureView;
	private EditText mDefenderLevelView;
	private TextView mDefenderHpView;
	private TextView mDefenderDefView;
	private TextView mDefenderSpDefView;
	private TextView mDefenderSpeedView;
	private Spinner mDefenderAbilityView;
	private Spinner mDefenderItemView;
	private Spinner mDefenderModifierView;

	private Attack mAttack;
	private Spinner mAttackView;
	private EditText mAttackBaseDamageView;
	private int mAttackChoicePosition = -1;

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
		setViews();

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(ARG_ATTACKER_ID)) {
				mAttacker = new TeamPokemon(
						savedInstanceState.getBundle(ARG_ATTACKER_ID));
				for (int i = 0; i < 6; i++) {
					mAttacker.mEvs[i] = 252;
				}
				if (savedInstanceState.containsKey(ARG_ATTACK_ID)) {
					mAttackChoicePosition = savedInstanceState
							.getInt(ARG_ATTACK_ID);
				}
				loadAttackerData();
			}
			if (savedInstanceState.containsKey(ARG_DEFENDER_ID)) {
				mDefender = new TeamPokemon(
						savedInstanceState.getBundle(ARG_DEFENDER_ID));
				for (int i = 0; i < 6; i++) {
					mAttacker.mEvs[i] = 252;
				}
				loadDefenderData();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mAttacker != null) {
			Bundle attacker = new Bundle();
			mAttacker.saveStatus(attacker);
			outState.putBundle(ARG_ATTACKER_ID, attacker);
			if (mAttack != null) {
				outState.putInt(ARG_ATTACK_ID,
						mAttackView.getSelectedItemPosition());
			}
		}
		if (mDefender != null) {
			Bundle defender = new Bundle();
			mDefender.saveStatus(defender);
			outState.putBundle(ARG_DEFENDER_ID, defender);
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

		mAttackerModifierView.setOnItemSelectedListener(this);
		mAttackerModifierView.setSelection(6);
	}

	private void prepareAttackerAbilityView() {
		mAttackerAbilityView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_ability);

		mAttackerAbilityView.setOnItemSelectedListener(this);
	}

	private void prepareAttackerItemView() {
		mAttackerItemView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_item);

		mAttackerItemView.setOnItemSelectedListener(this);
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

		mDefenderModifierView.setOnItemSelectedListener(this);
		mDefenderModifierView.setSelection(6);
	}

	private void prepareDefenderAbilityView() {
		mDefenderAbilityView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_ability);

		mDefenderAbilityView.setOnItemSelectedListener(this);
	}

	private void prepareDefenderItemView() {
		mDefenderItemView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_item);

		mDefenderItemView.setOnItemSelectedListener(this);
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
		if (isAttacker) {
			if (mAttacker == null) {
				return;
			}
            int currentAbility = mAttacker.mCurrentAbility.mId;
            pokemonAbilities[Pokemon.ABILITY_INDEX_1] =
                    mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_1).mName;
            pokemonAbilities[Pokemon.ABILITY_INDEX_2] =
                    (mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_2).mId == 0) ? "-"
                    : mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_2).mName;
            pokemonAbilities[Pokemon.ABILITY_INDEX_DW_1] =
                    (mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mId == 0) ? "-"
                    : mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mName;
            mAttackerAbilityAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, android.R.id.text1,
                    pokemonAbilities);

			mAttackerAbilityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			mAttackerAbilityView.setAdapter(mAttackerAbilityAdapter);
			mAttackerAbilityView.setSelection(currentAbility);
		} else {
			if (mDefender == null) {
				return;
			}
            int currentAbility = mDefender.mCurrentAbility.mId;
            pokemonAbilities[Pokemon.ABILITY_INDEX_1] =
                    mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_1).mName;
            pokemonAbilities[Pokemon.ABILITY_INDEX_2] =
                    (mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_2).mId == 0) ? "-"
                            : mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_2).mName;
            pokemonAbilities[Pokemon.ABILITY_INDEX_DW_1] =
                    (mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mId == 0) ? "-"
                            : mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mName;
            mDefenderAbilityAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, android.R.id.text1,
                    pokemonAbilities);

			mDefenderAbilityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			mDefenderAbilityView.setAdapter(mDefenderAbilityAdapter);
			mDefenderAbilityView.setSelection(currentAbility);
		}
	}

	// INFORMATION LOADING

	// TODO
	private void loadAttackerData() {
		try {
			mAttacker.mLevel = Integer.parseInt(mAttackerLevelView.getText()
					.toString());
			mAttackerModifierView
					.setSelection(mAttacker.mStatsModifier[TeamPokemon.STAT_INDEX_ATT]);
			mAttackerNatureView.setSelection(mAttacker.mNature);
			setAttackerStats();
			mAttackerAbilityView.setSelection(mAttacker.mCurrentAbility.mId);
		} catch (NumberFormatException e) {
			mAttacker.mLevel = 100;
		}
		getLoaderManager().restartLoader(LOADER_ATTACKS, null, this);
	}

	private void setAttackerStats() {
		if (mAttacker == null) {
			return;
		}
		mAttacker.showStats();
		mAttacker.mStatsModifier[TeamPokemon.STAT_INDEX_ATT] = mAttackerModifierView
				.getSelectedItemPosition();
		mAttacker.mStatsModifier[TeamPokemon.STAT_INDEX_SP_ATT] = mAttackerModifierView
				.getSelectedItemPosition();

		mAttackerHpView.setText("" + mAttacker.mStats[TeamPokemon.STAT_INDEX_HP]);
		mAttackerAttView.setText("" + mAttacker.mStats[TeamPokemon.STAT_INDEX_ATT]);
		mAttackerSpAttView.setText(""
				+ mAttacker.mStats[TeamPokemon.STAT_INDEX_SP_ATT]);
		mAttackerSpeedView.setText(""
				+ mAttacker.mStats[TeamPokemon.STAT_INDEX_SPEED]);
		setAbilityAdapter(true);
	}

	private void loadDefenderData() {
		try {
			mDefender.mLevel = Integer.parseInt(mDefenderLevelView.getText()
					.toString());
			mDefenderModifierView
					.setSelection(mDefender.mStatsModifier[TeamPokemon.STAT_INDEX_DEF]);
			mDefenderNatureView.setSelection(mDefender.mNature);
			setDefenderStats();
			mDefenderAbilityView.setSelection(mDefender.mCurrentAbility.mId);
		} catch (NumberFormatException e) {
			mDefender.mLevel = 100;
		}
	}

	private void setDefenderStats() {
		if (mDefender == null) {
			return;
		}
		mDefender.showStats();
		mDefenderHpView.setText("" + mDefender.mStats[TeamPokemon.STAT_INDEX_HP]);
		mDefenderDefView.setText("" + mDefender.mStats[TeamPokemon.STAT_INDEX_DEF]);
		mDefenderSpDefView.setText(""
				+ mDefender.mStats[TeamPokemon.STAT_INDEX_SP_DEF]);
		mDefenderSpeedView.setText(""
				+ mDefender.mStats[TeamPokemon.STAT_INDEX_SPEED]);
		mDefender.mStatsModifier[TeamPokemon.STAT_INDEX_DEF] = mDefenderModifierView
				.getSelectedItemPosition();
		mDefender.mStatsModifier[TeamPokemon.STAT_INDEX_SP_DEF] = mDefenderModifierView
				.getSelectedItemPosition();
		setAbilityAdapter(false);
	}

	private void loadAttack(long attack) {
		Bundle args = new Bundle();
		args.putLong(ARG_ATTACK_ID, attack);
		getLoaderManager().restartLoader(LOADER_ATTACK, args, this);
	}

	private void setAttackParameters() {
		mAttackBaseDamageView.setText("" + mAttack.mPower);
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

		String totalDamage = DamageCalcTools.calculateDamageAmount(mAttacker,
                mDefender, mAttack);
		String porcentDamage = DamageCalcTools.calculateDamagePercent(
                mAttacker, mDefender, mAttack);
		if (totalDamage == null) {
			mResultView.setText(R.string.no_damage);
		} else {
			mResultView.setText(getString(R.string.damage_calc_damage) + " "
					+ totalDamage + " (" + porcentDamage + ")");
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		// This loader accepts empty parameters. If arg_attacker_id is supplied,
		// it
		// will leave the form from the attacker (as it has no attacks)
		case LOADER_ATTACKS:
			int form = 0;
			if (args != null && args.containsKey(ARG_ATTACKER_ID)) {
				form = 0;
			} else {
				form = mAttacker.mForm;
			}
			String[] attacksProjection = { PokeContract.Attacks.TABLE_NAME
					+ "." + PokeContract.Attacks._ID };
			String attacksSelection = PokeContract.PokemonAttacks.NUMBER
					+ "=? AND " + PokeContract.PokemonAttacks.FORM + "=? AND "
					+ PokeContract.Attacks.POWER + " > 0";
			String[] attacksSelectionArgs = {
					Integer.toString(mAttacker.mNumber), Integer.toString(form) };
			return new CursorLoader(getActivity(),
					PokeContract.PokemonAttacks.CONTENT_POKEMON_ATTACKS,
					attacksProjection, attacksSelection, attacksSelectionArgs,
					null);
		case LOADER_ATTACKING_POKEMON_AUTO_COMPLETE:
			// This two loaders are identical to each other. The difference is
			// only on the result handling
		case LOADER_DEFENDING_POKEMON_AUTO_COMPLETE:
			String[] autoCompleteProjection = { PokeContract.PokemonName._ID,
					PokeContract.PokemonName.NAME };
			String autoCompleteSelection = PokeContract.PokemonName.NAME
					+ " LIKE ?";
			String[] autoCompleteSelectionArgs = { "%" + mPokemonName + "%" };
			return new CursorLoader(getActivity(),
					PokeContract.PokemonName.CONTENT_POKEMON_NAME,
					autoCompleteProjection, autoCompleteSelection,
					autoCompleteSelectionArgs, null);
		case LOADER_ATTACKING_POKEMON:
			// This two loaders are identical to each other. The difference is
			// only on the result handling
		case LOADER_DEFENDING_POKEMON:
			String pokemonSelection = PokeContract.PokemonName.TABLE_NAME + "."
					+ PokeContract.PokemonName._ID + "=?";
			String[] pokemonSelectionArgs = { Long.toString(args
					.getLong(ARG_POKEMON_ID)) };
			return new CursorLoader(getActivity(),
					PokeContract.Pokedex.CONTENT_POKEDEX, null,
					pokemonSelection, pokemonSelectionArgs, null);
		case LOADER_ATTACK:
			String attackSelection = PokeContract.Attacks._ID + "=?";
			String[] attackSelectionArgs = { Long.toString(args
					.getLong(ARG_ATTACK_ID)) };
			return new CursorLoader(getActivity(),
					PokeContract.Attacks.CONTENT_ATTACK, null, attackSelection,
					attackSelectionArgs, null);
		default:
			return null;
		}
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
				for (int i = 0; i < 6; i++) {
					mAttacker.mEvs[i] = 252;
				}
				loadAttackerData();
			}
			break;
		case LOADER_DEFENDING_POKEMON:
			if (cursor.moveToFirst()) {
				mDefender = new TeamPokemon(cursor);
				for (int i = 0; i < 6; i++) {
					mDefender.mEvs[i] = 252;
				}
				loadDefenderData();
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
			if (s.hashCode() == mAttackBaseDamageView.getText().hashCode()) {
				if (mAttack != null) {
					mAttack.mPower = Integer.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerLevelView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mLevel = Integer.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderLevelView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mLevel = Integer.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerHpView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.STAT_INDEX_HP] = Integer.parseInt(s
							.toString());
				}
			} else if (s.hashCode() == mAttackerAttView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.STAT_INDEX_ATT] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerSpAttView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.STAT_INDEX_SP_ATT] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mAttackerSpeedView.getText().hashCode()) {
				if (mAttacker != null) {
					mAttacker.mStats[TeamPokemon.STAT_INDEX_SPEED] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderHpView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.STAT_INDEX_HP] = Integer.parseInt(s
							.toString());
				}
			} else if (s.hashCode() == mDefenderDefView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.STAT_INDEX_DEF] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderSpDefView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.STAT_INDEX_SP_DEF] = Integer
							.parseInt(s.toString());
				}
			} else if (s.hashCode() == mDefenderSpeedView.getText().hashCode()) {
				if (mDefender != null) {
					mDefender.mStats[TeamPokemon.STAT_INDEX_SPEED] = Integer
							.parseInt(s.toString());
				}
			}
			calculateDamage();
		} catch (NumberFormatException e) {
			return;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		switch (adapterView.getId()) {
		case R.id.damage_calc_attack_modifier:
			if (mAttacker != null) {
				mAttacker.mStatsModifier[TeamPokemon.STAT_INDEX_ATT] = position;
				mAttacker.mStatsModifier[TeamPokemon.STAT_INDEX_SP_ATT] = position;
				setAttackerStats();
			}
			break;
		case R.id.damage_calc_defense_modifier:
			if (mDefender != null) {
				mDefender.mStatsModifier[TeamPokemon.STAT_INDEX_DEF] = position;
				mDefender.mStatsModifier[TeamPokemon.STAT_INDEX_SP_DEF] = position;
				setDefenderStats();
			}
			break;
		case R.id.damage_calc_attack:
			mAttackChoicePosition = position;
			loadAttack(id);
			break;
		case R.id.damage_calc_attacker_nature:
			if (mAttacker != null) {
				mAttacker.mNature = position;
				setAttackerStats();
			}
			break;
		case R.id.damage_calc_defender_nature:
			if (mDefender != null) {
				mDefender.mNature = position;
				setDefenderStats();
			}
			break;
		case R.id.damage_calc_attacker_ability:
			if (mAttacker != null) {
				if ((mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_2).mId == 0)
						&& (position == Pokemon.ABILITY_INDEX_2)) {
					mAttacker.setAbility(mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_1).mId);
					mAttackerAbilityView
							.setSelection(Pokemon.ABILITY_INDEX_1);
				} else if ((mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mId == 0)
						&& (position == Pokemon.ABILITY_INDEX_DW_1)) {
                    mAttacker.setAbility(mAttacker.mAbilities.get(Pokemon.ABILITY_INDEX_1).mId);
					mAttackerAbilityView
							.setSelection(Pokemon.ABILITY_INDEX_1);
				} else {
					mAttacker.setAbility(mAttacker.mAbilities.get(position).mId);
				}
			}
			break;
		case R.id.damage_calc_defender_ability:
			if (mDefender != null) {
				if ((position == Pokemon.ABILITY_INDEX_2)
						&& (mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_2).mId == 0)) {
					mDefender.setAbility(mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_1).mId);
					mDefenderAbilityView
							.setSelection(Pokemon.ABILITY_INDEX_1);
				} else if ((position == Pokemon.ABILITY_INDEX_DW_1)
						&& (mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_DW_1).mId == 0)) {
                    mDefender.setAbility(mDefender.mAbilities.get(Pokemon.ABILITY_INDEX_1).mId);
					mDefenderAbilityView
							.setSelection(Pokemon.ABILITY_INDEX_1);
				} else {
					mDefender.setAbility(position);
				}
			}
			break;
		case R.id.damage_calc_attacker_item:
			break;
		case R.id.damage_calc_defender_item:
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
					this);
		} else {

			getLoaderManager().restartLoader(LOADER_DEFENDING_POKEMON, args,
					this);
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