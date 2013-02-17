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
	private static final int LOADER_STATS = 2;
	private static final int LOADER_NATURE = 3;
	private static final String ARG_ID = "pokemon_id";

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
		mPokemonNameEditText
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long id) {
						changePokemonStats(id);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

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

	private void changePokemonStats(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ID, id);
		getActivity().getSupportLoaderManager().restartLoader(LOADER_STATS,
				args, new MyPokemonLoader());
	}

	private void setAutoCompleteAdapter() {
		getActivity().getSupportLoaderManager().restartLoader(
				LOADER_AUTO_COMPLETE, null, new MyPokemonLoader());
		String[] from = { PokeContract.PokemonTable.POKEMON_NAME };
		int[] to = { android.R.id.text1 };
		mAutoCompleteAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);

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

	private class MyPokemonLoader implements LoaderCallbacks<Cursor> {

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Loader<Cursor> loader = null;

			Uri uri = PokeContract.CONTENT_POKEMON;

			switch (id) {
			case LOADER_AUTO_COMPLETE:
				String[] autoCompleteProjection = {
						PokeContract.PokemonTable._ID,
						PokeContract.PokemonTable.POKEMON_NAME };
				loader = new CursorLoader(getActivity(), uri,
						autoCompleteProjection, null, null, null);
				break;
			case LOADER_STATS:
				String[] projection = { PokeContract.PokemonTable._ID,
						PokeContract.PokemonTable.BASE_STAT_HP,
						PokeContract.PokemonTable.BASE_STAT_ATT,
						PokeContract.PokemonTable.BASE_STAT_DEF,
						PokeContract.PokemonTable.BASE_STAT_SPATT,
						PokeContract.PokemonTable.BASE_STAT_SPDEF,
						PokeContract.PokemonTable.BASE_STAT_SPEED };
				String selection = PokeContract.PokemonTable._ID + "=?";
				String[] selectionArgs = { Long.toString(args.getLong(ARG_ID)) };
				loader = new CursorLoader(getActivity(), uri, projection,
						selection, selectionArgs, null);
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
			case LOADER_STATS:
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					baseHP = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_HP));
					baseAtt = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_ATT));
					baseDef = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_DEF));
					baseSpAtt = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPATT));
					baseSpDef = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPDEF));
					baseSpeed = cursor
							.getInt(cursor
									.getColumnIndex(PokeContract.PokemonTable.BASE_STAT_SPEED));
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
			}
		}
	}

	private void setNature() {
		mIVAttTextView.setTextColor(Color.BLACK);
		mIVDefTextView.setTextColor(Color.BLACK);
		mIVSpeedTextView.setTextColor(Color.BLACK);
		mIVSpAttTextView.setTextColor(Color.BLACK);
		mIVSpDefTextView.setTextColor(Color.BLACK);

		if (mNature.equals(getActivity().getString(R.string.nature_lonely))) {
			mIVAttTextView.setTextColor(Color.GREEN);
			mIVDefTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_brave))) {
			mIVAttTextView.setTextColor(Color.GREEN);
			mIVSpeedTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_adamant))) {
			mIVAttTextView.setTextColor(Color.GREEN);
			mIVSpAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_naughty))) {
			mIVAttTextView.setTextColor(Color.GREEN);
			mIVSpDefTextView.setTextColor(Color.RED);
			
		} else if (mNature.equals(getActivity().getString(R.string.nature_bold))) {
			mIVDefTextView.setTextColor(Color.GREEN);
			mIVAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_relaxed))) {
			mIVDefTextView.setTextColor(Color.GREEN);
			mIVSpeedTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_impish))) {
			mIVDefTextView.setTextColor(Color.GREEN);
			mIVSpAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_lax))) {
			mIVDefTextView.setTextColor(Color.GREEN);
			mIVSpDefTextView.setTextColor(Color.RED);

		} else if (mNature.equals(getActivity().getString(R.string.nature_timid))) {
			mIVSpeedTextView.setTextColor(Color.GREEN);
			mIVAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_hasty))) {
			mIVSpeedTextView.setTextColor(Color.GREEN);
			mIVDefTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_jolly))) {
			mIVSpeedTextView.setTextColor(Color.GREEN);
			mIVSpAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_naive))) {
			mIVSpeedTextView.setTextColor(Color.GREEN);
			mIVSpDefTextView.setTextColor(Color.RED);

		} else if (mNature.equals(getActivity().getString(R.string.nature_modest))) {
			mIVSpAttTextView.setTextColor(Color.GREEN);
			mIVAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_mild))) {
			mIVSpAttTextView.setTextColor(Color.GREEN);
			mIVDefTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_quiet))) {
			mIVSpAttTextView.setTextColor(Color.GREEN);
			mIVSpeedTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_rash))) {
			mIVSpAttTextView.setTextColor(Color.GREEN);
			mIVSpDefTextView.setTextColor(Color.RED);

		} else if (mNature.equals(getActivity().getString(R.string.nature_calm))) {
			mIVSpDefTextView.setTextColor(Color.GREEN);
			mIVAttTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_gentle))) {
			mIVSpDefTextView.setTextColor(Color.GREEN);
			mIVDefTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_sassy))) {
			mIVSpDefTextView.setTextColor(Color.GREEN);
			mIVSpeedTextView.setTextColor(Color.RED);
		} else if (mNature.equals(getActivity().getString(R.string.nature_careful))) {
			mIVSpDefTextView.setTextColor(Color.GREEN);
			mIVSpAttTextView.setTextColor(Color.RED);
		}
	}
}
