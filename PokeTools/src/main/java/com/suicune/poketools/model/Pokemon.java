package com.suicune.poketools.model;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.R;
import com.suicune.poketools.view.Typeable;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.suicune.poketools.model.Stats.Stat;

public abstract class Pokemon implements Typeable {
	public static final String ARG_GEN = "gen";
	public static final String ARG_DEX_NUMBER = "dex_number";
	public static final String ARG_FORM = "form";
	public static final String ARG_EGG_GROUP_1 = "egg_group_1";
	public static final String ARG_EGG_GROUP_2 = "egg_group_2";
	public static final String ARG_HEIGHT = "height";
	public static final String ARG_WEIGHT = "weight";
	public static final String ARG_MIN_LEVEL = "min_level";
	public static final String ARG_NICKNAME = "name";
	public static final String ARG_NAME = "nickname";
	public static final String ARG_LEVEL = "level";
	public static final String ARG_ABILITIES = "abilities";
	public static final String ARG_TYPES = "types";
	public static final String ARG_BASE_STATS = "baseStats";
	public static final String ARG_STATS = "stats";
	public static final String ARG_ATTACKS = "attacks";
	public static final String ARG_ABILITY = "ability";
	public static final String ARG_CURRENT_ATTACKS = "currentAttacks";
	public static final String ARG_HAPPINESS = "happiness";
	public static final String ARG_ADDITIONAL_TYPE = "additionalType";
	public static final String ARG_NATURE = "nature";

	public static final int MIN_LEVEL = 1;
	public static final int MAX_LEVEL = 100;
	public static final int DEFAULT_LEVEL = MAX_LEVEL;

	/**
	 * Inmutable parameters
	 */
	public abstract int gen();
	public abstract String name();

	public abstract int dexNumber();
	public abstract int formNumber();
	public abstract Stats stats();
	public abstract Map<Stat, Integer> baseStats();
	public abstract Map<Stat, Integer> evs();
	public abstract Map<Stat, Integer> ivs();
	public abstract Map<Stat, Integer> currentStats();
	public abstract Type type1();
	public abstract Type type2();
	public abstract Ability ability1();
	public abstract Ability ability2();
	public abstract Ability abilityHidden();
	public abstract List<Attack> attackList();
	public abstract EggGroup eggGroup1();
	public abstract EggGroup eggGroup2();
	public abstract double height();
	public abstract double weight();
	public abstract int minLevel();

	/**
	 * Pending to implement for dex
	 */
	public abstract double femaleRatio();
	public abstract double maleRatio();
	public abstract boolean isHiddenAbilityAvailable();
	public abstract Map<Integer, Attack> levelAttacks();
	public abstract Map<String, Attack> tmAttacks();
	public abstract List<Attack> eggAttacks();
	public abstract Map<String, Attack> tutorAttacks();
	public abstract Map<String, Attack> transferAttacks();

	/**
	 * Mutable properties
	 */
	public abstract int level();
	public abstract Map<Integer, Attack> currentAttacks();
	public abstract Ability currentAbility();
	public abstract String nickname();
	public abstract int happiness();
	public abstract Nature nature();
	public abstract Type additionalType();

	/**
	 * Setters
	 */
	public abstract Pokemon setAbility(Ability ability);
	public abstract Pokemon setNature(Nature nature);
	public abstract Pokemon setNickname(String nickname);
	public abstract Pokemon setHappiness(int happiness);
	public abstract Pokemon setLevel(int level);
	public abstract Pokemon addAdditionalType(Type type);
	public abstract Pokemon setCurrentAttacks(Map<Integer,Attack> attacks);
	public abstract Pokemon setCurrentAbility(Ability ability);

	public abstract Pokemon setIvs(int hp, int attack, int defense, int spattack, int spdefense,
								   int speed);

	public abstract Pokemon setEvs(int hp, int attack, int defense, int spattack, int spdefense,
								   int speed);

	public abstract Pokemon setStats(int hp, int attack, int defense, int spattack, int spdefense,
									 int speed);

	public abstract Pokemon addAttack(Attack attack, int position);
	public abstract Pokemon addAttack(Attack attack);

	public abstract Pokemon setIv(Stat stat, int value);
	public abstract Pokemon setEv(Stat stat, int value);
	public abstract Pokemon setValue(Stat stat, int value);
	public abstract Pokemon setStatModifier(Stat stat, int modifier);
	public abstract int statModifier(Stat stat);
	public abstract int finalValue(Stat stat);
	/**
	 * Utility methods
	 */
	public abstract Map<Stat, List<Integer>> calculateIvs();
	public abstract Stats calculateStats();
	public abstract List<Integer> attack(int index, Pokemon defender, Battlefield field);


	public abstract Item item();

	public abstract boolean isBurned();
	public abstract Bundle save();
	public abstract Pokemon load(Context context, Bundle bundle) throws IOException, JSONException;

	@Override public String toString() {
		return "#" + dexNumber() + " - " + name();
	}

	public static String getDefaultName(Context context, int number, int form) {
		String[] names = context.getResources().getStringArray(R.array.pokemon_names);

		int i = 0;
		String name = names[number];

		StringTokenizer tokenizer = new StringTokenizer(names[number], ";");
		while(tokenizer.hasMoreTokens()) {
			if (i == form) {
				name = tokenizer.nextToken();
				break;
			} else {
				tokenizer.nextToken();
				i++;
			}
		}
		return name;
	}

	public static int getForm(String names, String name) {
		int form = 0;
		StringTokenizer tokenizer = new StringTokenizer(names, ";");
		while(tokenizer.hasMoreTokens()) {
			if (name.equals(tokenizer.nextToken())) {
				break;
			} else {
				form++;
			}
		}
		return form;
	}

	public static List<String> parseAllNames(String[] names) {
		List<String> parsedNames = new ArrayList<>();
		for (String name : names) {
			StringTokenizer tokenizer = new StringTokenizer(name, ";");
			while (tokenizer.hasMoreTokens()) {
				parsedNames.add(tokenizer.nextToken());
			}
		}
		return parsedNames;
	}

	public abstract void updateStat(Stats.StatType type, Stat stat, int newValue);
}
