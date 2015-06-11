package com.suicune.poketools.model.gen6;

import android.os.Bundle;

import com.suicune.poketools.model.Ability;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Ability extends Ability {
	public String name;
	public String description;
	public String battleDescription;
	public int mCode;
	public Gen6Ability(String name, String description, int code, String battleDescription) {
		this.name = name;
		this.description = description;
		mCode = code;
		this.battleDescription = battleDescription;
	}

	@Override public int id() {
		return mCode;
	}

	@Override public String name() {
		return name;
	}

	@Override public String description() {
		return description;
	}

	@Override public String battleDescription() {
		return battleDescription;
	}

	@Override public Bundle save() {
		Bundle bundle = new Bundle();
		bundle.putString(ARG_NAME, name);
		bundle.putString(ARG_DESCRIPTION, description);
		bundle.putString(ARG_BATTLE_DESCRIPTION, battleDescription);
		bundle.putInt(ARG_CODE, mCode);
		return bundle;
	}
}
