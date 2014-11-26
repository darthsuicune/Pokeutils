package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Battlefield;

/**
 * Created by lapuente on 26.11.14.
 */
public class Gen6Battlefield implements Battlefield {
	@Override public boolean isCritical(Attack attack) {
		return false;
	}
}
