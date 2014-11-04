package com.suicune.poketools.model.factories;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.gen6.Gen6Ability;

/**
 * Created by denis on 20.09.14.
 */
public class AbilityFactory {
	public static Ability createAbility(Context context, int gen, int abilityCode) {
		switch (gen) {
			case 6:
				return createGen6Ability(context, abilityCode);
			default:
				return null;
		}
	}

	private static Ability createGen6Ability(Context context, int abilityCode) {
		String[] abilities = context.getResources().getStringArray(R.array.abilities);
		String[] descriptions = context.getResources().getStringArray(R.array.ability_descriptions);
		return new Gen6Ability(abilities[abilityCode], descriptions[abilityCode]);
	}

	public static Ability fromBundle(Bundle bundle, int index) {
		//TODO: DO.
		return null;
	}
}
