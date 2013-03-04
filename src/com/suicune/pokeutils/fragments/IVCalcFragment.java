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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.suicune.pokeutils.Natures;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;
import com.suicune.pokeutils.tools.IVTools;

public class IVCalcFragment extends Fragment implements TextWatcher,
		LoaderCallbacks<Cursor> {

	private static final int LOADER_AUTO_COMPLETE = 1;
	private static final int LOADER_NATURE = 2;
	private static final int LOADER_BASE_STATS = 3;
	protected static final String ROW_ID = "rowId";
	

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
		String[] from = { PokeContract.PokemonName.NAME };
		int[] to = { android.R.id.text1 };
		mAutoCompleteAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, null, from, to,
				0);
		mAutoCompleteAdapter
				.setCursorToStringConverter(new CursorToStringConverter() {

					@Override
					public CharSequence convertToString(Cursor c) {
						return c.getString(c
								.getColumnIndexOrThrow(PokeContract.PokemonName.NAME));
					}
				});
		
		mPokemonNameEditText.setAdapter(mAutoCompleteAdapter);
		mPokemonNameEditText.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position,
					long id) {
				Bundle args = new Bundle();
				args.putLong(ROW_ID, id);
				getActivity().getSupportLoaderManager().restartLoader(LOADER_BASE_STATS, args, IVCalcFragment.this);
			}
		});
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

		getActivity().getSupportLoaderManager().restartLoader(
				LOADER_AUTO_COMPLETE, null, this);
	}

	private void setNatureSpinnerAdapter() {
		getActivity().getSupportLoaderManager().restartLoader(LOADER_NATURE,
				null, this);
		String[] from = { PokeContract.Natures.NAME };
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
	
	private void setBaseStats(int hp, int att, int def, int spatt, int spdef, int speed){
		TextView baseHPView = (TextView) getActivity().findViewById(R.id.iv_calc_base_hp);
		TextView baseAttView = (TextView) getActivity().findViewById(R.id.iv_calc_base_att);
		TextView baseDefView = (TextView) getActivity().findViewById(R.id.iv_calc_base_def);
		TextView baseSpAttView = (TextView) getActivity().findViewById(R.id.iv_calc_base_sp_att);
		TextView baseSpDefView = (TextView) getActivity().findViewById(R.id.iv_calc_base_sp_def);
		TextView baseSpeedView = (TextView) getActivity().findViewById(R.id.iv_calc_base_speed);
		
		baseHP = hp;
		baseAtt = att;
		baseDef = def;
		baseSpAtt = spatt;
		baseSpDef = spdef;
		baseSpeed = speed;
		
		baseHPView.setText("" + hp);
		baseAttView.setText("" + att);
		baseDefView.setText("" + def);
		baseSpAttView.setText("" + spatt);
		baseSpDefView.setText("" + spdef);
		baseSpeedView.setText("" + speed);
		
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

		Uri uri;
		
		switch (id) {
		case LOADER_AUTO_COMPLETE:
			uri = PokeContract.PokemonName.CONTENT_POKEMON_NAME;
			String[] autoCompleteProjection = { PokeContract.PokemonName._ID,
					PokeContract.PokemonName.NAME,};
			String autoCompleteSelection = PokeContract.PokemonName.NAME
					+ " LIKE ?";
			String[] autoCompleteSelectionArgs = { "%" + mPokemonName + "%" };
			loader = new CursorLoader(getActivity(), uri,
					autoCompleteProjection, autoCompleteSelection, autoCompleteSelectionArgs, null);
			break;
		case LOADER_BASE_STATS:
			uri = PokeContract.PokemonBaseStats.CONTENT_POKEMON_BASE_STATS;
			String baseStatsSelection = PokeContract.PokemonBaseStats._ID + "=?";
			String[] selectionArgs = {
				Long.toString(args.getLong(ROW_ID))	
			};
			loader = new CursorLoader(getActivity(), uri, null, baseStatsSelection, selectionArgs, null);
			break;
		case LOADER_NATURE:
			uri = PokeContract.Natures.CONTENT_NATURE;

			String[] natureProjection = { PokeContract.Natures._ID,
					PokeContract.Natures.NAME };

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
		case LOADER_BASE_STATS:
			if(cursor.moveToFirst()){
				setBaseStats(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_HP))),
				Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_ATT))),
				Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_DEF))),
				Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPATT))),
				Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPDEF))),
				Integer.parseInt(cursor.getString(cursor.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPEED))));
			}
			break;
		case LOADER_NATURE:
			mNatureAdapter.swapCursor(cursor);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				mNature = cursor.getString(cursor
						.getColumnIndex(PokeContract.Natures.NAME));
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
			return Natures.LONELY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_brave))) {
			return Natures.BRAVE;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_adamant))) {
			return Natures.ADAMANT;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_naughty))) {
			return Natures.NAUGHTY;

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_bold))) {
			return Natures.BOLD;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_relaxed))) {
			return Natures.RELAXED;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_impish))) {
			return Natures.IMPISH;
		} else if (mNature.equals(getActivity().getString(R.string.nature_lax))) {
			return Natures.LAX;

		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_timid))) {
			return Natures.TIMID;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_hasty))) {
			return Natures.HASTY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_jolly))) {
			return Natures.JOLLY;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_naive))) {
			return Natures.NAIVE;

		} else if (mNature.equals(getActivity().getString(
				R.string.nature_modest))) {
			return Natures.MODEST;
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_mild))) {
			return Natures.MILD;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_quiet))) {
			return Natures.QUIET;
		} else if (mNature
				.equals(getActivity().getString(R.string.nature_rash))) {
			return Natures.RASH;

		} else if (mNature
				.equals(getActivity().getString(R.string.nature_calm))) {
			return Natures.CALM;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_gentle))) {
			return Natures.GENTLE;
		} else if (mNature.equals(getActivity()
				.getString(R.string.nature_sassy))) {
			return Natures.SASSY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_careful))) {
			return Natures.CAREFUL;
			
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_hardy))) {
			return Natures.HARDY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_bashful))) {
			return Natures.BASHFUL;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_docile))) {
			return Natures.DOCILE;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_quirky))) {
			return Natures.QUIRKY;
		} else if (mNature.equals(getActivity().getString(
				R.string.nature_serious))) {
			return Natures.SERIOUS;
		} else {
			return null;
		}
	}
}
