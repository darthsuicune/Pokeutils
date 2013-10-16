package com.suicune.pokeutils.tools;

import com.suicune.pokeutils.R;
import com.suicune.pokeutils.app.Natures;

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
	public static final int MIN_ATT = 1;
	public static final int MAX_ATT = 614;
	public static final int MIN_DEF = 1;
	public static final int MAX_DEF = 614;
	public static final int MIN_SP_ATT = 1;
	public static final int MAX_SP_ATT = 614;
	public static final int MIN_SP_DEF = 1;
	public static final int MAX_SP_DEF = 614;
	public static final int MIN_SPEED = 1;
	public static final int MAX_SPEED = 614;
	
	public static final int NATURE_POSITIVE_MODIFIER = 110;
	public static final int NATURE_NEGATIVE_MODIFIER = 90;

	/**
	 * This method calculates all the possible IVs that match the given values.
	 * 
	 * @param code
	 * @param nature
	 * @param currentStat
	 * @param currentEv
	 * @param currentLevel
	 * @param baseStat
	 * @return An ArrayList<Integer> with the calculated IVs
	 */
	public static ArrayList<Integer> calculateIVs(int code, String nature,
			int currentStat, int currentEv, int currentLevel, int baseStat) {

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
				if (currentStat == getHpValue(baseStat, currentEv, iv,
						currentLevel)) {
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

			if (nature.equals(Natures.LONELY) || nature.equals(Natures.BRAVE)
					|| nature.equals(Natures.ADAMANT)
					|| nature.equals(Natures.NAUGHTY)) {
				currentNatureModifier = 110;
			} else if (nature.equals(Natures.BOLD)
					|| nature.equals(Natures.TIMID)
					|| nature.equals(Natures.MODEST)
					|| nature.equals(Natures.CALM)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_DEF:
			if (currentStat < MIN_DEF || currentStat > MAX_DEF) {
				return null;
			}

			if (nature.equals(Natures.BOLD) || nature.equals(Natures.RELAXED)
					|| nature.equals(Natures.IMPISH)
					|| nature.equals(Natures.LAX)) {
				currentNatureModifier = 110;
			} else if (nature.equals(Natures.LONELY)
					|| nature.equals(Natures.HASTY)
					|| nature.equals(Natures.MILD)
					|| nature.equals(Natures.GENTLE)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SPEED:
			if (currentStat < MIN_SPEED || currentStat > MAX_SPEED) {
				return null;
			}

			if (nature.equals(Natures.TIMID) || nature.equals(Natures.HASTY)
					|| nature.equals(Natures.JOLLY)
					|| nature.equals(Natures.NAIVE)) {
				currentNatureModifier = 110;
			} else if (nature.equals(Natures.BRAVE)
					|| nature.equals(Natures.RELAXED)
					|| nature.equals(Natures.QUIET)
					|| nature.equals(Natures.SASSY)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SP_ATT:
			if (currentStat < MIN_SP_ATT || currentStat > MAX_SP_ATT) {
				return null;
			}

			if (nature.equals(Natures.MODEST) || nature.equals(Natures.MILD)
					|| nature.equals(Natures.QUIET)
					|| nature.equals(Natures.RASH)) {
				currentNatureModifier = 110;
			} else if (nature.equals(Natures.ADAMANT)
					|| nature.equals(Natures.IMPISH)
					|| nature.equals(Natures.JOLLY)
					|| nature.equals(Natures.CAREFUL)) {
				currentNatureModifier = 90;
			}
			break;
		case CODE_SP_DEF:
			if (currentStat < MIN_SP_DEF || currentStat > MAX_SP_DEF) {
				return null;
			}

			if (nature.equals(Natures.CALM) || nature.equals(Natures.GENTLE)
					|| nature.equals(Natures.SASSY)
					|| nature.equals(Natures.CAREFUL)) {
				currentNatureModifier = 110;
			} else if (nature.equals(Natures.NAUGHTY)
					|| nature.equals(Natures.LAX)
					|| nature.equals(Natures.NAIVE)
					|| nature.equals(Natures.RASH)) {
				currentNatureModifier = 90;
			}
			break;
		}

		for (int iv = 0; iv <= 31; iv++) {
			int calculated = getStatValue(baseStat, currentEv, iv,
					currentLevel, currentNatureModifier);
			if (currentStat == calculated) {
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
		if (minIVValue == 32 || maxIVValue == -1) {
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
		switch (hiddenPowerTypeDouble.intValue()) {
		case 0:
			return R.string.type_fighting;
		case 1:
			return R.string.type_flying;
		case 2:
			return R.string.type_poison;
		case 3:
			return R.string.type_ground;
		case 4:
			return R.string.type_rock;
		case 5:
			return R.string.type_bug;
		case 6:
			return R.string.type_ghost;
		case 7:
			return R.string.type_steel;
		case 8:
			return R.string.type_fire;
		case 9:
			return R.string.type_water;
		case 10:
			return R.string.type_grass;
		case 11:
			return R.string.type_electric;
		case 12:
			return R.string.type_psychic;
		case 13:
			return R.string.type_ice;
		case 14:
			return R.string.type_dragon;
		case 15:
			return R.string.type_dark;
		default:
			return 0;
		}
	}

	public static int getHiddenPowerPower(int hpIv, int attIv, int defIv,
			int spAttIv, int spDefIv, int speedIv) {
		Double hiddenPowerPowerDouble = Math
				.floor((((1 * getValue(hpIv)) + (2 * getValue(attIv))
						+ (4 * getValue(defIv)) + (8 * getValue(speedIv))
						+ (16 * getValue(spAttIv)) + (32 * getValue(spDefIv)))
                        * 40 / 63) + 30);

		return hiddenPowerPowerDouble.intValue();
	}

	public static int getHpValue(int baseStat, int currentEv, int iv,
			int currentLevel) {
		if(baseStat == 1){
			return 1;
		}
		Double result = Math
				.floor((((iv + (2 * baseStat) + (currentEv / 4) + 100) * currentLevel) / 100)
                        + 10);
		return result.intValue();
	}

	public static int getStatValue(int baseStat, int currentEv, int iv,
			int currentLevel, int currentNatureModifier) {
		if(baseStat == 1){
			return 1;
		}
		Double calculatedStat = (double) (2 * baseStat);
		double ev = currentEv / 4;
		calculatedStat += iv + ev;
		calculatedStat *= currentLevel;
		calculatedStat /= 100;
		calculatedStat += 5;
		calculatedStat *= currentNatureModifier;
		calculatedStat /= 100;
		calculatedStat = Math.floor(calculatedStat);
		return calculatedStat.intValue();
	}

	/**
	 * Convenience method for calculation hidden power power, based on the
	 * stats.
	 * 
	 * @param iv
	 * @return
	 */
	private static int getValue(int iv) {
		if (iv % 4 == 2 || iv % 4 == 3) {
			return 1;
		} else {
			return 0;
		}
	}
}
