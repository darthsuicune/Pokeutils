package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Battlefield;

public class Gen6Battlefield implements Battlefield {
	@Override public boolean isCritical(Attack attack) {
		return false;
	}
}
