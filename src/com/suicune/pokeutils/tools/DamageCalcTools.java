package com.suicune.pokeutils.tools;

import java.util.Arrays;
import java.util.Random;

import com.suicune.pokeutils.Attack;
import com.suicune.pokeutils.TeamPokemon;
import com.suicune.pokeutils.Types;
import com.suicune.pokeutils.Types.Bug;
import com.suicune.pokeutils.Types.Dark;
import com.suicune.pokeutils.Types.Dragon;
import com.suicune.pokeutils.Types.Electric;
import com.suicune.pokeutils.Types.Fighting;
import com.suicune.pokeutils.Types.Fire;
import com.suicune.pokeutils.Types.Flying;
import com.suicune.pokeutils.Types.Ghost;
import com.suicune.pokeutils.Types.Grass;
import com.suicune.pokeutils.Types.Ground;
import com.suicune.pokeutils.Types.Ice;
import com.suicune.pokeutils.Types.Normal;
import com.suicune.pokeutils.Types.Poison;
import com.suicune.pokeutils.Types.Psychic;
import com.suicune.pokeutils.Types.Rock;
import com.suicune.pokeutils.Types.Steel;
import com.suicune.pokeutils.Types.Water;

public class DamageCalcTools {
	public static final double TYPE_MODIFIER_WEAK = 2;
	public static final double TYPE_MODIFIER_2X_WEAK = 4;
	public static final double TYPE_MODIFIER_RESISTANT = 0.5;
	public static final double TYPE_MODIFIER_2X_RESISTANT = 0.25;
	public static final double TYPE_MODIFIER_INMUNE = 0;
	public static final double NO_TYPE_MODIFIER = 1;

	public static final double TEMP_MODIFIER_0_LEVEL = 2;
	public static final double TEMP_MODIFIER_1_LEVEL = 3;
	public static final double TEMP_MODIFIER_2_LEVEL = 4;
	public static final double TEMP_MODIFIER_3_LEVEL = 5;
	public static final double TEMP_MODIFIER_4_LEVEL = 6;
	public static final double TEMP_MODIFIER_5_LEVEL = 7;
	public static final double TEMP_MODIFIER_6_LEVEL = 8;

	private static final int MIN_RANDOM = 85;

	// http://www.smogon.com/dp/articles/damage_formula

	public static String calculateDamagePorcent(TeamPokemon attacker,
			TeamPokemon defender, Attack attack) {
		double maxDamage = getMaxDamage(attacker, defender, attack);
		if (maxDamage == 0) {
			return null;
		}
		double minDamage = 85 * maxDamage / 100;

		double minPorcent = minDamage * 100
				/ defender.mStats[TeamPokemon.INDEX_HP];
		double maxPorcent = maxDamage * 100
				/ defender.mStats[TeamPokemon.INDEX_HP];

		return Math.round(minPorcent) + "% - " + Math.round(maxPorcent) + "%";

	}

	public static String calculateDamageTotal(TeamPokemon attacker,
			TeamPokemon defender, Attack attack) {
		double maxDamage = getMaxDamage(attacker, defender, attack);
		if (maxDamage == 0) {
			return null;
		}
		double minDamage = 85 * maxDamage / 100;
		return Math.round(minDamage) + " - " + Math.round(maxDamage);
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
	public static long getRandomDamage(TeamPokemon attacker,
			TeamPokemon defender, Attack attack) {

		int random = Math.round(new Random().nextInt(16) + MIN_RANDOM / 100);

		return getMaxDamage(attacker, defender, attack) * random;
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

	public static long getMaxDamage(TeamPokemon attacker, TeamPokemon defender,
			Attack attack) {

		int pokemonAttackStat;
		int pokemonDefenseStat;
		double attackLevelModifier;
		double defenseLevelModifier;

		boolean hasStab = ((attacker.mType1 == attack.mType) || (attacker.mType2 == attack.mType));

		if (attack.mAttackClass == Attack.CLASS_PHYSICAL) {
			pokemonAttackStat = attacker.mStats[TeamPokemon.INDEX_ATT];
			attackLevelModifier = attacker.mStatsModifier[TeamPokemon.INDEX_ATT];

			pokemonDefenseStat = defender.mStats[TeamPokemon.INDEX_DEF];
			defenseLevelModifier = defender.mStatsModifier[TeamPokemon.INDEX_DEF];
		} else {
			pokemonAttackStat = attacker.mStats[TeamPokemon.INDEX_SP_ATT];
			attackLevelModifier = attacker.mStatsModifier[TeamPokemon.INDEX_SP_ATT];

			pokemonDefenseStat = defender.mStats[TeamPokemon.INDEX_SP_DEF];
			defenseLevelModifier = defender.mStatsModifier[TeamPokemon.INDEX_SP_DEF];
		}

		double typeModifier = getTypeModifier(attack, attacker, defender);

		if (typeModifier == TYPE_MODIFIER_INMUNE) {
			return 0;
		}
		// The formula requires a Math.floor after each operation.
		double result;
		result = Math.floor(2 * attacker.mLevel / 5);
		result += 2;
		double attackStat = Math.floor(pokemonAttackStat * attackLevelModifier);
		double defenseStat = Math.floor(pokemonDefenseStat
				* defenseLevelModifier);
		result = Math.floor(result * getBasePower(attacker, defender, attack)
				* attackStat / defenseStat);
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
	public static double getTypeModifier(Attack attack, TeamPokemon attacker,
			TeamPokemon defender) {
		if (!attackerHasMoldBreaker(attacker)
				&& defenderAbilityGrantsInmunity(attack, defender)) {
			return TYPE_MODIFIER_INMUNE;
		}
		double modifier1 = getSingleModifier(attack.mType, defender.mType1);
		double modifier2 = getSingleModifier(attack.mType, defender.mType2);
		return modifier1 * modifier2;
	}

	private static boolean defenderAbilityGrantsInmunity(Attack attack,
			TeamPokemon defender) {
		switch (defender.mSelectedAbility) {
		case 26: // Levitate
			if (attack.mType == Types.GROUND) {
				return true;
			}
			return false;
		case 18: // Flash fire
			if (attack.mType == Types.FIRE) {
				return true;
			}
			return false;
		case 10: // Volt absorb
		case 78: // Motor drive!
			if (attack.mType == Types.ELECTRIC) {
				return true;
			}
			return false;
		case 11: // Water absorb
		case 87: // Dry skin
			if (attack.mType == Types.WATER) {
				return true;
			}
			return false;
		case 25: // Wonder guard!!
			switch (attack.mId) {
			case 251: // Beat up
			case 117: // Bide
			case 353: // Doom desire
			case 424: // Fire fang
			case 248: // Future sight
			case 165: // Struggle
				return false;
			default:
				if (getSingleModifier(attack.mType, defender.mType1)
						* getSingleModifier(attack.mType, defender.mType2) > 1) {
					return false;
				}
				return true;
			}
		case 43: // Soundproof!!
			// Bug Buzz, Chatter, Echoed Voice, GrassWhistle, Growl, Heal Bell,
			// Hyper Voice, Metal Sound, Perish Song, Relic Song, Roar, Round,
			// Screech, Sing, Snarl, Snore, Supersonic, Uproar,
			switch (attack.mId) {
			case 405:
			case 448:
			case 497:
			case 320:
			case 45:
			case 215:
			case 304:
			case 319:
			case 195:
			case 547:
			case 46:
			case 496:
			case 103:
			case 47:
			case 555:
			case 173:
			case 48:
			case 253:
				return true;
			default:
				return false;
			}
		default:
			return false;
		}
	}

	private static boolean attackerHasMoldBreaker(TeamPokemon attacker) {
		return attacker.mSelectedAbility == 104;
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
			return (2 / TEMP_MODIFIER_6_LEVEL);
		case 1:
			return (2 / TEMP_MODIFIER_5_LEVEL);
		case 2:
			return (2 / TEMP_MODIFIER_4_LEVEL);
		case 3:
			return (2 / TEMP_MODIFIER_3_LEVEL);
		case 4:
			return (2 / TEMP_MODIFIER_2_LEVEL);
		case 5:
			return (2 / TEMP_MODIFIER_1_LEVEL);
		case 6:
			return (TEMP_MODIFIER_0_LEVEL / 2);
		case 7:
			return (TEMP_MODIFIER_1_LEVEL / 2);
		case 8:
			return (TEMP_MODIFIER_2_LEVEL / 2);
		case 9:
			return (TEMP_MODIFIER_3_LEVEL / 2);
		case 10:
			return (TEMP_MODIFIER_4_LEVEL / 2);
		case 11:
			return (TEMP_MODIFIER_5_LEVEL / 2);
		case 12:
			return (TEMP_MODIFIER_6_LEVEL / 2);
		default:
			return 0;
		}
	}

	public static boolean isVariableAttack(Attack attack) {
		if (attack.mPower == 1) {
			return true;
		}
		switch (attack.mId) {
		case 0:
			return true;
		default:
			return false;
		}
	}

	public static int getBasePower(TeamPokemon attacker, TeamPokemon defender,
			Attack attack) {
		if (isVariableAttack(attack)) {
			switch (attack.mId) {
			case 0:
				return 0;
			default:
				return 1;
			}
		} else {
			return attack.mPower;
		}
	}
}