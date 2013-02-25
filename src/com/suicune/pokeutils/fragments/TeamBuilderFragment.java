package com.suicune.pokeutils.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.pokeutils.Pokemon;
import com.suicune.pokeutils.R;

public class TeamBuilderFragment extends Fragment {
	private View mPokemon1View;
	private View mPokemon2View;
	private View mPokemon3View;
	private View mPokemon4View;
	private View mPokemon5View;
	private View mPokemon6View;
	
	private Pokemon pokemon1;
	private Pokemon pokemon2;
	private Pokemon pokemon3;
	private Pokemon pokemon4;
	private Pokemon pokemon5;
	private Pokemon pokemon6;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.team_builder, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
}
