package com.suicune.poketools.model;

import java.util.List;
import java.util.Map;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Pokemon {
	public int raceId();
	public int dexNumber();
	public int formNumber();
	public double femaleRatio();
	public double maleRatio();
	public Stats stats();
	public List<Type> types();
	public Ability ability1();
	public Ability ability2();
	public Ability abilityHidden();
	public boolean isHiddenAbilityAvailable();
	public Map<Integer, Attack> levelAttacks();
	public Map<String, Attack> tmAttacks();
	public List<Attack> eggAttacks();
	public Map<String, Attack> tutorAttacks();
	public Map<String, Attack> transferAttacks();

	public int level();
	public List<Attack> currentAttacks();
	public Ability currentAbility();

}
