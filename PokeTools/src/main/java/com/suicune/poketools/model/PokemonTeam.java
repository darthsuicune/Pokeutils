package com.suicune.poketools.model;

/**
 * Created by denis on 05.01.14.
 */
public interface PokemonTeam {
	public String getName();
	public Pokemon getMember(int position);
	public int getMemberCount();
}
