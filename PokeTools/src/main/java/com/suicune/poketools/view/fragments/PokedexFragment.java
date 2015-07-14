package com.suicune.poketools.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suicune.poketools.R;

public class PokedexFragment extends Fragment {
	RecyclerView list;

	public static PokedexFragment newInstance() {
		return new PokedexFragment();
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_pokedex, null, false);
		prepareViews(v);
		return v;
	}

	private void prepareViews(View v) {
		list = (RecyclerView) v.findViewById(R.id.pokemon_list);
	}
}
