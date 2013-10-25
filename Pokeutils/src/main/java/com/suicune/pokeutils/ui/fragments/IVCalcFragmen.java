package com.suicune.pokeutils.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.app.Natures;
import com.suicune.pokeutils.app.Pokemon;
import com.suicune.pokeutils.app.TeamPokemon;

import java.util.HashMap;

/**
 * Created by lapuente on 23.10.13.
 */
public class IVCalcFragmen extends Fragment implements View.OnClickListener {
    private EditText mIVHPView;
    private EditText mIVAttView;
    private EditText mIVDefView;
    private EditText mIVSpAttView;
    private EditText mIVSpDefView;
    private EditText mIVSpeedView;
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
    private TeamPokemon mPokemon;
    private EditText mPokemonLevelEditText;
    private Spinner mNatureSpinner;

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
        setNatureView(v);
        setButtons(v);
        setStatViews(v);
        mPokemonLevelEditText = (EditText) v.findViewById(R.id.iv_calc_pokemon_level);
    }

    private void setButtons(View v) {
        Button calculateIVs = (Button) v.findViewById(R.id.iv_calc_calculate_iv);
        Button calculateHiddenPower = (Button) v.findViewById(R.id.iv_calc_calculate_hidden_power);
        Button calculateStats = (Button) v.findViewById(R.id.iv_calc_calculate_stats);
        calculateIVs.setOnClickListener(this);
        calculateHiddenPower.setOnClickListener(this);
        calculateStats.setOnClickListener(this);
    }

    private void setAutoCompleteView(View v) {
        AutoCompleteTextView pokemonNameEditText = (AutoCompleteTextView)
                v.findViewById(R.id.iv_calc_pokemon_name);
        // Create a Hashmap that for each pokemon name assigns it its id. This will allow
        // us to recover the id later from the autocomplete view, as if we didn't do this,
        // it would get the value of the filtered list.
        final HashMap<String, Integer> pokemonList = new HashMap<String, Integer>();
        String[] pokemonNames = getResources().getStringArray(R.array.pokemon_names);

        for (int i = 0; i < pokemonNames.length; i++) {
            pokemonList.put(pokemonNames[i], i);
        }
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, pokemonNames);
        pokemonNameEditText.setAdapter(autoCompleteAdapter);
        pokemonNameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v,
                                    int position, long id) {
                //Create pokemon here
                mPokemon = new TeamPokemon(getActivity(),
                        pokemonList.get(adapterView.getItemAtPosition(position)));
                updatePokemon();

            }
        });
    }

    private void setNatureView(final View v) {
        mNatureSpinner = (Spinner) v.findViewById(R.id.iv_calc_nature);
        ArrayAdapter<Natures.Nature> adapter = new ArrayAdapter<Natures.Nature>(getActivity(),
                android.R.layout.simple_spinner_item, Natures.Nature.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNatureSpinner.setAdapter(adapter);
        mNatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updatePokemon();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setStatViews(final View v) {
        mIVHPView = (EditText) v.findViewById(R.id.iv_calc_iv_hp);
        mIVAttView = (EditText) v.findViewById(R.id.iv_calc_iv_att);
        mIVDefView = (EditText) v.findViewById(R.id.iv_calc_iv_def);
        mIVSpAttView = (EditText) v.findViewById(R.id.iv_calc_iv_sp_att);
        mIVSpDefView = (EditText) v.findViewById(R.id.iv_calc_iv_sp_def);
        mIVSpeedView = (EditText) v.findViewById(R.id.iv_calc_iv_speed);

        mStatHPView = (EditText) v.findViewById(R.id.iv_calc_stat_hp);
        mStatAttView = (EditText) v.findViewById(R.id.iv_calc_stat_att);
        mStatDefView = (EditText) v.findViewById(R.id.iv_calc_stat_def);
        mStatSpAttView = (EditText) v.findViewById(R.id.iv_calc_stat_sp_att);
        mStatSpDefView = (EditText) v.findViewById(R.id.iv_calc_stat_sp_def);
        mStatSpeedView = (EditText) v.findViewById(R.id.iv_calc_stat_speed);

        mEVHPView = (EditText) v.findViewById(R.id.iv_calc_ev_hp);
        mEVAttView = (EditText) v.findViewById(R.id.iv_calc_ev_att);
        mEVDefView = (EditText) v.findViewById(R.id.iv_calc_ev_def);
        mEVSpAttView = (EditText) v.findViewById(R.id.iv_calc_ev_sp_att);
        mEVSpDefView = (EditText) v.findViewById(R.id.iv_calc_ev_sp_def);
        mEVSpeedView = (EditText) v.findViewById(R.id.iv_calc_ev_speed);
    }

    private void updatePokemon() {
        if (mPokemon != null) {
            showBaseStats();
        }
        if (mNatureSpinner != null) {
            setNature((Natures.Nature) mNatureSpinner.getSelectedItem());
        }
    }

    private void showBaseStats() {
        TextView baseHPView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_hp);
        TextView baseAttView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_att);
        TextView baseDefView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_def);
        TextView baseSpAttView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_sp_att);
        TextView baseSpDefView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_sp_def);
        TextView baseSpeedView = (TextView) getActivity().findViewById(
                R.id.iv_calc_base_speed);

        baseHPView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_HP]);
        baseAttView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_ATT]);
        baseDefView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_DEF]);
        baseSpAttView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_SP_ATT]);
        baseSpDefView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_SP_DEF]);
        baseSpeedView.setText("" + mPokemon.mBaseStats[TeamPokemon.STAT_INDEX_SPEED]);
    }

    private void setNature(Natures.Nature nature) {
        if (mPokemon != null) {
            mPokemon.mNature = nature;
        }

        TextView mIVAttTextView = (TextView) getActivity().findViewById(R.id.iv_calc_iv_att_text);
        TextView mIVDefTextView = (TextView) getActivity().findViewById(R.id.iv_calc_iv_def_text);
        TextView mIVSpAttTextView = (TextView) getActivity()
                .findViewById(R.id.iv_calc_iv_sp_att_text);
        TextView mIVSpDefTextView = (TextView) getActivity()
                .findViewById(R.id.iv_calc_iv_sp_def_text);
        TextView mIVSpeedTextView = (TextView) getActivity()
                .findViewById(R.id.iv_calc_iv_speed_text);

        mIVAttTextView.setBackgroundColor(Color.TRANSPARENT);
        mIVDefTextView.setBackgroundColor(Color.TRANSPARENT);
        mIVSpAttTextView.setBackgroundColor(Color.TRANSPARENT);
        mIVSpDefTextView.setBackgroundColor(Color.TRANSPARENT);
        mIVSpeedTextView.setBackgroundColor(Color.TRANSPARENT);

        if (nature == Natures.Nature.LONELY) {
            mIVAttTextView.setBackgroundColor(Color.GREEN);
            mIVDefTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.BRAVE) {
            mIVAttTextView.setBackgroundColor(Color.GREEN);
            mIVSpeedTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.ADAMANT) {
            mIVAttTextView.setBackgroundColor(Color.GREEN);
            mIVSpAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.NAUGHTY) {
            mIVAttTextView.setBackgroundColor(Color.GREEN);
            mIVSpDefTextView.setBackgroundColor(Color.RED);

        } else if (nature == Natures.Nature.BOLD) {
            mIVDefTextView.setBackgroundColor(Color.GREEN);
            mIVAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.RELAXED) {
            mIVDefTextView.setBackgroundColor(Color.GREEN);
            mIVSpeedTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.IMPISH) {
            mIVDefTextView.setBackgroundColor(Color.GREEN);
            mIVSpAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.LAX) {
            mIVDefTextView.setBackgroundColor(Color.GREEN);
            mIVSpDefTextView.setBackgroundColor(Color.RED);

        } else if (nature == Natures.Nature.TIMID) {
            mIVSpeedTextView.setBackgroundColor(Color.GREEN);
            mIVAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.HASTY) {
            mIVSpeedTextView.setBackgroundColor(Color.GREEN);
            mIVDefTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.JOLLY) {
            mIVSpeedTextView.setBackgroundColor(Color.GREEN);
            mIVSpAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.NAIVE) {
            mIVSpeedTextView.setBackgroundColor(Color.GREEN);
            mIVSpDefTextView.setBackgroundColor(Color.RED);

        } else if (nature == Natures.Nature.MODEST) {
            mIVSpAttTextView.setBackgroundColor(Color.GREEN);
            mIVAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.MILD) {
            mIVSpAttTextView.setBackgroundColor(Color.GREEN);
            mIVDefTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.QUIET) {
            mIVSpAttTextView.setBackgroundColor(Color.GREEN);
            mIVSpeedTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.RASH) {
            mIVSpAttTextView.setBackgroundColor(Color.GREEN);
            mIVSpDefTextView.setBackgroundColor(Color.RED);

        } else if (nature == Natures.Nature.CALM) {
            mIVSpDefTextView.setBackgroundColor(Color.GREEN);
            mIVAttTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.GENTLE) {
            mIVSpDefTextView.setBackgroundColor(Color.GREEN);
            mIVDefTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.SASSY) {
            mIVSpDefTextView.setBackgroundColor(Color.GREEN);
            mIVSpeedTextView.setBackgroundColor(Color.RED);
        } else if (nature == Natures.Nature.CAREFUL) {
            mIVSpDefTextView.setBackgroundColor(Color.GREEN);
            mIVSpAttTextView.setBackgroundColor(Color.RED);
        }
    }

    private void calculateIVs() {
        if(mPokemon != null) {
            return;
        }
        try{
            int hpEv = Integer.parseInt(mEVHPView.getText().toString());
            int hpStat = Integer.parseInt(mStatHPView.getText().toString());
            mPokemon.mEvs[Pokemon.STAT_INDEX_HP] = hpEv;
            mPokemon.mStats[Pokemon.STAT_INDEX_HP] = hpStat;
        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
        try{

        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
        try{

        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
        try{

        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
        try{

        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
        try{

        } catch(NumberFormatException e) {
            // Bad entry, nothing to do.
        }
    }

    private void calculateStats() {

    }

    /**
     * In the works.
     */
    private void calculateHiddenPower() {
        TextView hiddenPowerTypeView = (TextView)
                getActivity().findViewById(R.id.iv_calc_hidden_power_type);
        hiddenPowerTypeView.setText("Not available at the moment");
    }
}