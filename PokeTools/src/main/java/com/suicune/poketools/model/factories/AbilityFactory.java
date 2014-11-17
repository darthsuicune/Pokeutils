package com.suicune.poketools.model.factories;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.gen6.Gen6Ability;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by denis on 20.09.14.
 */
public class AbilityFactory {
	public static Ability createAbility(Context context, int gen, int abilityCode) {
		switch (gen) {
			case 6:
			default:
				return createGen6Ability(context, abilityCode);
		}
	}

	private static Ability createGen6Ability(Context context, int abilityCode) {
		String[] abilities = context.getResources().getStringArray(R.array.abilities);
		String[] descriptions = context.getResources().getStringArray(R.array.ability_descriptions);
		String[] battleDescriptions =
				context.getResources().getStringArray(R.array.ability_battle_descriptions);
		return new Gen6Ability(abilities[abilityCode], descriptions[abilityCode], abilityCode,
				battleDescriptions[abilityCode]);
	}

	public static Ability fromBundle(int gen, Bundle bundle) {
		switch (gen) {
			case 6:
			default:
				return new Gen6Ability(bundle.getString(Ability.ARG_NAME),
						bundle.getString(Ability.ARG_DESCRIPTION), bundle.getInt(Ability.ARG_CODE),
						bundle.getString(Ability.ARG_BATTLE_DESCRIPTION));
		}
	}

	public static Ability[] fromJson(Context context, int gen, JSONArray abilityArray)
			throws JSONException {
		switch (gen) {
			case 6:
			default:
				return createGen6List(context, abilityArray);
		}
	}

	public static Ability[] createGen6List(Context context, JSONArray abilityArray)
			throws JSONException {
		Ability[] abilities = new Ability[3];
		for (int i = 0; i < 3; i++) {
			abilities[i] = AbilityFactory.createAbility(context, 6, abilityArray.getInt(i));
		}
		return abilities;
	}

	public static int find(Context context, Ability ability) {
		String[] abilities = context.getResources().getStringArray(R.array.abilities);
		for (int i = 0; i < abilities.length; i++) {
			if (abilities[i].equals(ability.name())) {
				return i;
			}
		}
		return 0;
	}
}
