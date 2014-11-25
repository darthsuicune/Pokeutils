package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;
import com.suicune.poketools.view.PokemonCardHolder;
import com.suicune.poketools.view.PokemonCardView;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link DamageCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DamageCalcFragment extends Fragment implements PokemonCardHolder {

	private static final String ARG_ATTACKER = "attacker";
	private static final String ARG_DEFENDER = "defender";

	private PokemonCardView attackerView;
	private PokemonCardView defenderView;
	private Pokemon attacker;
	private Pokemon defender;

	private Map<Integer, TextView> attackerResults = new HashMap<>();
	private Map<Integer, TextView> defenderResults = new HashMap<>();

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment DamageCalcFragment.
	 */
	public static DamageCalcFragment newInstance() {
		return new DamageCalcFragment();
	}

	public DamageCalcFragment() {
		// Required empty public constructor
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			if (savedInstanceState != null && savedInstanceState.containsKey(ARG_ATTACKER)) {
				attacker = PokemonFactory.createFromBundle(getActivity(),
						savedInstanceState.getBundle(ARG_ATTACKER));
			}
			if (savedInstanceState != null && savedInstanceState.containsKey(ARG_DEFENDER)) {
				defender = PokemonFactory.createFromBundle(getActivity(),
						savedInstanceState.getBundle(ARG_DEFENDER));
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		attackerView.saveState(outState, ARG_ATTACKER);
		defenderView.saveState(outState, ARG_DEFENDER);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_damage_calc, container, false);
		attackerView = (PokemonCardView) fragmentView.findViewById(R.id.damage_calc_attacker);
		defenderView = (PokemonCardView) fragmentView.findViewById(R.id.damage_calc_defender);
		loadResultViews(attackerResults,
				fragmentView.findViewById(R.id.damace_calc_attacker_results));
		loadResultViews(defenderResults,
				fragmentView.findViewById(R.id.damace_calc_defender_results));
		return fragmentView;
	}

	private void loadResultViews(Map<Integer, TextView> views, View view) {
		views.put(1, (TextView) view.findViewById(R.id.attack_1_result));
		views.put(2, (TextView) view.findViewById(R.id.attack_2_result));
		views.put(3, (TextView) view.findViewById(R.id.attack_3_result));
		views.put(4, (TextView) view.findViewById(R.id.attack_4_result));
	}

	@Override public void onResume() {
		super.onResume();
		attackerView.setup(this, attacker);
		defenderView.setup(this, defender);
	}

	@Override public void updatePokemon(Pokemon pokemon) {
		if (pokemon == attackerView.pokemon) {
			attacker = pokemon;
		} else {
			defender = pokemon;
		}
		if (attackerView.hasAValidPokemon() && defenderView.hasAValidPokemon()) {
			calculateDamages();
		}
	}

	private void calculateDamages() {

	}
}
