package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;
import com.suicune.poketools.view.PokemonCardHolder;
import com.suicune.poketools.view.PokemonCardView;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.suicune.poketools.model.Stats.Stat;


public class IvCalcFragment extends Fragment implements PokemonCardHolder {
	private static final String ARG_POKEMON = "pokemon";
	private PokemonCardView cardView;
	private Pokemon pokemon;

	private Map<Stat, TextView> resultViews = new HashMap<>();

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment IvCalcFragment.
	 */
	public static IvCalcFragment newInstance() {
		return new IvCalcFragment();
	}

	public IvCalcFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_iv_calc, container, false);
		cardView = (PokemonCardView) fragmentView.findViewById(R.id.pokemon);
		setupCalculator(fragmentView);
		return fragmentView;
	}

	private void setupCalculator(View fragmentView) {
		Button button = (Button) fragmentView.findViewById(R.id.calculate_ivs);
		button.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {
				displayIvs();
			}
		});
		View results = fragmentView.findViewById(R.id.results);
		resultViews.put(Stat.HP, (TextView) results.findViewById(R.id.hp));
		resultViews.put(Stat.ATTACK, (TextView) results.findViewById(R.id.attack));
		resultViews.put(Stat.DEFENSE, (TextView) results.findViewById(R.id.defense));
		resultViews.put(Stat.SPECIAL_ATTACK, (TextView) results.findViewById(R.id.special_attack));
		resultViews
				.put(Stat.SPECIAL_DEFENSE, (TextView) results.findViewById(R.id.special_defense));
		resultViews.put(Stat.SPEED, (TextView) results.findViewById(R.id.speed));
	}

	private void displayIvs() {
		if (pokemon != null) {
			Map<Stat, List<Integer>> results = pokemon.calculateIvs();
			for (Stat stat : Stat.values(6)) {
				List<Integer> statResults = results.get(stat);
				String result;
				switch (statResults.size()) {
					case 0:
						result = getString(R.string.empty);
						break;
					case 1:
						result = "" + statResults.get(0);
						break;
					default:
						result = "" + results.get(stat).get(0) + " - " +
								 results.get(stat).get(results.get(stat).size() - 1);
				}
				resultViews.get(stat).setText(result);
			}
		}
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			if (savedInstanceState != null && savedInstanceState.containsKey(ARG_POKEMON)) {
				pokemon = PokemonFactory
						.createFromBundle(getActivity(), savedInstanceState.getBundle(ARG_POKEMON));
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		cardView.saveState(outState, ARG_POKEMON);
	}

	@Override public void onResume() {
		super.onResume();
		cardView.setup(this, pokemon);
	}

	@Override public void updatePokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
		displayIvs();
	}
}
