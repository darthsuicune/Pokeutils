package com.suicune.poketools.model;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by lapuente on 17.09.14.
 */
public abstract class Pokemon {
	public static final String ARG_GEN = "gen";
	public static final String ARG_DEX_NUMBER = "number";
	public static final String ARG_FORM = "form";
	public static final String ARG_FORM_COUNT = "form_count";
	public static final String ARG_NICKNAME = "name";
	public static final String ARG_NAME = "nickname";
	public static final String ARG_LEVEL = "level";
	public static final String ARG_ABILITIES = "abilities";
	public static final String ARG_ABILITY_1 = "ability_1";
	public static final String ARG_ABILITY_2 = "ability_2";
	public static final String ARG_ABILITY_HIDDEN = "ability_hidden";
	public static final String ARG_TYPES = "types";
	public static final String ARG_TYPE_1 = "type_1";
	public static final String ARG_TYPE_2 = "type_2";
	public static final String ARG_BASE_STATS = "baseStats";
	public static final String ARG_STATS = "stats";

	public static final int DEFAULT_LEVEL = 100;

	public abstract int gen();
	public abstract String name();
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
	public abstract Pokemon setAbility(Ability ability);
	public abstract Pokemon setNature(Nature nature);
	public abstract Pokemon setNickname(String nickname);
	public abstract Pokemon setHappiness(int happiness);
	public abstract Pokemon setLevel(int level);
	public abstract Pokemon addAdditionalType(Type type);
	public abstract Pokemon setCurrentAttacks(List<Attack> attacks);
	public abstract Pokemon setCurrentAbility(Ability ability);

	public abstract Pokemon setIvs(int hp, int attack, int defense, int spattack, int spdefense,
								   int speed);

	public abstract Pokemon setEvs(int hp, int attack, int defense, int spattack, int spdefense,
								   int speed);

	public abstract Pokemon setStats(int hp, int attack, int defense, int spattack, int spdefense,
									 int speed);

	public abstract Pokemon addAttack(Attack attack, int position);

	public abstract Pokemon calculateIvs();
	public abstract Pokemon calculateStats();

	public abstract Bundle save();
	public abstract Pokemon load(Bundle bundle);

	@Override public String toString() {
		return "#" + dexNumber() + " - " + name();
	}

	public static String getName(Context context, int number, int form) {
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
}
