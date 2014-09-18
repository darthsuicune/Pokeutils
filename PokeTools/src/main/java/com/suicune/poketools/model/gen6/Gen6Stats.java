package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Stats;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on min1.min1.14.
 */
public class Gen6Stats extends Stats {
    private static final int MAX_EV = 255;
    private static final int MAX_IV = 31;
    private static final int MAX_BASE = 255;
	private static final int MAX_VALUE = 255;
    private static final int MIN_EV = 0;
    private static final int MIN_IV = 0;
    private static final int MIN_BASE = 1;
	private static final int MIN_VALUE = 0;

    public final Map<Stat, Integer> ivs;
	public final Map<Stat, Integer> evs;
	public final Map<Stat, Integer> base;
	public final Map<Stat, Integer> values;

    public Stats.StatType statType;

	public Gen6Stats() {
		ivs = new HashMap<>();
		evs = new HashMap<>();
		base = new HashMap<>();
		values = new HashMap<>();
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
    
    private boolean checkForValidValues(Map<Stat, Integer> values, int max, int min){
		boolean areValid = true;
        if (values.get(Stat.HP) > max || values.get(Stat.HP) < min) {
            values.put(Stat.HP, min);
			areValid = false;
        }
        if (values.get(Stat.ATTACK) > max || values.get(Stat.ATTACK) < min) {
            values.put(Stat.ATTACK, min);
			areValid = false;
        }
        if (values.get(Stat.DEFENSE) > max || values.get(Stat.DEFENSE) < min) {
            values.put(Stat.DEFENSE, min);
			areValid = false;
        }
        if (values.get(Stat.SPECIAL_ATTACK) > max || values.get(Stat.SPECIAL_ATTACK) < min) {
            values.put(Stat.SPECIAL_ATTACK, min);
			areValid = false;
        }
        if (values.get(Stat.SPECIAL_DEFENSE) > max || values.get(Stat.SPECIAL_DEFENSE) < min) {
            values.put(Stat.SPECIAL_DEFENSE, min);
			areValid = false;
        }
        if (values.get(Stat.SPEED) > max || values.get(Stat.SPEED) < min) {
            values.put(Stat.SPEED, min);
			areValid = false;
        }
		return areValid;
    }

	@Override public int gen() {
		return 6;
	}

	@Override public Stats setIvs(int hp, int attack, int defense, int spattack, int spdefense,
								  int speed) {
		checkForValidValues();
		notifyChanged();
		return null;
	}

	@Override public Stats setEvs(int hp, int attack, int defense, int spattack, int spdefense,
								  int speed) {
		checkForValidValues();
		notifyChanged();
		return null;
	}

	@Override public Stats setBaseStats(int hp, int attack, int defense, int spattack,
										int spdefense, int speed) {
		checkForValidValues();
		notifyChanged();
		return null;
	}

	@Override public Stats setCurrentValues(int hp, int attack, int defense, int spattack,
											int spdefense, int speed) {
		checkForValidValues();
		notifyChanged();
		return null;
	}

	@Override public Stats updateWith(Stats newStats) {
		checkForValidValues();
		notifyChanged();
		return null;
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
