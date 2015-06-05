package com.suicune.poketools.model;

import android.os.Bundle;

public interface Ability {
	String ARG_NAME = "name";
	String ARG_CODE = "code";
	String ARG_DESCRIPTION = "description";
	String ARG_BATTLE_DESCRIPTION = "battle_description";
	int id();
	String name();
	String description();
	String battleDescription();
	Bundle save();
}
