package com.suicune.poketools.model.gen6;

import android.os.Bundle;

import com.suicune.poketools.model.Ability;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Ability implements Ability {
	public String mName;
	public String mDescription;
	public String mBattleDescription;
	public int mCode;
	public Gen6Ability(String name, String description, int code, String battleDescription) {
		mName = name;
		mDescription = description;
		mCode = code;
		mBattleDescription = battleDescription;
	}

	@Override public int id() {
		return mCode;
	}

	@Override public String name() {
		return mName;
	}

	@Override public String description() {
		return mDescription;
	}

	@Override public String battleDescription() {
		return mBattleDescription;
	}

	@Override public Bundle save() {
		Bundle bundle = new Bundle();
		bundle.putString(ARG_NAME, mName);
		bundle.putString(ARG_DESCRIPTION, mDescription);
		bundle.putString(ARG_BATTLE_DESCRIPTION, mBattleDescription);
		bundle.putInt(ARG_CODE, mCode);
		return bundle;
	}
}
