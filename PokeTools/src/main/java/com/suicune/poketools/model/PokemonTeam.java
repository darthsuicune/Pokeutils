package com.suicune.poketools.model;

import android.content.Context;

/**
 * Created by denis on 05.01.14.
 */
public interface PokemonTeam {
	public String getName();
	public PokemonTeam setName(String name);
	public Pokemon getMember(int position);
	public int getMemberCount();
	public PokemonTeam addMember(int position, Pokemon pokemon);
	public String[] getNames(Context context);
}
