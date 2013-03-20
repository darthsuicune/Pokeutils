package com.suicune.pokeutils.tools;

import java.util.Arrays;
import java.util.Random;

import com.suicune.pokeutils.Types;
import com.suicune.pokeutils.Types.*;

public class DamageCalcTools {
	public static final double TYPE_MODIFIER_WEAK = 2;
	public static final double TYPE_MODIFIER_2X_WEAK = 4;
	public static final double TYPE_MODIFIER_RESISTANT = 0.5;
	public static final double TYPE_MODIFIER_2X_RESISTANT = 0.25;
	public static final double TYPE_MODIFIER_INMUNE = 0;
	public static final double NO_TYPE_MODIFIER = 1;

	public static final double TEMP_MODIFIER_0_LEVEL = 1;
	public static final double TEMP_MODIFIER_1_LEVEL = 1.50;
	public static final double TEMP_MODIFIER_2_LEVEL = 2.0;
	public static final double TEMP_MODIFIER_3_LEVEL = 2.50;
	public static final double TEMP_MODIFIER_4_LEVEL = 3.0;
	public static final double TEMP_MODIFIER_5_LEVEL = 3.50;
	public static final double TEMP_MODIFIER_6_LEVEL = 4.0;

	private static final int MIN_RANDOM = 85;

	/**
	 * 
	 * This method returns a formated String as percentages with the min - max
	 * damage of an attack.
	 * 
	 * Returns null if there is no damages.
	 * 
	 * @param pokemonAttack
	 *            The current stat value of the pokemon attack / special attack
	 * @param attackerLevel
	 *            The level of the attacking pokemon
	 * @param attackBasePower
	 *            The base power of the attack being used
	 * @param pokemonDefense
	 *            The current stat value of the pokemon defense / special
	 *            defense
	 * @param pokemonHP
	 * @param typeModifier
	 * @param attackLevelModifier
	 * @param defenseLevelModifier
	 * @param hasStab
	 * @return
	 */
	public static String calculateDamagePorcent(int pokemonAttackStat,
			int attackerLevel, int attackBasePower, int pokemonDefenseStat,
			int pokemonHP, double typeModifier, double attackLevelModifier,
			double defenseLevelModifier, boolean hasStab) {
		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return null;
		}

		long minDamage = getMinDamage(pokemonAttackStat, attackerLevel,
				attackBasePower, pokemonDefenseStat, typeModifier,
				attackLevelModifier, defenseLevelModifier, hasStab);
		long maxDamage = getMaxDamage(pokemonAttackStat, attackerLevel,
				attackBasePower, pokemonDefenseStat, typeModifier,
				attackLevelModifier, defenseLevelModifier, hasStab);

		double minPorcent = 100 * minDamage / pokemonHP;
		double maxPorcent = 100 * maxDamage / pokemonHP;

		return minPorcent + "% - " + maxPorcent + "%";
	}

	/**
	 * This method returns a formated String as total amount with the min - max
	 * damage of an attack.
	 * 
	 * Returns null if there is no damages.
	 * 
	 * @param pokemonAttack
	 * @param attackerLevel
	 * @param attackBasePower
	 * @param pokemonDefense
	 * @param typeModifier
	 * @param attackLevelModifier
	 * @param defenseLevelModifier
	 * @param hasStab
	 * @return
	 */
	public static String calculateDamageTotal(int pokemonAttackStat,
			int attackerLevel, int attackBasePower, int pokemonDefenseStat,
			double typeModifier, double attackLevelModifier,
			double defenseLevelModifier, boolean hasStab) {
		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return null;
		}

		return getMinDamage(pokemonAttackStat, attackerLevel, attackBasePower,
				pokemonDefenseStat, typeModifier, attackLevelModifier,
				defenseLevelModifier, hasStab)
				+ " - "
				+ getMaxDamage(pokemonAttackStat, attackerLevel,
						attackBasePower, pokemonDefenseStat, typeModifier,
						attackLevelModifier, defenseLevelModifier, hasStab);
	}

	/**
	 * This method returns the minimum damage an attack will do.
	 * 
	 * @param pokemonAttack
	 * @param attackerLevel
	 * @param attackBasePower
	 * @param pokemonDefense
	 * @param typeModifier
	 * @param attackLevelModifier
	 * @param defenseLevelModifier
	 * @param hasStab
	 * @return
	 */
	public static long getMinDamage(int pokemonAttackStat, int attackerLevel,
			int attackBasePower, int pokemonDefenseStat, double typeModifier,
			double attackLevelModifier, double defenseLevelModifier,
			boolean hasStab) {
		return Math.round(getMaxDamage(pokemonAttackStat, attackerLevel,
				attackBasePower, pokemonDefenseStat, typeModifier,
				attackLevelModifier, defenseLevelModifier, hasStab)
				* MIN_RANDOM / 100);
	}

	/**
	 * This method will return a random amount of damage that the attack will do
	 * 
	 * @param pokemonAttack
	 * @param attackerLevel
	 * @param attackBasePower
	 * @param pokemonDefense
	 * @param typeModifier
	 * @param attackLevelModifier
	 * @param defenseLevelModifier
	 * @param hasStab
	 * @return
	 */
	public static long getRandomDamage(int pokemonAttackStat,
			int attackerLevel, int attackBasePower, int pokemonDefenseStat,
			double typeModifier, double attackLevelModifier,
			double defenseLevelModifier, boolean hasStab) {

		int random = Math.round(new Random().nextInt(16) + MIN_RANDOM / 100);

		return getMaxDamage(pokemonAttackStat, attackerLevel, attackBasePower,
				pokemonDefenseStat, typeModifier, attackLevelModifier,
				defenseLevelModifier, hasStab)
				* random;
	}

	/**
	 * This method returns the maximum damage an attack will do
	 * 
	 * @param pokemonAttack
	 * @param attackerLevel
	 * @param attackBasePower
	 * @param pokemonDefense
	 * @param typeModifier
	 * @param attackLevelModifier
	 * @param defenseLevelModifier
	 * @param hasStab
	 * @return
	 */

	public static long getMaxDamage(int pokemonAttackStat, int attackerLevel,
			int attackBasePower, int pokemonDefenseStat, double typeModifier,
			double attackLevelModifier, double defenseLevelModifier,
			boolean hasStab) {
		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return 0;
		}
		// http://www.smogon.com/dp/articles/damage_formula
		// The formula requires a Math.floor after each operation.
		double result;
		result = 2 * attackerLevel / 5;
		result += 2;
		result = result * attackBasePower * pokemonAttackStat
				/ pokemonDefenseStat;
		result /= 50;
		// Mod1
		result += 2;
		// Critical hit * Mod2
		if (hasStab) {
			result *= 1.50;
		}
		result *= typeModifier;
		// Mod3
		// If the result is < 1, it will do at least 1 damage.
		if (result < 1) {
			return 1;
		}

		return Math.round(Math.floor(result));
	}

	/**
	 * 
	 * @param attackingType
	 * @param defendingType1
	 * @param defendingType2
	 * @return
	 */
	public static double getTypeModifier(int attackingType, int defendingType1,
			int defendingType2) {
		double modifier1 = getSingleModifier(attackingType, defendingType1);
		double modifier2 = getSingleModifier(attackingType, defendingType2);
		return modifier1 * modifier2;
	}

	private static double getSingleModifier(int attackingType, int defendingType) {
		switch (defendingType) {
		case Types.NORMAL:
			if (Arrays.binarySearch(Normal.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Normal.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Normal.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.FIGHTING:
			if (Arrays.binarySearch(Fighting.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Fighting.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Fighting.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.FLYING:
			if (Arrays.binarySearch(Flying.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Flying.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Flying.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.POISON:
			if (Arrays.binarySearch(Poison.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Poison.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			}
			break;
		case Types.GROUND:
			if (Arrays.binarySearch(Ground.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Ground.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Ground.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.ROCK:
			if (Arrays.binarySearch(Rock.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Rock.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Rock.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.BUG:
			if (Arrays.binarySearch(Bug.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Bug.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Bug.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.GHOST:
			if (Arrays.binarySearch(Ghost.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Ghost.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Ghost.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.STEEL:
			if (Arrays.binarySearch(Steel.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Steel.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Steel.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.FIRE:
			if (Arrays.binarySearch(Fire.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Fire.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Fire.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.WATER:
			if (Arrays.binarySearch(Water.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Water.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Water.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.GRASS:
			if (Arrays.binarySearch(Grass.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Grass.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Grass.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.ELECTRIC:
			if (Arrays.binarySearch(Electric.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Electric.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Electric.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.PSYCHIC:
			if (Arrays.binarySearch(Psychic.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Psychic.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Psychic.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.ICE:
			if (Arrays.binarySearch(Ice.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Ice.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Ice.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.DRAGON:
			if (Arrays.binarySearch(Dragon.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Dragon.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Dragon.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		case Types.DARK:
			if (Arrays.binarySearch(Dark.weaknesses, attackingType) > -1) {
				return TYPE_MODIFIER_WEAK;
			} else if (Arrays.binarySearch(Dark.resistances, attackingType) > -1) {
				return TYPE_MODIFIER_RESISTANT;
			} else if (Arrays.binarySearch(Dark.inmunities, attackingType) > -1) {
				return TYPE_MODIFIER_INMUNE;
			}
			break;
		}
		return NO_TYPE_MODIFIER;
	}

	public static double getStatModifier(int position) {
		switch (position) {
		case 0:
			return (1 / TEMP_MODIFIER_6_LEVEL);
		case 1:
			return (1 / TEMP_MODIFIER_5_LEVEL);
		case 2:
			return (1 / TEMP_MODIFIER_4_LEVEL);
		case 3:
			return (1 / TEMP_MODIFIER_3_LEVEL);
		case 4:
			return (1 / TEMP_MODIFIER_2_LEVEL);
		case 5:
			return (1 / TEMP_MODIFIER_1_LEVEL);
		case 6:
			return TEMP_MODIFIER_0_LEVEL;
		case 7:
			return TEMP_MODIFIER_1_LEVEL;
		case 8:
			return TEMP_MODIFIER_2_LEVEL;
		case 9:
			return TEMP_MODIFIER_3_LEVEL;
		case 10:
			return TEMP_MODIFIER_4_LEVEL;
		case 11:
			return TEMP_MODIFIER_5_LEVEL;
		case 12:
			return TEMP_MODIFIER_6_LEVEL;
		default:
			return 0;
		}
	}
}