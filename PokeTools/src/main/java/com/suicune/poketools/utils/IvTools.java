package com.suicune.poketools.utils;

import java.util.ArrayList;
import java.util.List;

public class IvTools {

	/**
	 * Stats calculator by bulbapedia
	 * http://bulbapedia.bulbagarden.net/wiki/Stats#Determination_of_stats
	 */

	public static int calculateHp(int gen, int level, int base, int iv, int ev) {
		switch (gen) {
			// TODO: Gen 1 and 2 use a different formula
			case 1:
			case 2:
				return 1;
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculateHpGen6(level, base, iv, ev);
		}

	}

	/**
	 * Formula for HP, gen3 and above
	 * HP = [IV + (2*Base) + EV/4 + 100] * Level/100 + 10
	 *
	 * @param level
	 * @param base
	 * @param iv
	 * @param ev
	 * @return
	 */
	private static int calculateHpGen6(int level, int base, int iv, int ev) {
		//Special Shedinja case
		if (base == 1) {
			return 1;
		}
		int base2 = base * 2;
		double ev4 = ev / 4;
		double level100 = (double) level / 100.0;
		double intermediate = iv + base2 + ev4 + 100;
		double intermediate2 = level100 * intermediate;
		int result = (int) intermediate2;
		return result + 10;
	}

	public static int calculateStat(int gen, int level, int base, int iv, int ev, double modifier) {
		switch (gen) {
			case 1:
			case 2:
				return 1;
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculateStatGen6(level, base, iv, ev, modifier);
		}
	}

	/**
	 * Formula for Stats, gen 3 and above
	 * Stat = {[IV + (2*Base) + EV/4] * Level/100 + 5} * modifier
	 *
	 * @param level
	 * @param base
	 * @param iv
	 * @param ev
	 * @param modifier
	 * @return
	 */
	private static int calculateStatGen6(int level, int base, int iv, int ev, double modifier) {
		int base2 = base * 2;
		double ev4 = ev / 4;
		double level100 = (double) level / 100.0;
		double intermediate = iv + base2 + ev4;
		double intermediate2 = level100 * intermediate;
		double intermediate3 = intermediate2 + 5;
		double result = intermediate3 * modifier;
		return (int) result;
	}

	public static List<Integer> calculatePossibleStatValues(int gen, int level, int base, int iv,
															int ev, double modifier) {
		switch (gen) {
			case 1:
			case 2:
				return new ArrayList<>();
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculatePossibleStatValuesGen6(level, base, iv, ev, modifier);
		}
	}

	private static List<Integer> calculatePossibleStatValuesGen6(int level, int base, int iv,
																 int ev, double modifier) {
		List<Integer> possibleValues = new ArrayList<>();
		for (int i = 0; i <= 31; i++) {
			int value = calculateStatGen6(level, base, i, ev, modifier);
			if (value == calculateStatGen6(level, base, iv, ev, modifier)) {
				possibleValues.add(i);
			}
		}
		return possibleValues;
	}
}
