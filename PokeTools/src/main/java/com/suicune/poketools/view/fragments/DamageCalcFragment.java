package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.view.PokemonCardHolder;
import com.suicune.poketools.view.PokemonCardView;

import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link DamageCalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DamageCalcFragment extends Fragment implements PokemonCardHolder {

	private PokemonCardView mAttacker;
	private PokemonCardView mDefender;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.fragment_damage_calc, container, false);
		if(mAttacker == null) {
			mAttacker = new PokemonCardView(getActivity(), this,
					(CardView) fragmentView.findViewById(R.id.damage_calc_attacker));
		}
		if (mDefender == null) {
			mDefender = new PokemonCardView(getActivity(), this,
					(CardView) fragmentView.findViewById(R.id.damage_calc_defender));
		}
		return fragmentView;
	}

	//TODO: Implement
	@Override public void updatePokemon(Pokemon pokemon) {
		if(pokemon == mAttacker.mPokemon) {
			List<Attack> attackerAttacks = mAttacker.getAttacks();
		} else {
			List<Attack> defenderAttacks = mDefender.getAttacks();
		}
		if(mAttacker.mPokemon != null && mDefender.mPokemon != null) {
			calculateDamages();
		}
	}

	//TODO: Implement
	private void calculateDamages() {

	}
}
