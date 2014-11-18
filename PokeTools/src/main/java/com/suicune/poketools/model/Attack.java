package com.suicune.poketools.model;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Attack {
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

	public Type type();

	public boolean hasSpecialTreatment();

	public Category category();

	public int gen();

	public int id();

	public int power();

	public int accuracy();

	public int pp();

	public int priority();

	public int critRate();

	public int effectChance();

	public int flinchChance();

	public int healing();

	public int maxTurns();
	public int minTurns();
	public int recoil();
	public Status status();

	public String name();

	public String description();

	public String toString();

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
