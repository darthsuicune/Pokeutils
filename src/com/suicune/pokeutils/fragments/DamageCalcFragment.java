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
	private Spinner mAttackModifierView;
	private int mAttackModifierPosition = 6;

	// Defender views and constants
	private TeamPokemon mDefender;
	private Spinner mDefenderNatureView;
	private AutoCompleteTextView mDefenderView;
	private EditText mDefenderLevelView;
	private TextView mDefenderHpView;
	private TextView mDefenderDefView;
	private TextView mDefenderSpDefView;
	private TextView mDefenderSpeedView;

	private Attack mAttack;
	private Spinner mAttackView;
	private EditText mAttackBaseDamageView;
	private int mAttackChoicePosition = -1;

	private Spinner mDefenseModifierView;
	private int mDefenseModifierPosition = 6;

	private TextView mResultView;

	private SimpleCursorAdapter mAttackerAdapter;
	private SimpleCursorAdapter mDefenderAdapter;
	private SimpleCursorAdapter mAttacksAdapter;

	private String mPokemonName;

	private String[] mAttackNames;

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
		mAttackNames = getResources().getStringArray(R.array.moves);
		if (savedInstanceState != null) {
			mAttackModifierPosition = savedInstanceState
					.getInt("attack modififer position");
			mDefenseModifierPosition = savedInstanceState
					.getInt("defense modifier position");
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
		} else {
			setViews();
		}
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(ARG_ATTACK_ID)) {
			mAttackChoicePosition = savedInstanceState.getInt(ARG_ATTACK_ID);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("attack modififer position", mAttackModifierPosition);
		outState.putInt("defense modifier position", mDefenseModifierPosition);
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

	private void setViews() {
		mAttackerLevelView = (EditText) getActivity().findViewById(
				R.id.damage_calc_attacker_level);
		if (mAttackerLevelView == null) {
			return;
		}

		mAttackerNatureView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_attacker_nature);

		mAttackerHpView = (TextView) getActivity().findViewById(
				R.id.damage_calc_attacker_hp);
		mAttackerAttView = (TextView) getActivity().findViewById(
				R.id.damage_calc_attacker_att);
		mAttackerSpAttView = (TextView) getActivity().findViewById(
				R.id.damage_calc_attacker_sp_att);
		mAttackerSpeedView = (TextView) getActivity().findViewById(
				R.id.damage_calc_attacker_speed);

		mDefenderNatureView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defender_nature);
		mDefenderLevelView = (EditText) getActivity().findViewById(
				R.id.damage_calc_defender_level);
		mDefenderHpView = (TextView) getActivity().findViewById(
				R.id.damage_calc_defender_hp);
		mDefenderDefView = (TextView) getActivity().findViewById(
				R.id.damage_calc_defender_def);
		mDefenderSpDefView = (TextView) getActivity().findViewById(
				R.id.damage_calc_defender_sp_def);
		mDefenderSpeedView = (TextView) getActivity().findViewById(
				R.id.damage_calc_defender_speed);

		mAttackerLevelView.setText("100");
		mDefenderLevelView.setText("100");
		mResultView = (TextView) getActivity().findViewById(
				R.id.damage_calc_damage);
		prepareNatureSpinners();
		prepareAutoCompleteViews();
		prepareAttackViews();
		prepareDefenseViews();

		mAttackerView.addTextChangedListener(this);
		mDefenderView.addTextChangedListener(this);
		mAttackerLevelView.addTextChangedListener(this);
		mDefenderLevelView.addTextChangedListener(this);
		mAttackBaseDamageView.addTextChangedListener(this);
	}

	private void prepareNatureSpinners() {
		mAttackerNatureView.setAdapter(getNaturesAdapter());
		mDefenderNatureView.setAdapter(getNaturesAdapter());

		mAttackerNatureView.setOnItemSelectedListener(this);
		mDefenderNatureView.setOnItemSelectedListener(this);
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

	private void prepareAutoCompleteViews() {
		mAttackerView = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_1);
		mDefenderView = (AutoCompleteTextView) getActivity().findViewById(
				R.id.damage_calc_pokemon_2);
		setAutoCompleteAdapters();

		mAttackerView.setOnItemClickListener(this);
		mDefenderView.setOnItemClickListener(this);
	}

	private void setAutoCompleteAdapters() {
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mAttackerAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mDefenderAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);

		mAttackerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mDefenderAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		mAttackerAdapter.setCursorToStringConverter(this);
		mDefenderAdapter.setCursorToStringConverter(this);

		mAttackerView.setAdapter(mAttackerAdapter);
		mDefenderView.setAdapter(mDefenderAdapter);
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

		setAttackSpinnerAdapter();
		mAttackView.setAdapter(mAttacksAdapter);
		mAttackView.setOnItemSelectedListener(this);
	}

	private void prepareDefenseViews() {
		mDefenseModifierView = (Spinner) getActivity().findViewById(
				R.id.damage_calc_defense_modifier);
		mDefenseModifierView.setSelection(mDefenseModifierPosition);
		mDefenseModifierView.setOnItemSelectedListener(this);
	}

	private void setAttackSpinnerAdapter() {
		String[] from = { PokeContract.Attacks._ID };
		int[] to = { android.R.id.text1 };
		mAttacksAdapter = new AttacksAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null, from, to, 0);
		mAttacksAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	private void loadAttack(long attack) {
		Bundle args = new Bundle();
		args.putLong(ARG_ATTACK_ID, attack);
		getLoaderManager().restartLoader(LOADER_ATTACK, args, this);
	}

	private void setAttackParameters() {
		mAttackBaseDamageView.setText("" + mAttack.mPower);
	}

	private void setAttackerStats() {
		mAttacker.setIvs(new int[] { 31, 31, 31, 31, 31, 31 });
		mAttacker.setEvs(new int[] { 252, 252, 252, 252, 252, 252 });
		mAttacker.setStats();

		mAttackerHpView.setText("" + mAttacker.mStats[TeamPokemon.INDEX_HP]);
		mAttackerAttView.setText("" + mAttacker.mStats[TeamPokemon.INDEX_ATT]);
		mAttackerSpAttView.setText(""
				+ mAttacker.mStats[TeamPokemon.INDEX_SP_ATT]);
		mAttackerSpeedView.setText(""
				+ mAttacker.mStats[TeamPokemon.INDEX_SPEED]);
	}

	private void setDefenderStats() {
		mDefender.setIvs(new int[] { 31, 31, 31, 31, 31, 31 });
		mDefender.setEvs(new int[] { 252, 252, 252, 252, 252, 252 });
		mDefender.setStats();

		mDefenderHpView.setText("" + mDefender.mStats[TeamPokemon.INDEX_HP]);
		mDefenderDefView.setText("" + mDefender.mStats[TeamPokemon.INDEX_DEF]);
		mDefenderSpDefView.setText(""
				+ mDefender.mStats[TeamPokemon.INDEX_SP_DEF]);
		mDefenderSpeedView.setText(""
				+ mDefender.mStats[TeamPokemon.INDEX_SPEED]);
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (!s.equals("")) {
			mPokemonName = s.toString();
			if (s.hashCode() == mAttackerView.getText().hashCode()) {
				isAttacker = true;
				getLoaderManager().restartLoader(
						LOADER_ATTACKING_POKEMON_AUTO_COMPLETE, null, this);
			} else if (s.hashCode() == mDefenderView.getText().hashCode()) {
				isAttacker = false;
				getLoaderManager().restartLoader(
						LOADER_DEFENDING_POKEMON_AUTO_COMPLETE, null, this);
			}
		} else {
			mPokemonName = "";
		}
		calculateDamage();
	}

	// TODO: Modify to use new calls
	private void calculateDamage() {
		if (mAttacker == null || mDefender == null || mAttack == null
				|| TextUtils.isEmpty(mAttackerLevelView.getText())
				|| TextUtils.isEmpty(mDefenderLevelView.getText())
				|| TextUtils.isEmpty(mAttackBaseDamageView.getText())) {
			return;
		}

		int attackerLevel = Integer.parseInt(mAttackerLevelView.getText()
				.toString());
		int pokemonAttackStat;
		int pokemonDefenseStat;
		int pokemonHP;
		try {
			if (mAttack.mAttackClass == Attack.CLASS_PHYSICAL) {
				pokemonAttackStat = Integer.parseInt(mAttackerAttView.getText()
						.toString());
				pokemonDefenseStat = Integer.parseInt(mDefenderDefView
						.getText().toString());
			} else if (mAttack.mAttackClass == Attack.CLASS_SPECIAL) {
				pokemonAttackStat = Integer.parseInt(mAttackerSpAttView
						.getText().toString());
				pokemonDefenseStat = Integer.parseInt(mDefenderSpDefView
						.getText().toString());
			} else {
				mResultView.setText(R.string.no_damage);
				return;
			}
			pokemonHP = Integer.parseInt(mDefenderHpView.getText().toString());
		} catch (NumberFormatException e) {
			// If the user inserts something which is not a number, do nothing.
			return;
		}

		double attackLevelModifier = DamageCalcTools
				.getStatModifier(mAttackModifierPosition);
		int attackingType = mAttack.mType;

		double defenseLevelModifier = DamageCalcTools
				.getStatModifier(mDefenseModifierPosition);
		int defendingType1 = mDefender.mType1;
		int defendingType2 = mDefender.mType2;

		double typeModifier = DamageCalcTools.getTypeModifier(attackingType,
				defendingType1, defendingType2);
		int attackBasePower = Integer.parseInt(mAttackBaseDamageView.getText()
				.toString());

		boolean hasStab = ((mAttacker.mType1 == mAttack.mType) || (mAttacker.mType2 == mAttack.mType));

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
					+ "=? AND " + PokeContract.PokemonAttacks.FORM + "=? AND "
					+ PokeContract.Attacks.POWER + " > 0";
			String[] attacksSelectionArgs = {
					Integer.toString(mAttacker.mNumber),
					Integer.toString(mAttacker.mForm) };
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
			mAttackModifierPosition = position;
			break;
		case R.id.damage_calc_defense_modifier:
			mDefenseModifierPosition = position;
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
			v.setText(mAttackNames[Integer.parseInt(text)]);
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
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
