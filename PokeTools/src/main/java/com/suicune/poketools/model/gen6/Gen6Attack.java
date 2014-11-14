package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Type;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Attack implements Attack {
	public final Type type;
	public final int attackClass;
	public final Category category;
	public final int power;
	public final int accuracy;
	public final int pp;
	public final int priority;
	public final String name;
	public final String description;

	public Gen6Attack(Type type, int attackClass, int power, int accuracy, int pp, int priority,
				  String name, String description) {
		this.type = type;
		this.attackClass = attackClass;
		this.power = power;
		this.accuracy = accuracy;
		this.pp = pp;
		this.priority = priority;
		this.name = name;
		this.description = description;
		this.category = Category.fromClass(attackClass);
	}

	@Override public Type type() {
		return type;
	}

	@Override public boolean hasSpecialTreatment() {
		//TODO: Requires massive switch with special treatment checks
		if(power == 1) {
			return true;
		}
		return false;
	}

	@Override public Category category() {
		return category;
	}

	@Override public int gen() {
		return 6;
	}

	@Override public int attackClass() {
		return attackClass;
	}

	@Override public int power() {
		return power;
	}

	@Override public int accuracy() {
		return accuracy;
	}

	@Override public int pp() {
		return pp;
	}

	@Override public int priority() {
		return priority;
	}

	@Override public String name() {
		return name;
	}

	@Override public String description() {
		return description;
	}
}
