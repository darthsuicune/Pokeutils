package com.suicune.pokeutils.tools;

import java.util.ArrayList;

public class IVTools {
	public static final int CODE_HP = 0;
	public static final int CODE_ATT = 1;
	public static final int CODE_DEF = 2;
	public static final int CODE_SP_ATT = 3;
	public static final int CODE_SP_DEF = 4;
	public static final int CODE_SPEED = 5;

	public static final int MIN_HP = 1;
	public static final int MAX_HP = 714;
	public static final int MIN_ATT = 41;
	public static final int MAX_ATT = 504;
	public static final int MIN_DEF = 41;
	public static final int MAX_DEF = 614;
	public static final int MIN_SP_ATT = 50;
	public static final int MAX_SP_ATT = 504;
	public static final int MIN_SP_DEF = 68;
	public static final int MAX_SP_DEF = 614;
	public static final int MIN_SPEED = 41;
	public static final int MAX_SPEED = 504;
	
	public static final String NATURE_HARDY = "Hardy";
	public static final String NATURE_BASHFUL = "Bashful";
	public static final String NATURE_DOCILE = "Docile";
	public static final String NATURE_QUIRKY = "Quirky";
	public static final String NATURE_SERIOUS = "Serious";
	
	
	public static final String NATURE_LONELY = "Lonely";
	public static final String NATURE_BRAVE = "Brave";
	public static final String NATURE_ADAMANT = "Adamant";
	public static final String NATURE_NAUGHTY = "Naughty";
	
	public static final String NATURE_BOLD = "Bold";
	public static final String NATURE_RELAXED = "Relaxed";
	public static final String NATURE_IMPISH = "Impish";
	public static final String NATURE_LAX = "Lax";
	
	public static final String NATURE_TIMID = "Timid";
	public static final String NATURE_HASTY = "Hasty";
	public static final String NATURE_JOLLY = "Jolly";
	public static final String NATURE_NAIVE = "Naive";
	
	public static final String NATURE_MODEST = "Modest";
	public static final String NATURE_MILD = "Mild";
	public static final String NATURE_QUIET = "Quiet";
	public static final String NATURE_RASH = "Rash";
	
	public static final String NATURE_CALM = "Calm";
	public static final String NATURE_GENTLE = "Gentle";
	public static final String NATURE_SASSY = "Sassy";
	public static final String NATURE_CAREFUL = "Careful";

	public static ArrayList<Integer> calculateIVs(int code, String nature, int currentStat,
			int currentEv, int currentLevel, int baseStat) {

		if (currentEv < 0 || currentEv > 255) {
			return null;
		}
		ArrayList<Integer> ivList = new ArrayList<Integer>();

		int currentNatureModifier = 100;

		switch (code) {
		case CODE_HP:
			if (currentStat < MIN_HP || currentStat > MAX_HP) {
				return null;
			}

			for (int iv = 0; iv <= 31; iv++) {
				if (currentStat == Math.floor((((iv + (2 * baseStat)
						+ (currentEv / 4) + 100) * currentLevel) / 100) + 10)) {
					ivList.add(iv);
				}
			}

			if (ivList.size() < 1) {
				return null;
			}

			return ivList;
		case CODE_ATT:
			if (currentStat < MIN_ATT || currentStat > MAX_ATT) {
				return null;
			}

			if (nature.equals(NATURE_LONELY) || nature.equals(NATURE_BRAVE) || nature.equals(NATURE_ADAMANT)
					|| nature.equals(NATURE_NAUGHTY)) {
				currentNatureModifier = 110;
			} else if (nature.equals(NATURE_BOLD) || nature.equals(NATURE_TIMID)
					|| nature.equals(NATURE_MODEST) || nature.equals(NATURE_CALM)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_DEF:
			if (currentStat < MIN_DEF || currentStat > MAX_DEF) {
				return null;
			}

			if (nature.equals(NATURE_BOLD) || nature.equals(NATURE_RELAXED) || nature.equals(NATURE_IMPISH)
					|| nature.equals(NATURE_LAX)) {
				currentNatureModifier = 110;
			} else if (nature.equals(NATURE_LONELY) || nature.equals(NATURE_HASTY)
					|| nature.equals(NATURE_MILD) || nature.equals(NATURE_GENTLE)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SPEED:
			if (currentStat < MIN_SPEED || currentStat > MAX_SPEED) {
				return null;
			}

			if (nature.equals(NATURE_TIMID) || nature.equals(NATURE_HASTY) || nature.equals(NATURE_JOLLY)
					|| nature.equals(NATURE_NAIVE)) {
				currentNatureModifier = 110;
			} else if (nature.equals(NATURE_BRAVE) || nature.equals(NATURE_RELAXED)
					|| nature.equals(NATURE_QUIET) || nature.equals(NATURE_SASSY)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SP_ATT:
			if (currentStat < MIN_SP_ATT || currentStat > MAX_SP_ATT) {
				return null;
			}

			if (nature.equals(NATURE_MODEST) || nature.equals(NATURE_MILD) || nature.equals(NATURE_QUIET)
					|| nature.equals(NATURE_RASH)) {
				currentNatureModifier = 110;
			} else if (nature.equals(NATURE_ADAMANT) || nature.equals(NATURE_IMPISH)
					|| nature.equals(NATURE_JOLLY) || nature.equals(NATURE_CAREFUL)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SP_DEF:
			if (currentStat < MIN_SP_DEF || currentStat > MAX_SP_DEF) {
				return null;
			}

			if (nature.equals(NATURE_CALM) || nature.equals(NATURE_GENTLE) || nature.equals(NATURE_SASSY)
					|| nature.equals(NATURE_CAREFUL)) {
				currentNatureModifier = 110;
			} else if (nature.equals(NATURE_NAUGHTY) || nature.equals(NATURE_LAX)
					|| nature.equals(NATURE_NAIVE) || nature.equals(NATURE_RASH)) {
				currentNatureModifier = 90;
			}
			break;
		}

		for (int iv = 0; iv <= 31; iv++) {
			if (currentStat == Math
					.floor(((((iv + (2 * baseStat) + (currentEv / 4)) * currentLevel) / 100) + 5)
							* (currentNatureModifier / 100))) {
				ivList.add(iv);
			}
		}

		return ivList;
	}

	public static String showIVs(int code, ArrayList<Integer> ivs) {
		int minIVValue = 32;
		int maxIVValue = -1;
		for (int i = 0; i < ivs.size(); i++) {
			int iv = ivs.get(i);
			if (iv > maxIVValue) {
				maxIVValue = iv;
			}
			if (iv < minIVValue) {
				minIVValue = iv;
			}
		}
		if(minIVValue == 32 || maxIVValue == -1){
			return null;
		}
		if (minIVValue == maxIVValue) {
			return "" + minIVValue;
		} else {
			return "" + minIVValue + "-" + maxIVValue;
		}
	}

	public static int getHiddenPowerType(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {

		Double hiddenPowerTypeDouble = Math.floor(((1 * (hpIv % 2))
				+ (2 * (attIv % 2)) + (4 * (defIv % 2)) + (8 * (speedIv % 2))
				+ (16 * (spAttIv % 2)) + (32 * (spDefIv % 2))) * 15 / 63);
		return hiddenPowerTypeDouble.intValue();
	}

	public static int getHiddenPowerPower(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		Double hiddenPowerPowerDouble = Math
				.floor((((1 * getValue(hpIv)) + (2 * getValue(attIv))
						+ (4 * getValue(defIv)) + (8 * getValue(speedIv))
						+ (16 * getValue(spAttIv)) + (32 * getValue(spDefIv))) * 40 / 63) + 30);

		return hiddenPowerPowerDouble.intValue();
	}

	private static int getValue(int iv) {
		if (iv % 4 == 2 || iv % 4 == 3) {
			return 1;
		} else {
			return 0;
		}
	}
}
