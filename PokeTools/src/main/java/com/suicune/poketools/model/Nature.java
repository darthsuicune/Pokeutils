package com.suicune.poketools.model;

import com.suicune.poketools.model.gen6.Gen6Nature;

/**
 * Created by denis on 20.09.14.
 */
public interface Nature {
	public int nameResId();
	public Stats.Stat increasedStat();
	public Stats.Stat decreasedStat();
	public double attackModifier();
	public double defenseModifier();
	public double specialAttackModifier();
	public double specialDefenseModifier();
	public double speedModifier();
}
