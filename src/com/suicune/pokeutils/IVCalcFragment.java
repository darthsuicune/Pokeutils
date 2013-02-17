package com.suicune.pokeutils;

import java.util.ArrayList;

import android.database.Cursor;
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

	private TextView mIVHPView;
	private TextView mIVAttView;
	private TextView mIVDefView;
	private TextView mIVSpAttView;
	private TextView mIVSpDefView;
	private TextView mIVSpeedView;

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

		mNatureSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				changeNature(id);

				mNature = ((TextView) view.findViewById(android.R.id.text1))
						.getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void changePokemonStats(long id) {
	}
	
	private void changeNature(long id){
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
		ArrayList<Integer> ivs = null;
		if (s.hashCode() == mStatHPView.getText().hashCode()
				|| s.hashCode() == mEVHPView.getText().hashCode()) {
			ivs = calculateIV(CODE_HP);
			if (ivs != null) {
				showIVs(CODE_HP, ivs);
			}
		} else if (s.hashCode() == mStatAttView.getText().hashCode()
				|| s.hashCode() == mEVAttView.getText().hashCode()) {
			ivs = calculateIV(CODE_ATT);
			if (ivs != null) {
				showIVs(CODE_ATT, ivs);
			}

		} else if (s.hashCode() == mStatDefView.getText().hashCode()
				|| s.hashCode() == mEVDefView.getText().hashCode()) {
			ivs = calculateIV(CODE_DEF);
			if (ivs != null) {
				showIVs(CODE_DEF, ivs);
			}

		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpAttView.getText().hashCode()) {
			ivs = calculateIV(CODE_SP_ATT);
			if (ivs != null) {
				showIVs(CODE_SP_ATT, ivs);
			}

		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpDefView.getText().hashCode()) {
			ivs = calculateIV(CODE_SP_DEF);
			if (ivs != null) {
				showIVs(CODE_SP_DEF, ivs);
			}

		} else if (s.hashCode() == mStatSpeedView.getText().hashCode()
				|| s.hashCode() == mEVSpeedView.getText().hashCode()) {
			ivs = calculateIV(CODE_SPEED);
			if (ivs != null) {
				showIVs(CODE_SPEED, ivs);
			}

		}
	}

	private ArrayList<Integer> calculateIV(int code) {
		int currentLevel = Integer.parseInt(mPokemonLevelEditText.getText()
				.toString());

		if (currentLevel > 100 || currentLevel < 1) {
			return null;
		}
		ArrayList<Integer> ivList = new ArrayList<Integer>();
		
		int currentStatValue = 0;
		int currentEVValue = 0;
		int currentNatureModifier = 100;
		int baseStat = 0;

		switch (code) {
		case CODE_HP:
			currentStatValue = Integer.parseInt(mStatHPView.getText()
					.toString());
			if (currentStatValue < MIN_HP || currentStatValue > MAX_HP) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVHPView.getText().toString());
			if (currentEVValue < 0 || currentEVValue > 255) {
				return null;
			}

			for (int iv = 0; iv < 31; iv++) {
				if (currentStatValue == (((iv + (2 * baseHP) + (currentEVValue / 4) + 100) * currentLevel) / 100) + 10) {
					ivList.add(iv);
				}
			}

			if (ivList.size() < 1) {
				return null;
			}

			return null;
		case CODE_ATT:
			currentStatValue = Integer.parseInt(mStatAttView.getText()
					.toString());
			if (currentStatValue < MIN_ATT || currentStatValue > MAX_ATT) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVAttView.getText().toString());
			
			if(mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")){
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseAtt;
			break;
		case CODE_DEF:
			currentStatValue = Integer.parseInt(mStatDefView.getText()
					.toString());
			if (currentStatValue < MIN_DEF || currentStatValue > MAX_DEF) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVDefView.getText().toString());			
			if(mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")){
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseDef;
			break;
		case CODE_SP_ATT:
			currentStatValue = Integer.parseInt(mStatSpAttView.getText()
					.toString());
			if (currentStatValue < MIN_SP_ATT || currentStatValue > MAX_SP_ATT) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVSpAttView.getText().toString());
			
			if(mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")){
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseSpAtt;
			break;
		case CODE_SP_DEF:
			currentStatValue = Integer.parseInt(mStatSpDefView.getText()
					.toString());
			if (currentStatValue < MIN_SP_DEF || currentStatValue > MAX_SP_DEF) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVSpDefView.getText().toString());
			
			if(mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")){
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}			
			baseStat = baseSpDef;
			break;
		case CODE_SPEED:
			currentStatValue = Integer.parseInt(mStatSpeedView.getText()
					.toString());
			if (currentStatValue < MIN_SPEED || currentStatValue > MAX_SPEED) {
				return null;
			}
			currentEVValue = Integer.parseInt(mEVSpeedView.getText().toString());
			
			if(mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")){
				currentNatureModifier = 110;
			} else if (mNature.equals("") || mNature.equals("") || mNature.equals("") || mNature.equals("")) {
				currentNatureModifier = 90;
			}
			baseStat = baseSpeed;
			break;
		}
		if (currentEVValue < 0 || currentEVValue > 255) {
			return null;
		}

		for (int iv = 0; iv < 31; iv ++){
			if (currentStatValue == Math.floor(((((iv + (2 * baseStat) + (currentEVValue / 4))* currentLevel) / 100) + 5) * (currentNatureModifier/100))) {
				ivList.add(iv);
			}
		}
		
		return ivList;
	}

	private void showIVs(int code, ArrayList<Integer> ivs) {
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
		switch (code) {
		case CODE_HP:
			mIVHPView.setText("" + minIVValue + "-" + maxIVValue);
			break;
		case CODE_ATT:
			mIVAttView.setText("" + minIVValue + "-" + maxIVValue);
			break;
		case CODE_DEF:
			mIVDefView.setText("" + minIVValue + "-" + maxIVValue);
			break;
		case CODE_SP_ATT:
			mIVSpAttView.setText("" + minIVValue + "-" + maxIVValue);
			break;
		case CODE_SP_DEF:
			mIVSpDefView.setText("" + minIVValue + "-" + maxIVValue);
			break;
		case CODE_SPEED:
			mIVSpeedView.setText("" + minIVValue + "-" + maxIVValue);
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

			String[] projection = { PokeContract.PokemonTable._ID,
					PokeContract.PokemonTable.POKEMON_NAME,
					PokeContract.PokemonTable.BASE_STAT_HP,
					PokeContract.PokemonTable.BASE_STAT_ATT,
					PokeContract.PokemonTable.BASE_STAT_DEF,
					PokeContract.PokemonTable.BASE_STAT_SPATT,
					PokeContract.PokemonTable.BASE_STAT_SPDEF,
					PokeContract.PokemonTable.BASE_STAT_SPEED };
			String selection = PokeContract.PokemonTable._ID + "=?";
			String[] selectionArgs = { "1" };

			switch (id) {
			case LOADER_AUTO_COMPLETE:
				loader = new CursorLoader(getActivity(),
						PokeContract.CONTENT_POKEMON, projection, selection,
						selectionArgs, null);
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
}
