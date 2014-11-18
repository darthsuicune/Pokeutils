package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Type;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Attack implements Attack {
	public final int id;
	public final Type type;
	public final Category category;
	public final int power;
	public final int accuracy;
	public final int pp;
	public final int priority;
	public final String name;
	public final String description;
	public final int mCritRate;
	public final int mEffectChance;
	public final int mFlinchChance;
	public final int mHealing;
	public final int mMaxTurns;
	public final int mMinTurns;
	public final int mRecoil;
	public final Status mStatus;

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
		this.mCritRate = (data.isNull(ARG_CRIT_RATE)) ? 0 : data.getInt(ARG_CRIT_RATE) ;
		this.mEffectChance = (data.isNull(ARG_EFFECT_CHANCE)) ? 0 : data.getInt(ARG_EFFECT_CHANCE) ;
		this.mFlinchChance = (data.isNull(ARG_FLINCH_CHANCE)) ? 0 : data.getInt(ARG_FLINCH_CHANCE) ;
		this.mHealing = (data.isNull(ARG_HEALING)) ? 0 : data.getInt(ARG_HEALING) ;
		this.mMaxTurns = (data.isNull(ARG_MAX_TURNS)) ? 0 : data.getInt(ARG_MAX_TURNS) ;
		this.mMinTurns = (data.isNull(ARG_MIN_TURNS)) ? 0 : data.getInt(ARG_MIN_TURNS) ;
		this.mRecoil = (data.isNull(ARG_RECOIL)) ? 0 : data.getInt(ARG_RECOIL) ;
		this.mStatus = Status.fromCode((data.isNull(ARG_STATUS)) ? 0 : data.getInt(ARG_STATUS)) ;
	}

	@Override public Type type() {
		return type;
	}

	@Override public boolean hasSpecialTreatment() {
		//TODO: Requires massive switch with special treatment checks
		if (power == 1) {
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

	@Override public int id() {
		return id;
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

	@Override public int critRate() {
		return mCritRate;
	}

	@Override public int effectChance() {
		return mEffectChance;
	}

	@Override public int flinchChance() {
		return mFlinchChance;
	}

	@Override public int healing() {
		return mHealing;
	}

	@Override public int maxTurns() {
		return mMaxTurns;
	}

	@Override public int minTurns() {
		return mMinTurns;
	}

	@Override public int recoil() {
		return mRecoil;
	}

	@Override public Status status() {
		return mStatus;
	}

	@Override public String name() {
		return name;
	}

	@Override public String description() {
		return description;
	}

	@Override public String toString() {
		return name + " (" + type.toString() + " " + power + ")";
	}

	@Override public boolean equals(Object o) {
		return ((Attack) o).id() == this.id;
	}
}
