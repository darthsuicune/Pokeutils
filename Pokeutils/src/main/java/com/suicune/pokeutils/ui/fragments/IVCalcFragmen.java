package com.suicune.pokeutils.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.app.TeamPokemon;

/**
 * Created by lapuente on 23.10.13.
 */
public class IVCalcFragmen extends Fragment implements View.OnClickListener {

    private static final int LOADER_AUTO_COMPLETE = 1;
    private static final int LOADER_POKEMON = 2;

    private TeamPokemon mPokemon;

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

    private Button mCalculateIVs;
    private Button mCalculateHiddenPower;
    private Button mCalculateStats;

    private TextView mHiddenPowerTypeView;

    private AutoCompleteTextView mPokemonNameEditText;
    private EditText mPokemonLevelEditText;
    private Spinner mNatureSpinner;

    private ArrayAdapter<String> mAutoCompleteAdapter;

    // Mandatory overrides

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View v = inflater.inflate(R.layout.iv_calc, container, false);
        prepareViews(v);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_calc_calculate_iv:
                calculateIVs();
                break;
            case R.id.iv_calc_calculate_stats:
                calculateStats();
                break;
            case R.id.iv_calc_calculate_hidden_power:
                calculateHiddenPower();
                break;
        }
    }

    // Private methods

    private void prepareViews(View v) {
        setAutoCompleteView(v);
    }

    private void setAutoCompleteView(View v) {
        mPokemonNameEditText = (AutoCompleteTextView) v.findViewById(R.id.iv_calc_pokemon_name);
        mAutoCompleteAdapter = new ArrayAdapter<String>(getActivity(), R.id.iv_calc_pokemon_name,
                getResources().getStringArray(R.array.pokemon_names));
        mPokemonNameEditText.setAdapter(mAutoCompleteAdapter);
        mPokemonNameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v,
                                    int position, long id) {
                //Create pokemon here
                mPokemon = new TeamPokemon(getActivity(), position);
            }
        });
    }

    private void calculateIVs(){

    }

    private void calculateStats() {

    }

    private void calculateHiddenPower() {

    }
}