package com.suicune.pokeutils.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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

    private TextView mHiddenPowerTypeView;

    private AutoCompleteTextView mPokemonNameEditText;
    private String mPokemonNameHelper = "";
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
        switch (view.getId()){

        }
    }

    // Private methods

    private void prepareViews(View v){

    }
}