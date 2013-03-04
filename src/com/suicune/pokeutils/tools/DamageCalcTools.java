package com.suicune.pokeutils.tools;

import java.util.Arrays;
import java.util.Random;

import com.suicune.pokeutils.tools.Types.*;

public class DamageCalcTools {
	public static final double TYPE_MODIFIER_WEAK = 2;
	public static final double TYPE_MODIFIER_2X_WEAK = 4;
	public static final double TYPE_MODIFIER_RESISTANT = 0.5;
	public static final double TYPE_MODIFIER_2X_RESISTANT = 0.25;
	public static final double TYPE_MODIFIER_INMUNE = 0;
	public static final double NO_TYPE_MODIFIER = 1;

	public static final double TEMP_MODIFIER_1_LEVEL = 1.50;
	public static final double TEMP_MODIFIER_2_LEVEL = 2.0;
	public static final double TEMP_MODIFIER_3_LEVEL = 2.50;
	public static final double TEMP_MODIFIER_4_LEVEL = 3.0;
	public static final double TEMP_MODIFIER_5_LEVEL = 3.50;
	public static final double TEMP_MODIFIER_6_LEVEL = 4.0;
	
	private static final int MIN_RANDOM = 85;
	private static final int MAX_RANDOM = 100;

	/**
	 * This method returns a formated String with the min - max damage of an
	 * attack Returns null if there is no damage.
	 * 
	 * @param pokemonAttack
	 * @param attackBasePower
	 * @param pokemonDefense
	 * @param pokemonHP
	 * @param typeModifier
	 * @param attackModifier
	 * @param defenseModifier
	 * @return
	 */
	public static String calculateDamagePorcent(int pokemonAttack,
			int attackerLevel, int attackBasePower, int pokemonDefense,
			int defenderLevel, int pokemonHP, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB) {
		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return null;
		}

		long minDamage = getMinDamage(pokemonAttack, attackerLevel,
				attackBasePower, pokemonDefense, typeModifier, attackModifier,
				defenseModifier, hasSTAB);
		long maxDamage = getMaxDamage(pokemonAttack, attackerLevel,
				attackBasePower, pokemonDefense, typeModifier, attackModifier,
				defenseModifier, hasSTAB);

		double minPorcent = 100 * minDamage / pokemonHP;
		double maxPorcent = 100 * maxDamage / pokemonHP;

		return minPorcent + "% - " + maxPorcent + "%";
	}

	public static String calculateDamageTotal(int pokemonAttack,
			int attackerLevel, int attackBasePower, int pokemonDefense,
			int defenderLevel, int pokemonHP, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB) {
		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return null;
		}

		return getMinDamage(pokemonAttack, attackerLevel, attackBasePower,
				pokemonDefense, typeModifier, attackModifier, defenseModifier,
				hasSTAB)
				+ " - "
				+ getMaxDamage(pokemonAttack, attackerLevel, attackBasePower,
						pokemonDefense, typeModifier, attackModifier,
						defenseModifier, hasSTAB);
	}

	public static long getMinDamage(int pokemonAttack, int attackerLevel,
			int attackBasePower, int pokemonDefense, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB) {
		return Math.round(getDamage(pokemonAttack, attackerLevel,
				attackBasePower, pokemonDefense, typeModifier, attackModifier,
				defenseModifier, hasSTAB) * MIN_RANDOM);
	}

	public static long getMaxDamage(int pokemonAttack, int attackerLevel,
			int attackBasePower, int pokemonDefense, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB) {
		return Math.round(getDamage(pokemonAttack, attackerLevel,
				attackBasePower, pokemonDefense, typeModifier, attackModifier,
				defenseModifier, hasSTAB) * MAX_RANDOM);
	}
	
	public static double getRandomDamage(int pokemonAttack, int attackerLevel,
			int attackBasePower, int pokemonDefense, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB){
		int random = new Random().nextInt(16) + MIN_RANDOM;
		
		return (getDamage(pokemonAttack, attackerLevel,
				attackBasePower, pokemonDefense, typeModifier, attackModifier,
				defenseModifier, hasSTAB) * random);
	}
	
	public static double getDamage(int pokemonAttack, int attackerLevel,
			int attackBasePower, int pokemonDefense, double typeModifier,
			double attackModifier, double defenseModifier, boolean hasSTAB){
		double result;
		result = 2 * attackerLevel / 5;
		result += 2;
		result = result * attackBasePower * pokemonAttack / pokemonDefense;
		result /= 50;
		result += 2;
		if(hasSTAB){
			result *= 1.50;
		}
		result *= typeModifier;
		result /= 100;
		return result;
	}

	public static double getModifier(int attackingType, int defendingType1,
			int defendingType2) {
		double modifier1 = getSingleModifier(attackingType, defendingType1);
		double modifier2 = getSingleModifier(attackingType, defendingType2);
		return modifier1 * modifier2;
	}

	public static double getSingleModifier(int attackingType, int defendingType) {
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
}