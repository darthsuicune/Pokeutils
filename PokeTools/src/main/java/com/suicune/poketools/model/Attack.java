package com.suicune.poketools.model;

import com.suicune.poketools.view.Typeable;

/**
 * Created by lapuente on 17.09.14.
 */
public abstract class Attack implements Typeable {
	public static final String ARG_TYPE = "attack_type";
	public static final String ARG_PRIORITY = "priority";
	public static final String ARG_PP = "pp";
	public static final String ARG_POWER = "power";
	public static final String ARG_CLASS = "damage_class";
	public static final String ARG_ACCURACY = "accuracy";
	public static final String ARG_CODE = "code";
	public static final String ARG_CAUSED_EFFECT = "caused_effect";
	public static final String ARG_CRIT_RATE = "crit_rate";
	public static final String ARG_EFFECT_CHANCE = "effect_chance";
	public static final String ARG_FLINCH_CHANCE = "flinch_chance";
	public static final String ARG_HEALING = "healing";
	public static final String ARG_MAX_TURNS = "max_turns";
	public static final String ARG_MIN_TURNS = "min_turns";
	public static final String ARG_RECOIL = "recoil";
	public static final String ARG_STATUS = "status";

	public abstract Type type();

	public abstract boolean hasSpecialTreatment();

	public abstract Category category();

	public abstract int gen();

	public abstract int id();

	public abstract int power();

	public abstract int accuracy();

	public abstract int pp();

	public abstract int priority();

	public abstract int critRate();

	public abstract int effectChance();

	public abstract int flinchChance();

	public abstract int healing();

	public abstract int maxTurns();
	public abstract int minTurns();
	public abstract int recoil();
	public abstract Status status();

	public abstract String name();

	public abstract String description();

	public abstract String toString();

	public enum Category {
		OTHER, PHYSICAL, SPECIAL;

		public static Category fromClass(int attackClass) {
			switch (attackClass) {
				case 1:
					return PHYSICAL;
				case 2:
					return SPECIAL;
				default:
					return OTHER;
			}
		}
	}

	public enum Status {
		NONE;
		public static Status fromCode(int code) {
			switch(code) {
				default:
				return NONE;
			}
		}
	}
}
