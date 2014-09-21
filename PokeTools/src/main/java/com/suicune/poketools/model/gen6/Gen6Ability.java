package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Ability;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Ability implements Ability {
	public String mName;
	public String mDescription;
	public Gen6Ability(String name, String description) {
		mName = name;
		mDescription = description;
	}
	@Override public String name() {
		return mName;
	}

	@Override public String description() {
		return mDescription;
	}
}
