package com.suicune.poketools.model;

import android.os.Bundle;

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
	public abstract String nickname();
	public abstract int happiness();
	public abstract Nature nature();
	public abstract Type additionalType();
	public abstract Pokemon setNature(Nature nature);
	public abstract Pokemon setNickname(String nickname);
	public abstract Pokemon setHappiness(int happiness);
	public abstract Pokemon setLevel(int level);
	public abstract Pokemon addAdditionalType(Type type);
	public abstract Pokemon setCurrentAttacks(List<Attack> attacks);
	public abstract Pokemon setCurrentAbility(Ability ability);

	public abstract Bundle save();
	public abstract Pokemon load(Bundle bundle);

	@Override public String toString() {
		return "#" + dexNumber() + " - " + getName(dexNumber(), formNumber());
	}

	public String getName(int number, int form) {
		//TODO: Replace with actual name
		return "name";
	}

}
