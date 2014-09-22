package com.suicune.poketools.model;

import java.util.List;
import java.util.Map;

/**
 * Created by lapuente on 17.09.14.
 */
public abstract class Pokemon {
	public abstract int dexNumber();
	public abstract int formNumber();
	public abstract double femaleRatio();
	public abstract double maleRatio();
	public abstract Stats stats();
	public abstract List<Type> types();
	public abstract Ability ability1();
	public abstract Ability ability2();
	public abstract Ability abilityHidden();
	public abstract boolean isHiddenAbilityAvailable();
	public abstract Map<Integer, Attack> levelAttacks();
	public abstract Map<String, Attack> tmAttacks();
	public abstract List<Attack> eggAttacks();
	public abstract Map<String, Attack> tutorAttacks();
	public abstract Map<String, Attack> transferAttacks();
	
	public abstract int level();
	public abstract List<Attack> currentAttacks();
	public abstract Ability currentAbility();

	@Override public String toString() {
		return "#" + dexNumber() + " - " + getName(dexNumber(), formNumber());
	}

	public String getName(int number, int form) {
		//TODO: Replace with actual name
		return "name";
	}

}
