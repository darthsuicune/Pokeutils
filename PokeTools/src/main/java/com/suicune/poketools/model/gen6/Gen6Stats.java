package com.suicune.poketools.model.gen6;

import android.os.Bundle;

import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.utils.IvTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Nature nature;
	public int level;

	public Gen6Stats(int level, int[] baseStats) {
		this.level = level;
		ivs = new HashMap<>();
		evs = new HashMap<>();
		base = new HashMap<>();
		values = new HashMap<>();
		this.nature = Gen6Nature.HARDY;
		setIvs(MAX_IV, MAX_IV, MAX_IV, MAX_IV, MAX_IV, MAX_IV);
		setEvs(MIN_EV, MIN_EV, MIN_EV, MIN_EV, MIN_EV, MIN_EV);
		setBaseStats(baseStats[0], baseStats[1], baseStats[2], baseStats[3], baseStats[4],
				baseStats[5]);
		setValuesFromStats(level);
	}

	@Override public boolean checkForValidValues() {
		return checkForValidValues(evs, MAX_EV, MIN_EV) &&
			   checkForValidValues(ivs, MAX_IV, MIN_IV) &&
			   checkForValidValues(base, MAX_BASE, MIN_BASE) &&
			   checkForValidValues(values, MAX_VALUE, MIN_VALUE);
	}

	private boolean checkForValidValues(Map<Stat, Integer> values, int max, int min) {
		return (values.get(Stat.HP) > max && values.get(Stat.HP) < min &&
				values.get(Stat.ATTACK) > max && values.get(Stat.ATTACK) < min &&
				values.get(Stat.DEFENSE) > max && values.get(Stat.DEFENSE) < min &&
				values.get(Stat.SPECIAL_ATTACK) > max && values.get(Stat.SPECIAL_ATTACK) < min &&
				values.get(Stat.SPECIAL_DEFENSE) > max && values.get(Stat.SPECIAL_DEFENSE) < min &&
				values.get(Stat.SPEED) > max && values.get(Stat.SPEED) < min);
	}

	@Override public int gen() {
		return 6;
	}

	@Override
	public Stats setIvs(int hp, int attack, int defense, int spattack, int spdefense, int speed) {
		ivs.put(Stat.HP, hp);
		ivs.put(Stat.ATTACK, attack);
		ivs.put(Stat.DEFENSE, defense);
		ivs.put(Stat.SPECIAL_ATTACK, spattack);
		ivs.put(Stat.SPECIAL_DEFENSE, spdefense);
		ivs.put(Stat.SPEED, speed);
		checkForValidValues(ivs, MAX_IV, MIN_IV);
		notifyChanged();
		return this;
	}

	@Override
	public Stats setEvs(int hp, int attack, int defense, int spattack, int spdefense, int speed) {
		evs.put(Stat.HP, hp);
		evs.put(Stat.ATTACK, attack);
		evs.put(Stat.DEFENSE, defense);
		evs.put(Stat.SPECIAL_ATTACK, spattack);
		evs.put(Stat.SPECIAL_DEFENSE, spdefense);
		evs.put(Stat.SPEED, speed);
		checkForValidValues(evs, MAX_EV, MIN_IV);
		notifyChanged();
		return this;
	}

	@Override
	public Stats setBaseStats(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		base.put(Stat.HP, hp);
		base.put(Stat.ATTACK, attack);
		base.put(Stat.DEFENSE, defense);
		base.put(Stat.SPECIAL_ATTACK, spattack);
		base.put(Stat.SPECIAL_DEFENSE, spdefense);
		base.put(Stat.SPEED, speed);
		checkForValidValues(base, MAX_BASE, MIN_BASE);
		notifyChanged();
		return this;
	}

	@Override
	public Stats setCurrentValues(int hp, int attack, int defense, int spattack, int spdefense,
								  int speed) {
		values.put(Stat.HP, hp);
		values.put(Stat.ATTACK, attack);
		values.put(Stat.DEFENSE, defense);
		values.put(Stat.SPECIAL_ATTACK, spattack);
		values.put(Stat.SPECIAL_DEFENSE, spdefense);
		values.put(Stat.SPEED, speed);
		checkForValidValues(values, MAX_VALUE, MIN_VALUE);
		notifyChanged();
		return this;
	}

	@Override public Stats setValuesFromStats(int level) {
		this.level = level;
		for (Stat stat : Stat.values(gen())) {
			values.put(stat, calculateValue(stat, base.get(stat), ivs.get(stat), evs.get(stat)));
		}
		return this;
	}

	private int calculateValue(Stat stat, int base, int iv, int ev) {
		if (stat == Stat.HP) {
			return IvTools.calculateHp(gen(), level, base, iv, ev);
		} else {
			return IvTools.calculateStat(gen(), level, base, iv, ev, nature.statModifier(stat));
		}
	}

	@Override public Stats setNature(Nature nature) {
		this.nature = nature;
		this.setValuesFromStats(level);
		return this;
	}

	@Override public Bundle save() {
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_LEVEL, level);
		bundle.putIntArray(ARG_EVS, toArray(evs));
		bundle.putIntArray(ARG_IVS, toArray(ivs));
		bundle.putIntArray(ARG_BASE, toArray(base));
		return bundle;
	}

	@Override public Map<Stat, List<Integer>> calculateIvs() {
		Map<Stat, List<Integer>> result = new HashMap<>();
		for (Stat stat : Stat.values(gen())) {
			result.put(stat,
					IvTools.calculatePossibleStatValues(gen(), level, base.get(stat), ivs.get(stat),
							evs.get(stat), nature.statModifier(stat)));
		}
		return result;
	}

	@Override public Stats calculateStats() {
		return setValuesFromStats(this.level);
	}

	@Override public Stats putIv(Stat stat, int value) {
		ivs.put(stat, value);
		values.put(stat, calculateValue(stat, base.get(stat), value, evs.get(stat)));
		return this;
	}

	@Override public Stats putEv(Stat stat, int value) {
		evs.put(stat, value);
		values.put(stat, calculateValue(stat, base.get(stat), ivs.get(stat), value));
		return this;
	}

	private int[] toArray(Map<Stat, Integer> map) {
		return new int[]{map.get(Stat.HP),
						 map.get(Stat.ATTACK),
						 map.get(Stat.DEFENSE),
						 map.get(Stat.SPECIAL_ATTACK),
						 map.get(Stat.SPECIAL_DEFENSE),
						 map.get(Stat.SPEED)};
	}

	@Override public Stats updateWith(Stats newStats) {
		ivs = newStats.ivs();
		evs = newStats.evs();
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
