package com.suicune.poketools.utils;

import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;

/**
 * Created by denis on 01.01.14.
 */
public class IvTools {
    public static Stats getIvs(int gen, Pokemon pokemon){
        return null;
    }
    public static Stats calculateStats(Pokemon pokemon){
		Stats stats = pokemon.stats();
		stats.setValuesFromStats(pokemon.level());
		return stats;
    }

	/**
	 * Stats calculator by bulbapedia
	 * http://bulbapedia.bulbagarden.net/wiki/Stats#Determination_of_stats
	 *
	 */


	public static int calculateHp(int gen, int level, int base, int iv, int ev) {
		switch(gen) {
			case 1:
			case 2:
				return 0;
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculateHpGen6(level, base, iv, ev);
		}

	}

	/**
	 * Formula for HP
	 * HP = [IV + (2*Base) + EV/4 + 100] * Level/100 + 10
	 * @param level
	 * @param base
	 * @param iv
	 * @param ev
	 * @return
	 */
	private static int calculateHpGen6(int level, int base, int iv, int ev) {
		//Special Shedinja case
		if(base == 1) {
			return 1;
		}
		int base2 = base * 2;
		double ev4 = ev / 4;
		double level100 = level / 100;
		double intermediate = iv + base2 + ev4 + 100;
		double intermediate2 = level100 * intermediate;
		int result = (int) intermediate2;
		return result + 10;
	}

	public static int calculateStat(int gen, int level, int base, int iv, int ev, double modifier) {
		switch(gen) {
			case 1:
			case 2:
				return 0;
			case 3:
			case 4:
			case 5:
			case 6:
			default:
				return calculateStatGen6(level, base, iv, ev, modifier);
		}
	}

	/**
	 * Formula for Stats
	 * Stat = {[IV + (2*Base) + EV/4] * Level/100 + 5} * modifier
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
		double level100 = level / 100;
		double intermediate = iv + base2 + ev4;
		double intermediate2 = level100 * intermediate;
		double intermediate3 = intermediate2 + 5;
		double result = intermediate3 * modifier;
		return (int) result;
	}
}
