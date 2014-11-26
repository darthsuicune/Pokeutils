package com.suicune.poketools.utils;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Battlefield;
import com.suicune.poketools.model.Item;
import com.suicune.poketools.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 01.01.14.
 * <p/>
 * Based on data from http://bulbapedia.bulbagarden.net/wiki/Damage
 */
public class DamageCalcTools {


	public static List<Integer> getDamage(int gen, Attack attack, Pokemon attacker,
										  Pokemon defender, Battlefield field) {
		switch (gen) {
			case 6:
			default:
				return getGen6Damage(attack, attacker, defender, field);
		}
	}

	public static List<Integer> getGen6Damage(Attack attack, Pokemon attacker, Pokemon defender,
											  Battlefield field) {
		List<Integer> results = new ArrayList<>();
		if (attack.category() != Attack.Category.OTHER) {
			int baseDamage = baseDamage(attacker.level(), attacker.finalValue(attack.usedStat()),
					defender.finalValue(attack.receivedStat()), attack.power(attacker, defender));
			boolean stab = (attack.type() == attacker.type1() || attack.type() == attacker.type2());
			double typeEffectiveness = attack.getEffectiveness(defender);
			double critical = (field.isCritical(attack)) ? 1.5 : 1.0;
			double modifier =
					getModifier(attacker.item(), defender.item(), attacker.currentAbility(),
							defender.currentAbility(), field, attacker.isBurned());
			double modifiedDamage =
					modifiedDamage(baseDamage, stab, typeEffectiveness, critical, modifier);
			for (double i = 0; i <= 15; i++) {
				results.add((int) (modifiedDamage * (100.0 - i) / 100.0));
			}
		}

		return results;
	}

	/**
	 * Damage = ( ( 2*Level + 10 )/250 * Attack/Defense * Base + 2 ) * Modifier
	 *
	 * @param level
	 * @param attack
	 * @param defense
	 * @param base
	 * @return
	 */
	private static int baseDamage(int level, int attack, int defense, int base) {
		double first = (2.0 * (double) level + 10.0) / 250.0;
		double second = (double) attack / (double) defense;
		return (int) (first * second * base + 2.0);
	}

	private static double modifiedDamage(int baseDamage, boolean stab, double typeEffectiveness,
										 double critical, double modifier) {
		double stabBonus = (stab) ? 1.5 : 1.0;
		return (int) (baseDamage * stabBonus * typeEffectiveness * critical * modifier);
	}

	private static double getModifier(Item attackerItem, Item defenderItem, Ability attackerAbility,
									  Ability defenderAbility, Battlefield field,
									  boolean isAttackerBurned) {
		//TODO: ... Do.
		double isBurned = (isAttackerBurned) ? 0.5 : 1;
		return 1.0 * isBurned;
	}
}
