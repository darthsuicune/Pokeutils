package com.suicune.poketools.model;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lapuente on 17.09.14.
 */
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

	public abstract Stats setIvs(int hp, int attack, int defense, int spattack, int spdefense, int speed);

	public abstract Stats setEvs(int hp, int attack, int defense, int spattack, int spdefense, int speed);

	public abstract Stats setBaseStats(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed);

	public abstract Stats setCurrentValues(int hp, int attack, int defense, int spattack, int spdefense,
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

	public enum Stat {
		HP,
		ATTACK,
		DEFENSE,
		SPECIAL,
		SPECIAL_ATTACK,
		SPECIAL_DEFENSE,
		SPEED;
	}

	public enum StatType {
		EV,
		IV,
		BASE,
		VALUE
	}

	protected void notifyChanged() {
		for(OnStatsChangedListener listener : listeners) {
			listener.onStatsChanged(this);
		}
	}

	public interface OnStatsChangedListener {
		public void onStatsChanged(Stats newStats);
	}
}
