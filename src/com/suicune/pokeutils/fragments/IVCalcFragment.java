package com.suicune.pokeutils.fragments;

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

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;
import com.suicune.pokeutils.tools.IVTools;

public class IVCalcFragment extends Fragment implements TextWatcher,
		LoaderCallbacks<Cursor> {

	private static final int LOADER_AUTO_COMPLETE = 1;
	private static final int LOADER_NATURE = 2;

	private EditText mIVHPView;
	private EditText mIVAttView;
	private EditText mIVDefView;
	private EditText mIVSpAttView;
	private EditText mIVSpDefView;
	private EditText mIVSpeedView;
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
		mIVHPView = (EditText) getActivity().findViewById(R.id.iv_calc_iv_hp);
		mIVAttView = (EditText) getActivity().findViewById(R.id.iv_calc_iv_att);
		mIVDefView = (EditText) getActivity().findViewById(R.id.iv_calc_iv_def);
		mIVSpAttView = (EditText) getActivity().findViewById(
				R.id.iv_calc_iv_sp_att);
		mIVSpDefView = (EditText) getActivity().findViewById(
				R.id.iv_calc_iv_sp_def);
		mIVSpeedView = (EditText) getActivity().findViewById(
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
				if (!s.equals("")) {
					mPokemonName = s.toString();
					getActivity().getSupportLoaderManager().restartLoader(
							LOADER_AUTO_COMPLETE, null, IVCalcFragment.this);
				} else {
					mPokemonName = "";
				}

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

		mIVHPView.addTextChangedListener(this);
		mIVAttView.addTextChangedListener(this);
		mIVDefView.addTextChangedListener(this);
		mIVSpAttView.addTextChangedListener(this);
		mIVSpDefView.addTextChangedListener(this);
		mIVSpeedView.addTextChangedListener(this);

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
				LOADER_AUTO_COMPLETE, null, this);
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
				null, this);
		String[] from = { PokeContract.NaturesTable.NATURE_NAME };
		int[] to = { android.R.id.text1 };
		mNatureAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_item, null, from, to, 0);
		mNatureAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.hashCode() != mIVHPView.getText().hashCode()
				&& s.hashCode() != mIVAttView.getText().hashCode()
				&& s.hashCode() != mIVDefView.getText().hashCode()
				&& s.hashCode() != mIVSpAttView.getText().hashCode()
				&& s.hashCode() != mIVSpDefView.getText().hashCode()
				&& s.hashCode() != mIVSpeedView.getText().hashCode()) {
			int level;
			try {
				level = Integer.parseInt(mPokemonLevelEditText.getText()
						.toString());
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
					statValue = Integer.parseInt(mStatHPView.getText()
							.toString());
					evValue = Integer.parseInt(mEVHPView.getText().toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_HP, getNature(),
						statValue, evValue, level, baseHP);
				if (ivs != null) {
					showIVs(IVTools.CODE_HP, ivs);
				}
			} else if (s.hashCode() == mStatAttView.getText().hashCode()
					|| s.hashCode() == mEVAttView.getText().hashCode()) {
				try {
					statValue = Integer.parseInt(mStatAttView.getText()
							.toString());
					evValue = Integer.parseInt(mEVAttView.getText().toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_ATT, getNature(),
						statValue, evValue, level, baseAtt);
				if (ivs != null) {
					showIVs(IVTools.CODE_ATT, ivs);
				}

			} else if (s.hashCode() == mStatDefView.getText().hashCode()
					|| s.hashCode() == mEVDefView.getText().hashCode()) {
				try {
					statValue = Integer.parseInt(mStatDefView.getText()
							.toString());
					evValue = Integer.parseInt(mEVDefView.getText().toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_DEF, getNature(),
						statValue, evValue, level, baseDef);
				if (ivs != null) {
					showIVs(IVTools.CODE_DEF, ivs);
				}

			} else if (s.hashCode() == mStatSpAttView.getText().hashCode()
					|| s.hashCode() == mEVSpAttView.getText().hashCode()) {
				try {
					statValue = Integer.parseInt(mStatSpAttView.getText()
							.toString());
					evValue = Integer.parseInt(mEVSpAttView.getText()
							.toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_SP_ATT, getNature(),
						statValue, evValue, level, baseSpAtt);
				if (ivs != null) {
					showIVs(IVTools.CODE_SP_ATT, ivs);
				}

			} else if (s.hashCode() == mStatSpDefView.getText().hashCode()
					|| s.hashCode() == mEVSpDefView.getText().hashCode()) {
				try {
					statValue = Integer.parseInt(mStatSpDefView.getText()
							.toString());
					evValue = Integer.parseInt(mEVSpDefView.getText()
							.toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_SP_DEF, getNature(),
						statValue, evValue, level, baseSpDef);
				if (ivs != null) {
					showIVs(IVTools.CODE_SP_DEF, ivs);
				}

			} else if (s.hashCode() == mStatSpeedView.getText().hashCode()
					|| s.hashCode() == mEVSpeedView.getText().hashCode()) {
				try {
					statValue = Integer.parseInt(mStatSpeedView.getText()
							.toString());
					evValue = Integer.parseInt(mEVSpeedView.getText()
							.toString());
				} catch (NumberFormatException e) {
					return;
				}
				ivs = IVTools.calculateIVs(IVTools.CODE_SPEED, getNature(),
						statValue, evValue, level, baseSpeed);
				if (ivs != null) {
					showIVs(IVTools.CODE_SPEED, ivs);
				}
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
			spAttIv = Integer.parseInt(mIVSpAttView.getText().toString());
			spDefIv = Integer.parseInt(mIVSpDefView.getText().toString());
		} catch (NumberFormatException e) {
			return;
		}

		mHiddenPowerTypeView.setText(": "
				+ getHiddenPowerType(hpIv, attIv, defIv, spAttIv, spDefIv,
						speedIv));
		mHiddenPowerPowerView.setText(" "
				+ getHiddenPowerPower(hpIv, attIv, defIv, spAttIv, spDefIv,
						speedIv));
	}

	private String getHiddenPowerType(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		int hiddenPowerType = IVTools.getHiddenPowerType(hpIv, attIv, defIv,
				spAttIv, spDefIv, speedIv);

		switch (hiddenPowerType) {
		case 0:
			return getString(R.string.type_fighting);
		case 1:
			return getString(R.string.type_flying);
		case 2:
			return getString(R.string.type_poison);
		case 3:
			return getString(R.string.type_ground);
		case 4:
			return getString(R.string.type_rock);
		case 5:
			return getString(R.string.type_bug);
		case 6:
			return getString(R.string.type_ghost);
		case 7:
			return getString(R.string.type_steel);
		case 8:
			return getString(R.string.type_fire);
		case 9:
			return getString(R.string.type_water);
		case 10:
			return getString(R.string.type_grass);
		case 11:
			return getString(R.string.type_electric);
		case 12:
			return getString(R.string.type_psychic);
		case 13:
			return getString(R.string.type_ice);
		case 14:
			return getString(R.string.type_dragon);
		case 15:
			return getString(R.string.type_dark);
		default:
			return null;
		}
	}

	private int getHiddenPowerPower(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		return IVTools.getHiddenPowerPower(hpIv, attIv, defIv, spAttIv,
				spDefIv, speedIv);

	}

	private void showIVs(int code, ArrayList<Integer> ivs) {
		String result = IVTools.showIVs(code, ivs);
		switch (code) {
		case IVTools.CODE_HP:
			mIVHPView.setText(result);
			break;
		case IVTools.CODE_ATT:
			mIVAttView.setText(result);
			break;
		case IVTools.CODE_DEF:
			mIVDefView.setText(result);
			break;
		case IVTools.CODE_SP_ATT:
			mIVSpAttView.setText(result);
			break;
		case IVTools.CODE_SP_DEF:
			mIVSpDefView.setText(result);
			break;
		case IVTools.CODE_SPEED:
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;

		Uri uri = PokeContract.CONTENT_POKEMON;

		switch (id) {
		case LOADER_AUTO_COMPLETE:
			String[] autoCompleteProjection = { PokeContract.PokemonTable._ID,
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
				mNature = cursor.getString(cursor
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

	private String getNature() {
		if (mNature.equals(getActivity().getString(R.string.nature_lonely))) {
			return IVTools.NATURE_LONELY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_brave))) {
			return IVTools.NATURE_BRAVE;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_adamant))) {
			return IVTools.NATURE_ADAMANT;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_naughty))) {
			return IVTools.NATURE_NAUGHTY;

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_bold))) {
			return IVTools.NATURE_BOLD;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_relaxed))) {
			return IVTools.NATURE_RELAXED;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_impish))) {
			return IVTools.NATURE_IMPISH;
		} else if (mNature.equals(getActivity().getString(R.string.nature_lax))) {
			return IVTools.NATURE_LAX;

		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_timid))) {
			return IVTools.NATURE_TIMID;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_hasty))) {
			return IVTools.NATURE_HASTY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_jolly))) {
			return IVTools.NATURE_JOLLY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_naive))) {
			return IVTools.NATURE_NAIVE;

		} else if (mNature.equals(getActivity().getString(
				R.string.nature_modest))) {
			return IVTools.NATURE_MODEST;
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_mild))) {
			return IVTools.NATURE_MILD;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_quiet))) {
			return IVTools.NATURE_QUIET;
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_rash))) {
			return IVTools.NATURE_RASH;

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_calm))) {
			return IVTools.NATURE_CALM;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_gentle))) {
			return IVTools.NATURE_GENTLE;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_sassy))) {
			return IVTools.NATURE_SASSY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_careful))) {
			return IVTools.NATURE_CAREFUL;
			
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_hardy))) {
			return IVTools.NATURE_HARDY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_bashful))) {
			return IVTools.NATURE_BASHFUL;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_docile))) {
			return IVTools.NATURE_DOCILE;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_quirky))) {
			return IVTools.NATURE_QUIRKY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_serious))) {
			return IVTools.NATURE_SERIOUS;
		} else {
			return null;
		}
	}
}
