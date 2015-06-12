package com.suicune.poketools.model;

import android.os.Bundle;

public abstract class Ability {
	public static String ARG_NAME = "name";
	public static String ARG_CODE = "code";
	public static String ARG_DESCRIPTION = "description";
	public static String ARG_BATTLE_DESCRIPTION = "battle_description";

	public abstract int id();

	public abstract String name();

	public abstract String description();

	public abstract String battleDescription();

	public abstract Bundle save();

	@Override public boolean equals(Object o) {
		if(o instanceof Ability) {
			Ability test = (Ability) o;
			return test.id() == this.id() && test.name().equals(this.name());
		}
		return false;
	}

	@Override public String toString() {
		return name();
	}
}
