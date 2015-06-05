package com.suicune.poketools.model;

import java.util.List;

public interface Type {
	int nameResId();
	List<Type> weaknesses();
	List<Type> resistances();
	List<Type> immunities();
	int save();
	int color();
	double modifierAgainst(Type type);
}
