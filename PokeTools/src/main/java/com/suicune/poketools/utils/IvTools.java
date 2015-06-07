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
		double result = (iv + 2*base + ev/4 + 100)*level/100 + 10;
		return (int) result;
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
		double result = ((iv + 2 * base + ev / 4) * level / 100 + 5) * modifier;
		return (int) result;
	}

	public static List<Integer> calculatePossibleHpIvValues(int gen, int level, int value, int base,
															int ev) {
		switch (gen) {
			case 1:
			case 2:
				return new ArrayList<>();
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculatePossibleHpIvValuesGen6(level, base, value, ev);
		}
	}

	private static List<Integer> calculatePossibleHpIvValuesGen6(int level, int value, int base,
																 int ev) {
		List<Integer> possibleValues = new ArrayList<>();
		for (int i = 0; i <= 31; i++) {
			if (value == calculateHpGen6(level, base, i, ev)) {
				possibleValues.add(i);
			}
		}
		return possibleValues;
	}

	public static List<Integer> calculatePossibleIvValues(int gen, int level, int value, int base,
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
				return calculatePossibleIvValuesGen6(level, base, value, ev, modifier);
		}
	}

	private static List<Integer> calculatePossibleIvValuesGen6(int level, int value, int base,
															   int ev, double modifier) {
		List<Integer> possibleValues = new ArrayList<>();
		for (int i = 0; i <= 31; i++) {
			if (value == calculateStatGen6(level, base, i, ev, modifier)) {
				possibleValues.add(i);
			}
		}
		return possibleValues;
	}

	public static String asText(List<Integer> stats, String empty) {
		String result;
		switch (stats.size()) {
			case 0:
				result = empty;
				break;
			case 1:
				result = "" + stats.get(0);
				break;
			default:
				result = "" + stats.get(0) + " - " +
						stats.get(stats.size() - 1);
		}
		return result;
	}
}
