package com.suicune.poketools.model;

/**
 * Created by denis on 20.09.14.
 */
public interface Nature {
	public int nameResId();
	public Stats.Stat increasedStat();
	public Stats.Stat decreasedStat();
	public double statModifier(Stats.Stat stat);
	public int save();
}
