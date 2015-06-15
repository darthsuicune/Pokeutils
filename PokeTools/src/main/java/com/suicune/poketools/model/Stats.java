package com.suicune.poketools.model;

import android.os.Bundle;

import com.suicune.poketools.R;
import com.suicune.poketools.model.gen6.Gen6Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Stats {
	public static final String ARG_BASE = "base";
	public static final String ARG_EVS = "evs";
	public static final String ARG_IVS = "ivs";
	public static final String ARG_LEVEL = "level";
	List<OnStatsChangedListener> listeners;

	protected Stats() {
		listeners = new ArrayList<>();
	}

	public void registerListener(OnStatsChangedListener listener) {
		listeners.add(listener);
	}

	public abstract int gen();

	public abstract Stats setIvs(int hp, int attack, int defense, int spattack, int spdefense,
								 int speed);

	public abstract Stats setEvs(int hp, int attack, int defense, int spattack, int spdefense,
								 int speed);

	public abstract Stats setBaseStats(int hp, int attack, int defense, int spattack, int spdefense,
									   int speed);

	public abstract Stats setCurrentValues(int hp, int attack, int defense, int spattack,
										   int spdefense,
										   int speed);

	public abstract Stats updateWith(Stats newStats);

	public abstract Map<Stat, Integer> ivs();

	public abstract Map<Stat, Integer> evs();

	public abstract Map<Stat, Integer> base();

	public abstract Map<Stat, Integer> currentValues();

	public abstract boolean checkForValidValues();

	public abstract Stats setValuesFromStats(int level);

	public abstract Stats setNature(Nature nature);

	public abstract Bundle save();

	public abstract Map<Stat, List<Integer>> calculateIvs();

	public abstract Stats calculateStats();

	public abstract Stats putIv(Stat stat, int value);

	public abstract Stats putEv(Stat stat, int value);

	public static boolean isValid(int gen, StatType statType, Stat stat, int value) {
		switch (gen) {
			case 1:
			default:
				return Gen6Stats.isValid(statType, stat, value);
		}
	}

	public abstract int get(Stat stat, StatType base);

	public enum Stat {
		HP,
		ATTACK,
		DEFENSE,
		SPECIAL,
		SPECIAL_ATTACK,
		SPECIAL_DEFENSE,
		SPEED;

		public static Stat[] values(int gen) {
			switch (gen) {
				case 1:
					return new Stat[]{
							HP, ATTACK, DEFENSE, SPECIAL, SPEED
					};
				default:
					return new Stat[]{
							HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED
					};
			}
		}
	}

	public enum StatType {
		EV(R.string.evs),
		IV(R.string.ivs),
		BASE(R.string.base_stats),
		VALUE(R.string.values), MODIFIER(R.string.modifiers);

		int resId;

		StatType(int resId) {
			this.resId = resId;
		}

		public int resId() {
			return resId;
		}
	}

	protected void notifyChanged() {
		for (OnStatsChangedListener listener : listeners) {
			listener.onStatsChanged(this);
		}
	}

	public interface OnStatsChangedListener {
		void onStatsChanged(Stats newStats);
	}
}
