package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Type;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Attack extends Attack {
	public final int id;
	public final Type type;
	public final Category category;
	public final int power;
	public final int accuracy;
	public final int pp;
	public final int priority;
	public final String name;
	public final String description;
	public final int critRate;
	public final int effectChance;
	public final int flinchChance;
	public final int healing;
	public final int maxTurns;
	public final int minTurns;
	public final int recoil;
	public final Status status;

	public Gen6Attack(Type type, JSONObject data, String name, String description)
			throws JSONException {
		this.id = (data.isNull(ARG_CODE)) ? 0 : data.getInt(ARG_CODE) ;
		this.type = type;
		this.category = Category.fromClass((data.isNull(ARG_CLASS)) ? 0 : data.getInt(ARG_CLASS)) ;
		this.power = (data.isNull(ARG_POWER)) ? 0 : data.getInt(ARG_POWER) ;
		this.accuracy = (data.isNull(ARG_ACCURACY)) ? 0 : data.getInt(ARG_ACCURACY) ;
		this.pp = (data.isNull(ARG_PP)) ? 0 : data.getInt(ARG_PP) ;
		this.priority = (data.isNull(ARG_PRIORITY)) ? 0 : data.getInt(ARG_PRIORITY) ;
		this.name = name;
		this.description = description;
		this.critRate = (data.isNull(ARG_CRIT_RATE)) ? 0 : data.getInt(ARG_CRIT_RATE) ;
		this.effectChance = (data.isNull(ARG_EFFECT_CHANCE)) ? 0 : data.getInt(ARG_EFFECT_CHANCE) ;
		this.flinchChance = (data.isNull(ARG_FLINCH_CHANCE)) ? 0 : data.getInt(ARG_FLINCH_CHANCE) ;
		this.healing = (data.isNull(ARG_HEALING)) ? 0 : data.getInt(ARG_HEALING) ;
		this.maxTurns = (data.isNull(ARG_MAX_TURNS)) ? 0 : data.getInt(ARG_MAX_TURNS) ;
		this.minTurns = (data.isNull(ARG_MIN_TURNS)) ? 0 : data.getInt(ARG_MIN_TURNS) ;
		this.recoil = (data.isNull(ARG_RECOIL)) ? 0 : data.getInt(ARG_RECOIL) ;
		this.status = Status.fromCode((data.isNull(ARG_STATUS)) ? 0 : data.getInt(ARG_STATUS)) ;
	}

	@Override public Type type() {
		return type;
	}

	@Override public boolean hasSpecialTreatment() {
		//TODO: Requires massive switch with special treatment checks
		if (power == 1) {
			return true;
		}
		switch(id) {

			default:
				return false;
		}
	}

	@Override public Category category() {
		return category;
	}

	@Override public int gen() {
		return 6;
	}

	@Override public int id() {
		return id;
	}

	@Override public int power() {
		if(power == 1) {

		}
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

	@Override public int critRate() {
		return critRate;
	}

	@Override public int effectChance() {
		return effectChance;
	}

	@Override public int flinchChance() {
		return flinchChance;
	}

	@Override public int healing() {
		return healing;
	}

	@Override public int maxTurns() {
		return maxTurns;
	}

	@Override public int minTurns() {
		return minTurns;
	}

	@Override public int recoil() {
		return recoil;
	}

	@Override public Status status() {
		return status;
	}

	@Override public String name() {
		return name;
	}

	@Override public String description() {
		return description;
	}

	public String toString() {
		return name + " (" + type.toString() + " " + power + ")";
	}

	@Override public boolean equals(Object o) {
		return ((Attack) o).id() == this.id;
	}

	@Override public int color() {
		return type.color();
	}
}
