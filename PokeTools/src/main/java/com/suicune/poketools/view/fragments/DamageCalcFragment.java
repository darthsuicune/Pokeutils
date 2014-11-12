package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.factories.PokemonFactory;
import com.suicune.poketools.view.PokemonCardHolder;
import com.suicune.poketools.view.PokemonCardView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link DamageCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DamageCalcFragment extends Fragment implements PokemonCardHolder {

	private static final String ARG_ATTACKER = "attacker";
	private static final String ARG_DEFENDER = "defender";

	private PokemonCardView mAttackerView;
	private PokemonCardView mDefenderView;
	private Pokemon mAttacker;
	private Pokemon mDefender;

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
		if (savedInstanceState != null && savedInstanceState.containsKey(ARG_ATTACKER)) {
			mAttacker = PokemonFactory.createFromBundle(savedInstanceState.getBundle(ARG_ATTACKER));
		}
		if (savedInstanceState != null && savedInstanceState.containsKey(ARG_DEFENDER)) {
			mDefender = PokemonFactory.createFromBundle(savedInstanceState.getBundle(ARG_DEFENDER));
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(mAttacker != null) {
			outState.putBundle(ARG_ATTACKER, mAttacker.save());
		}
		if(mDefender != null) {
			outState.putBundle(ARG_DEFENDER, mDefender.save());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_damage_calc, container, false);
		mAttackerView = (PokemonCardView) fragmentView.findViewById(R.id.damage_calc_attacker);
		mDefenderView = (PokemonCardView) fragmentView.findViewById(R.id.damage_calc_defender);
		return fragmentView;
	}

	@Override public void onResume() {
		super.onResume();
		mAttackerView.setup(this, mAttacker);
		mDefenderView.setup(this, mDefender);
	}

	//TODO: Implement
	@Override public void updatePokemon(Pokemon pokemon) {
		if (pokemon == mAttackerView.mPokemon) {
			mAttacker = pokemon;
		} else {
			mDefender = pokemon;
		}
		if (mAttackerView.isReady() && mDefenderView.isReady()) {
			calculateDamages();
		}
	}

	//TODO: Implement
	private void calculateDamages() {

	}
}
