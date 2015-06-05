package com.suicune.poketools.model;

public interface Nature {
	int nameResId();
	Stats.Stat increasedStat();
	Stats.Stat decreasedStat();
	double statModifier(Stats.Stat stat);
	int save();
}
