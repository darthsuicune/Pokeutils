package com.suicune.pokeutils;

import java.util.ArrayList;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.suicune.pokeutils.database.PokeContract;

public class IVCalcFragment extends Fragment implements TextWatcher {
	private static final int CODE_HP = 0;
	private static final int CODE_ATT = 1;
	private static final int CODE_DEF = 2;
	private static final int CODE_SP_ATT = 3;
	private static final int CODE_SP_DEF = 4;
	private static final int CODE_SPEED = 5;

	private static final int MIN_HP = 1;
	private static final int MAX_HP = 714;
	private static final int MIN_ATT = 41;
	private static final int MAX_ATT = 504;
	private static final int MIN_DEF = 41;
	private static final int MAX_DEF = 614;
	private static final int MIN_SP_ATT = 50;
	private static final int MAX_SP_ATT = 504;
	private static final int MIN_SP_DEF = 68;
	private static final int MAX_SP_DEF = 614;
	private static final int MIN_SPEED = 41;
	private static final int MAX_SPEED = 504;

	private static final int LOADER_AUTO_COMPLETE = 1;
	private static final int LOADER_NATURE = 2;

	private TextView mIVHPView;
	private TextView mIVAttView;
	private TextView mIVDefView;
	private TextView mIVSpAttView;
	private TextView mIVSpDefView;
	private TextView mIVSpeedView;
	private TextView mIVAttTextView;
	private TextView mIVDefTextView;
	private TextView mIVSpAttTextView;
	private TextView mIVSpDefTextView;
	private TextView mIVSpeedTextView;

	private EditText mStatHPView;
	private EditText mStatAttView;
	private EditText mStatDefView;
	private EditText mStatSpAttView;
	private EditText mStatSpDefView;
	private EditText mStatSpeedView;

	private EditText mEVHPView;
	private EditText mEVAttView;
	private EditText mEVDefView;
	private EditText mEVSpAttView;
	private EditText mEVSpDefView;
	private EditText mEVSpeedView;

	private TextView mHiddenPowerTypeView;
	private TextView mHiddenPowerPowerView;

	private AutoCompleteTextView mPokemonNameEditText;
	private EditText mPokemonLevelEditText;
	private Spinner mNatureSpinner;

	private int baseHP;
	private int baseAtt;
	private int baseDef;
	private int baseSpAtt;
	private int baseSpDef;
	private int baseSpeed;

	private String mNature;

	private String mPokemonName = "";

	private SimpleCursorAdapter mAutoCompleteAdapter;
	private SimpleCursorAdapter mNatureAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstancemState) {
		return inflater.inflate(R.layout.iv_calc, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstancemState) {
		super.onActivityCreated(savedInstancemState);
		setViews();
		setListeners();
	}

	void setViews() {
		mHiddenPowerTypeView = (TextView) getActivity().findViewById(
				R.id.iv_calc_hidden_power_type);
		mHiddenPowerPowerView = (TextView) getActivity().findViewById(
				R.id.iv_calc_hidden_power_power);
		mIVHPView = (TextView) getActivity().findViewById(R.id.iv_calc_iv_hp);
		mIVAttView = (TextView) getActivity().findViewById(R.id.iv_calc_iv_att);
		mIVDefView = (TextView) getActivity().findViewById(R.id.iv_calc_iv_def);
		mIVSpAttView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_sp_att);
		mIVSpDefView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_sp_def);
		mIVSpeedView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_speed);

		mIVAttTextView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_att_text);
		mIVDefTextView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_def_text);
		mIVSpAttTextView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_sp_att_text);
		mIVSpDefTextView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_sp_def_text);
		mIVSpeedTextView = (TextView) getActivity().findViewById(
				R.id.iv_calc_iv_speed_text);

		mStatHPView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_hp);
		mStatAttView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_att);
		mStatDefView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_def);
		mStatSpAttView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_sp_att);
		mStatSpDefView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_sp_def);
		mStatSpeedView = (EditText) getActivity().findViewById(
				R.id.iv_calc_stat_speed);

		mEVHPView = (EditText) getActivity().findViewById(R.id.iv_calc_ev_hp);
		mEVAttView = (EditText) getActivity().findViewById(R.id.iv_calc_ev_att);
		mEVDefView = (EditText) getActivity().findViewById(R.id.iv_calc_ev_def);
		mEVSpAttView = (EditText) getActivity().findViewById(
				R.id.iv_calc_ev_sp_att);
		mEVSpDefView = (EditText) getActivity().findViewById(
				R.id.iv_calc_ev_sp_def);
		mEVSpeedView = (EditText) getActivity().findViewById(
				R.id.iv_calc_ev_speed);

		mPokemonNameEditText = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.iv_calc_pokemon_name);
		mPokemonLevelEditText = (EditText) getActivity().findViewById(
				R.id.iv_calc_pokemon_level);
		mNatureSpinner = (Spinner) getActivity().findViewById(
				R.id.iv_calc_nature);
	}

	private void setListeners() {
		setAutoCompleteAdapter();
		mPokemonNameEditText.setAdapter(mAutoCompleteAdapter);
		mPokemonNameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (!s.equals(""))
					mPokemonName = s.toString();
				else
					mPokemonName = "";
				getActivity().getSupportLoaderManager().restartLoader(
						LOADER_AUTO_COMPLETE, null, new MyPokemonLoader());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		mStatHPView.addTextChangedListener(this);
		mStatAttView.addTextChangedListener(this);
		mStatDefView.addTextChangedListener(this);
		mStatSpAttView.addTextChangedListener(this);
		mStatSpDefView.addTextChangedListener(this);
		mStatSpeedView.addTextChangedListener(this);

		mEVHPView.addTextChangedListener(this);
		mEVAttView.addTextChangedListener(this);
		mEVDefView.addTextChangedListener(this);
		mEVSpAttView.addTextChangedListener(this);
		mEVSpDefView.addTextChangedListener(this);
		mEVSpeedView.addTextChangedListener(this);

		setNatureSpinnerAdapter();
		mNatureSpinner.setAdapter(mNatureAdapter);
		mNatureSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				mNature = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
				setNature();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void setAutoCompleteAdapter() {
		getActivity().getSupportLoaderManager().restartLoader(
				LOADER_AUTO_COMPLETE, null, new MyPokemonLoader());
		String[] from = { PokeContract.PokemonTable.POKEMON_NAME };
		int[] to = { android.R.id.text1 };
		mAutoCompleteAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mAutoCompleteAdapter
				.setCursorToStringConverter(new CursorToStringConverter() {

					@Override
					public CharSequence convertToString(Cursor c) {
						TextView hpBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_hp);
						TextView attBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_att);
						TextView defBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_def);
						TextView spattBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_sp_att);
						TextView spdefBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_sp_def);
						TextView speedBase = (TextView) getActivity()
								.findViewById(R.id.iv_calc_base_speed);

						baseHP = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_HP)));
						baseAtt = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_ATT)));
						baseDef = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_DEF)));
						baseSpAtt = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPATT)));
						baseSpDef = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPDEF)));
						baseSpeed = Integer.parseInt(c.getString(c
								.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPEED)));

						hpBase.setText("" + baseHP);
						attBase.setText("" + baseAtt);
						defBase.setText("" + baseDef);
						spattBase.setText("" + baseSpAtt);
						spdefBase.setText("" + baseSpDef);
						speedBase.setText("" + baseSpeed);
						return c.getString(c
								.getColumnIndexOrThrow(PokeContract.PokemonTable.POKEMON_NAME));
					}
				});

	}

	private void setNatureSpinnerAdapter() {
		getActivity().getSupportLoaderManager().restartLoader(LOADER_NATURE,
				null, new MyPokemonLoader());
		String[] from = { PokeContract.NaturesTable.NATURE_NAME };
		int[] to = { android.R.id.text1 };
		mNatureAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null, from, to, 0);
		mNatureAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	@Override
	public void afterTextChanged(Editable s) {
		int level;
		try {
			level = Integer
					.parseInt(mPokemonLevelEditText.getText().toString());
		} catch (NumberFormatException e) {
			return;
		}

		if (level > 100 || level < 1) {
			return;
		}

		ArrayList<Integer> ivs = null;
		int statValue;
		int evValue;

		if (s.hashCode() == mStatHPView.getText().hashCode()
				|| s.hashCode() == mEVHPView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatHPView.getText().toString());
				evValue = Integer.parseInt(mEVHPView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_HP, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_HP, ivs);
			}
		} else if (s.hashCode() == mStatAttView.getText().hashCode()
				|| s.hashCode() == mEVAttView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatAttView.getText().toString());
				evValue = Integer.parseInt(mEVAttView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_ATT, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_ATT, ivs);
			}

		} else if (s.hashCode() == mStatDefView.getText().hashCode()
				|| s.hashCode() == mEVDefView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatDefView.getText().toString());
				evValue = Integer.parseInt(mEVDefView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_DEF, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_DEF, ivs);
			}

		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpAttView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatSpAttView.getText()
						.toString());
				evValue = Integer.parseInt(mEVSpAttView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_SP_ATT, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_SP_ATT, ivs);
			}

		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpDefView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatSpDefView.getText()
						.toString());
				evValue = Integer.parseInt(mEVSpDefView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_SP_DEF, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_SP_DEF, ivs);
			}

		} else if (s.hashCode() == mStatSpeedView.getText().hashCode()
				|| s.hashCode() == mEVSpeedView.getText().hashCode()) {
			try {
				statValue = Integer.parseInt(mStatSpeedView.getText()
						.toString());
				evValue = Integer.parseInt(mEVSpeedView.getText().toString());
			} catch (NumberFormatException e) {
				return;
			}
			ivs = calculateIV(CODE_SPEED, statValue, evValue, level);
			if (ivs != null) {
				showIVs(CODE_SPEED, ivs);
			}
		}
		int hpIv = -1;
		int attIv = -1;
		int defIv = -1;
		int spAttIv = -1;
		int spDefIv = -1;
		int speedIv = -1;
		try {
			hpIv = Integer.parseInt(mIVHPView.getText().toString());
			attIv = Integer.parseInt(mIVAttView.getText().toString());
			defIv = Integer.parseInt(mIVDefView.getText().toString());
			speedIv = Integer.parseInt(mIVSpeedView.getText().toString());
			spAttIv = Integer.parseInt(mIVSpAttTextView.getText().toString());
			spDefIv = Integer.parseInt(mIVSpDefTextView.getText().toString());
		} catch (NumberFormatException e) {
			return;
		}
		
		mHiddenPowerTypeView.setText(": " + getHiddenPowerType(hpIv, attIv, defIv,
				spAttIv, spDefIv, speedIv));
		mHiddenPowerPowerView.setText(" " + getHiddenPowerPower(hpIv, attIv, defIv, spAttIv,
				spDefIv, speedIv));
	}

	private ArrayList<Integer> calculateIV(int code, int currentStat,
			int currentEv, int currentLevel) {
		if (currentEv < 0 || currentEv > 255) {
			return null;
		}
		ArrayList<Integer> ivList = new ArrayList<Integer>();

		int currentNatureModifier = 100;
		int baseStat = 0;

		switch (code) {
		case CODE_HP:
			if (currentStat < MIN_HP || currentStat > MAX_HP) {
				return null;
			}

			for (int iv = 0; iv <= 31; iv++) {
				if (currentStat == Math.floor((((iv + (2 * baseHP)
						+ (currentEv / 4) + 100) * currentLevel) / 100) + 10)) {
					ivList.add(iv);
				}
			}

			if (ivList.size() < 1) {
				return null;
			}

			return ivList;
		case CODE_ATT:
			if (currentStat < MIN_ATT || currentStat > MAX_ATT) {
				return null;
			}

			if (mNature.equals("") || mNature.equals("") || mNature.equals("")
					|| mNature.equals("")) {
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("")
					|| mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseAtt;
			break;
		case CODE_DEF:
			if (currentStat < MIN_DEF || currentStat > MAX_DEF) {
				return null;
			}

			if (mNature.equals("") || mNature.equals("") || mNature.equals("")
					|| mNature.equals("")) {
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("")
					|| mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseDef;
			break;
		case CODE_SP_ATT:
			if (currentStat < MIN_SP_ATT || currentStat > MAX_SP_ATT) {
				return null;
			}

			if (mNature.equals("") || mNature.equals("") || mNature.equals("")
					|| mNature.equals("")) {
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("")
					|| mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseSpAtt;
			break;
		case CODE_SP_DEF:
			if (currentStat < MIN_SP_DEF || currentStat > MAX_SP_DEF) {
				return null;
			}

			if (mNature.equals("") || mNature.equals("") || mNature.equals("")
					|| mNature.equals("")) {
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("")
					|| mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseSpDef;
			break;
		case CODE_SPEED:
			if (currentStat < MIN_SPEED || currentStat > MAX_SPEED) {
				return null;
			}

			if (mNature.equals("") || mNature.equals("") || mNature.equals("")
					|| mNature.equals("")) {
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("")
					|| mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseSpeed;
			break;
		}

		for (int iv = 0; iv <= 31; iv++) {
			if (currentStat == Math
					.floor(((((iv + (2 * baseStat) + (currentEv / 4)) * currentLevel) / 100) + 5)
							* (currentNatureModifier / 100))) {
				ivList.add(iv);
			}
		}

		return ivList;
	}

	private void showIVs(int code, ArrayList<Integer> ivs) {
		String result;
		int minIVValue = 32;
		int maxIVValue = -1;
		for (int i = 0; i < ivs.size(); i++) {
			int iv = ivs.get(i);
			if (iv > maxIVValue) {
				maxIVValue = iv;
			}
			if (iv < minIVValue) {
				minIVValue = iv;
			}
		}
		if (minIVValue == maxIVValue) {
			result = "" + minIVValue;
		} else {
			result = "" + minIVValue + "-" + maxIVValue;
		}
		switch (code) {
		case CODE_HP:
			mIVHPView.setText(result);
			break;
		case CODE_ATT:
			mIVAttView.setText(result);
			break;
		case CODE_DEF:
			mIVDefView.setText(result);
			break;
		case CODE_SP_ATT:
			mIVSpAttView.setText(result);
			break;
		case CODE_SP_DEF:
			mIVSpDefView.setText(result);
			break;
		case CODE_SPEED:
			mIVSpeedView.setText(result);
			break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private String getHiddenPowerType(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		String hiddenPower = "";

		Double hiddenPowerTypeDouble = Math.floor(((1 * (hpIv % 2))
				+ (2 * (attIv % 2)) + (4 * (defIv % 2)) + (8 * (speedIv % 2))
				+ (16 * (spAttIv % 2)) + (32 * (spDefIv % 2))) * 15 / 63);
		int hiddenPowerType = hiddenPowerTypeDouble.intValue();

		switch (hiddenPowerType) {
		case 0:
			hiddenPower = getString(R.string.type_fighting);
			break;
		case 1:
			hiddenPower = getString(R.string.type_flying);
			break;
		case 2:
			hiddenPower = getString(R.string.type_poison);
			break;
		case 3:
			hiddenPower = getString(R.string.type_ground);
			break;
		case 4:
			hiddenPower = getString(R.string.type_rock);
			break;
		case 5:
			hiddenPower = getString(R.string.type_bug);
			break;
		case 6:
			hiddenPower = getString(R.string.type_ghost);
			break;
		case 7:
			hiddenPower = getString(R.string.type_steel);
			break;
		case 8:
			hiddenPower = getString(R.string.type_fire);
			break;
		case 9:
			hiddenPower = getString(R.string.type_water);
			break;
		case 10:
			hiddenPower = getString(R.string.type_grass);
			break;
		case 11:
			hiddenPower = getString(R.string.type_electric);
			break;
		case 12:
			hiddenPower = getString(R.string.type_psychic);
			break;
		case 13:
			hiddenPower = getString(R.string.type_ice);
			break;
		case 14:
			hiddenPower = getString(R.string.type_dragon);
			break;
		case 15:
			hiddenPower = getString(R.string.type_dark);
			break;
		}

		return hiddenPower;
	}

	private int getHiddenPowerPower(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		Double hiddenPowerPowerDouble = Math.floor((((1 * getValue(hpIv))
				+ (2 * getValue(attIv)) + (4 * getValue(defIv)) + (8 * getValue(speedIv))
				+ (16 * getValue(spAttIv)) + (32 * getValue(spDefIv))) * 40 / 63) + 30);

		return hiddenPowerPowerDouble.intValue();
	}

	private int getValue(int iv) {
		if (iv % 4 == 2 || iv % 4 == 3) {
			return 1;
		} else {
			return 0;
		}
	}

	private class MyPokemonLoader implements LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Loader<Cursor> loader = null;

			Uri uri = PokeContract.CONTENT_POKEMON;

			switch (id) {
			case LOADER_AUTO_COMPLETE:
				String[] autoCompleteProjection = {
						PokeContract.PokemonTable._ID,
						PokeContract.PokemonTable.POKEMON_NAME,
						PokeContract.PokemonTable.BASE_STAT_HP,
						PokeContract.PokemonTable.BASE_STAT_ATT,
						PokeContract.PokemonTable.BASE_STAT_DEF,
						PokeContract.PokemonTable.BASE_STAT_SPATT,
						PokeContract.PokemonTable.BASE_STAT_SPDEF,
						PokeContract.PokemonTable.BASE_STAT_SPEED, };
				String selection = PokeContract.PokemonTable.POKEMON_NAME
						+ " LIKE ?";
				String[] selectionArgs = { "%" + mPokemonName + "%" };
				loader = new CursorLoader(getActivity(), uri,
						autoCompleteProjection, selection, selectionArgs, null);
				break;
			case LOADER_NATURE:
				uri = PokeContract.CONTENT_NATURE;

				String[] natureProjection = { PokeContract.NaturesTable._ID,
						PokeContract.NaturesTable.NATURE_NAME };

				loader = new CursorLoader(getActivity(), uri, natureProjection,
						null, null, null);
				break;
			}

			return loader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			switch (loader.getId()) {
			case LOADER_AUTO_COMPLETE:
				mAutoCompleteAdapter.swapCursor(cursor);
				break;
			case LOADER_NATURE:
				mNatureAdapter.swapCursor(cursor);

				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					mNature = cursor
							.getString(cursor
									.getColumnIndex(PokeContract.NaturesTable.NATURE_NAME));
					setNature();
				}
				break;
			}
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			switch (loader.getId()) {
			case LOADER_AUTO_COMPLETE:
				mAutoCompleteAdapter.swapCursor(null);
				break;

			case LOADER_NATURE:
				mNatureAdapter.swapCursor(null);
				break;
			}
		}
	}

	private void setNature() {
		mIVAttTextView.setBackgroundColor(Color.TRANSPARENT);
		mIVDefTextView.setBackgroundColor(Color.TRANSPARENT);
		mIVSpeedTextView.setBackgroundColor(Color.TRANSPARENT);
		mIVSpAttTextView.setBackgroundColor(Color.TRANSPARENT);
		mIVSpDefTextView.setBackgroundColor(Color.TRANSPARENT);

		if (mNature.equals(getActivity().getString(R.string.nature_lonely))) {
			mIVAttTextView.setBackgroundColor(Color.GREEN);
			mIVDefTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_brave))) {
			mIVAttTextView.setBackgroundColor(Color.GREEN);
			mIVSpeedTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_adamant))) {
			mIVAttTextView.setBackgroundColor(Color.GREEN);
			mIVSpAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_naughty))) {
			mIVAttTextView.setBackgroundColor(Color.GREEN);
			mIVSpDefTextView.setBackgroundColor(Color.RED);

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_bold))) {
			mIVDefTextView.setBackgroundColor(Color.GREEN);
			mIVAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_relaxed))) {
			mIVDefTextView.setBackgroundColor(Color.GREEN);
			mIVSpeedTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_impish))) {
			mIVDefTextView.setBackgroundColor(Color.GREEN);
			mIVSpAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_lax))) {
			mIVDefTextView.setBackgroundColor(Color.GREEN);
			mIVSpDefTextView.setBackgroundColor(Color.RED);

		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_timid))) {
			mIVSpeedTextView.setBackgroundColor(Color.GREEN);
			mIVAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_hasty))) {
			mIVSpeedTextView.setBackgroundColor(Color.GREEN);
			mIVDefTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_jolly))) {
			mIVSpeedTextView.setBackgroundColor(Color.GREEN);
			mIVSpAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_naive))) {
			mIVSpeedTextView.setBackgroundColor(Color.GREEN);
			mIVSpDefTextView.setBackgroundColor(Color.RED);

		} else if (mNature.equals(getActivity().getString(
				R.string.nature_modest))) {
			mIVSpAttTextView.setBackgroundColor(Color.GREEN);
			mIVAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_mild))) {
			mIVSpAttTextView.setBackgroundColor(Color.GREEN);
			mIVDefTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_quiet))) {
			mIVSpAttTextView.setBackgroundColor(Color.GREEN);
			mIVSpeedTextView.setBackgroundColor(Color.RED);
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_rash))) {
			mIVSpAttTextView.setBackgroundColor(Color.GREEN);
			mIVSpDefTextView.setBackgroundColor(Color.RED);

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_calm))) {
			mIVSpDefTextView.setBackgroundColor(Color.GREEN);
			mIVAttTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_gentle))) {
			mIVSpDefTextView.setBackgroundColor(Color.GREEN);
			mIVDefTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_sassy))) {
			mIVSpDefTextView.setBackgroundColor(Color.GREEN);
			mIVSpeedTextView.setBackgroundColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_careful))) {
			mIVSpDefTextView.setBackgroundColor(Color.GREEN);
			mIVSpAttTextView.setBackgroundColor(Color.RED);
		}
	}
}
