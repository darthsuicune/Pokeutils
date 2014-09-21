package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Stats;
import com.suicune.poketools.utils.IvTools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on min1.min1.14.
 */
public class Gen6Stats extends Stats {
	private static final int MAX_EV = 255;
	private static final int MAX_IV = 31;
	private static final int MAX_BASE = 255;
	private static final int MAX_VALUE = 714;
	private static final int MIN_EV = 0;
	private static final int MIN_IV = 0;
	private static final int MIN_BASE = 1;
	private static final int MIN_VALUE = 0;

	public Map<Stat, Integer> ivs;
	public Map<Stat, Integer> evs;
	public Map<Stat, Integer> base;
	public Map<Stat, Integer> values;

	public Stats.StatType statType;

	public Gen6Stats(int level, int[] baseStats) {
		ivs = new HashMap<>();
		evs = new HashMap<>();
		base = new HashMap<>();
		values = new HashMap<>();
		setIvs(MAX_IV, MAX_IV, MAX_IV, MAX_IV, MAX_IV, MAX_IV);
		setEvs(MIN_EV, MIN_EV, MIN_EV, MIN_EV, MIN_EV, MIN_EV);
		setBaseStats(baseStats[0], baseStats[1], baseStats[2], baseStats[3], baseStats[4],
				baseStats[5]);
		setValuesFromStats(level);
	}

	@Override public boolean checkForValidValues() {
		switch (statType) {
			case EV:
				return checkForValidValues(evs, MAX_EV, MIN_EV);
			case IV:
				return checkForValidValues(ivs, MAX_IV, MIN_IV);
			case BASE:
				return checkForValidValues(base, MAX_BASE, MIN_BASE);
			case VALUE:
				return checkForValidValues(values, MAX_VALUE, MIN_VALUE);
			default:
				return false;
		}
	}

	private boolean checkForValidValues(Map<Stat, Integer> values, int max, int min) {
		boolean areValid = true;
		if (values.get(Stat.HP) > max || values.get(Stat.HP) < min ||
			values.get(Stat.ATTACK) > max || values.get(Stat.ATTACK) < min ||
			values.get(Stat.DEFENSE) > max || values.get(Stat.DEFENSE) < min ||
			values.get(Stat.SPECIAL_ATTACK) > max || values.get(Stat.SPECIAL_ATTACK) < min ||
			values.get(Stat.SPECIAL_DEFENSE) > max || values.get(Stat.SPECIAL_DEFENSE) < min ||
			values.get(Stat.SPEED) > max || values.get(Stat.SPEED) < min) {
			areValid = false;
		}
		return areValid;
	}

	@Override public int gen() {
		return 6;
	}

	@Override public Stats setIvs(int hp, int attack, int defense, int spattack, int spdefense,
								  int speed) {
		ivs.put(Stat.HP, hp);
		ivs.put(Stat.ATTACK, attack);
		ivs.put(Stat.DEFENSE, defense);
		ivs.put(Stat.SPECIAL_ATTACK, spattack);
		ivs.put(Stat.SPECIAL_DEFENSE, spdefense);
		ivs.put(Stat.SPEED, speed);
		checkForValidValues();
		notifyChanged();
		return this;
	}

	@Override public Stats setEvs(int hp, int attack, int defense, int spattack, int spdefense,
								  int speed) {
		evs.put(Stat.HP, hp);
		evs.put(Stat.ATTACK, attack);
		evs.put(Stat.DEFENSE, defense);
		evs.put(Stat.SPECIAL_ATTACK, spattack);
		evs.put(Stat.SPECIAL_DEFENSE, spdefense);
		evs.put(Stat.SPEED, speed);
		checkForValidValues();
		notifyChanged();
		return this;
	}

	@Override public Stats setBaseStats(int hp, int attack, int defense, int spattack,
										int spdefense, int speed) {
		ivs.put(Stat.HP, hp);
		ivs.put(Stat.ATTACK, attack);
		ivs.put(Stat.DEFENSE, defense);
		ivs.put(Stat.SPECIAL_ATTACK, spattack);
		ivs.put(Stat.SPECIAL_DEFENSE, spdefense);
		ivs.put(Stat.SPEED, speed);
		checkForValidValues();
		notifyChanged();
		return this;
	}

	public Stats setValuesFromStats(int level) {
		values.put(Stat.HP,
				IvTools.calculateHp(level, base.get(Stat.HP), ivs.get(Stat.HP), evs.get(Stat.HP)));
		values.put(Stat.ATTACK,
				IvTools.calculateStat(level, base.get(Stat.ATTACK), ivs.get(Stat.ATTACK),
						evs.get(Stat.ATTACK)));
		values.put(Stat.DEFENSE,
				IvTools.calculateStat(level, base.get(Stat.DEFENSE), ivs.get(Stat.DEFENSE),
						evs.get(Stat.DEFENSE)));
		values.put(Stat.SPECIAL_ATTACK, IvTools.calculateStat(level, base.get(Stat.SPECIAL_ATTACK),
				ivs.get(Stat.SPECIAL_ATTACK), evs.get(Stat.SPECIAL_ATTACK)));
		values.put(Stat.SPECIAL_DEFENSE,
				IvTools.calculateStat(level, base.get(Stat.SPECIAL_DEFENSE),
						ivs.get(Stat.SPECIAL_DEFENSE), evs.get(Stat.SPECIAL_DEFENSE)));
		values.put(Stat.SPEED,
				IvTools.calculateStat(level, base.get(Stat.SPEED), ivs.get(Stat.SPEED),
						evs.get(Stat.SPEED)));
		return this;
	}

	@Override public Stats setCurrentValues(int hp, int attack, int defense, int spattack,
											int spdefense, int speed) {
		ivs.put(Stat.HP, hp);
		ivs.put(Stat.ATTACK, attack);
		ivs.put(Stat.DEFENSE, defense);
		ivs.put(Stat.SPECIAL_ATTACK, spattack);
		ivs.put(Stat.SPECIAL_DEFENSE, spdefense);
		ivs.put(Stat.SPEED, speed);
		checkForValidValues();
		notifyChanged();
		return this;
	}

	@Override public Stats updateWith(Stats newStats) {
		ivs = newStats.ivs();
		evs = newStats.evs();
		base = newStats.base();
		values = newStats.currentValues();
		checkForValidValues();
		notifyChanged();
		return this;
	}

	@Override public Map<Stat, Integer> ivs() {
		return ivs;
	}

	@Override public Map<Stat, Integer> evs() {
		return evs;
	}

	@Override public Map<Stat, Integer> base() {
		return base;
	}

	@Override public Map<Stat, Integer> currentValues() {
		return values;
	}

}
