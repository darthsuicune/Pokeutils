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
import android.widget.Toast;

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
		mPokemonNameEditText.setAdapter(getAutoCompleteAdapter());
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

	private SimpleCursorAdapter getAutoCompleteAdapter() {
		CursorLoader loader = (CursorLoader) getActivity()
				.getSupportLoaderManager().initLoader(0, null,
						new MyPokemonLoader());
		Cursor cursor = loader.loadInBackground();
		String[] from = { PokeContract.PokemonTable.POKEMON_NAME };
		int[] to = { android.R.id.text1 };
		return new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, cursor, from,
				to, 0);

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.hashCode() == mStatHPView.getText().hashCode()
				|| s.hashCode() == mEVHPView.getText().hashCode()) {
			calculateIV(CODE_HP);
		} else if (s.hashCode() == mStatAttView.getText().hashCode()
				|| s.hashCode() == mEVAttView.getText().hashCode()) {
			calculateIV(CODE_ATT);
		} else if (s.hashCode() == mStatDefView.getText().hashCode()
				|| s.hashCode() == mEVDefView.getText().hashCode()) {
			calculateIV(CODE_DEF);
		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpAttView.getText().hashCode()) {
			calculateIV(CODE_SP_ATT);
		} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
				|| s.hashCode() == mEVSpDefView.getText().hashCode()) {
			calculateIV(CODE_SP_DEF);
		} else if (s.hashCode() == mStatSpeedView.getText().hashCode()
				|| s.hashCode() == mEVSpeedView.getText().hashCode()) {
			calculateIV(CODE_SPEED);
		}
	}

	private void calculateIV(int code) {
		int currentLevel = Integer.parseInt(mPokemonLevelEditText.getText()
				.toString());

		if (currentLevel > 100 || currentLevel < 1) {
			return;
		}
		ArrayList<Integer> IVList = new ArrayList<Integer>();
		int currentStatValue;
		int currentEVValue;

		switch (code) {
		case CODE_HP:
			currentStatValue = Integer.parseInt(mStatHPView.getText()
					.toString());
			if (currentStatValue < MIN_HP || currentStatValue > MAX_HP) {
				return;
			}
			currentEVValue = Integer.parseInt(mEVHPView.getText().toString());
			if (currentEVValue < 0 || currentEVValue > 255) {
				return;
			}

			int minIVValue = -1;
			int maxIVValue = -1;

			for (int iv = 1; iv < 31; iv++) {
				if (currentStatValue == (((iv + (2 * baseHP)
						+ (currentEVValue / 4) + 100) * currentLevel) / 100) + 10) {
					IVList.add(iv);
				}
			}

			if (IVList.size() < 1) {
				return;
			}

			mIVHPView.setText(minIVValue + "-" + maxIVValue);
			return;
		case CODE_ATT:
			currentStatValue = Integer.parseInt(mStatHPView.getText()
					.toString());
			if (currentStatValue < MIN_ATT || currentStatValue > MAX_ATT) {
				return;
			}
			currentEVValue = Integer.parseInt(mEVHPView.getText().toString());
			if (currentEVValue < 0 || currentEVValue > 255) {
				return;
			}
			
			break;
		case CODE_DEF:
			break;
		case CODE_SP_ATT:
			break;
		case CODE_SP_DEF:
			break;
		case CODE_SPEED:
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

			String[] projection = {
					PokeContract.PokemonTable._ID,
					PokeContract.PokemonTable.POKEMON_NAME,
					PokeContract.PokemonTable.BASE_STAT_HP,
					PokeContract.PokemonTable.BASE_STAT_ATT,
					PokeContract.PokemonTable.BASE_STAT_DEF,
					PokeContract.PokemonTable.BASE_STAT_SPATT,
					PokeContract.PokemonTable.BASE_STAT_SPDEF,
					PokeContract.PokemonTable.BASE_STAT_SPEED
			};
			String selection = PokeContract.PokemonTable._ID + "=?";
			String[] selectionArgs = { "1" };
			loader = new CursorLoader(getActivity(),
					PokeContract.CONTENT_POKEMON, projection, selection,
					selectionArgs, null);
			switch (id) {
			default:
				break;
			}

			return loader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			switch (loader.getId()) {
			default:
				break;
			}
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}
