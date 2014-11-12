package com.suicune.poketools.model.factories;

import android.os.Bundle;

import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.gen6.Gen6Stats;

/**
 * Created by lapuente on 17.09.14.
 */
public class StatsFactory {
	public static Stats createStats(int gen, int level, int[] baseStats) {
		switch (gen) {
			case 6:
				return createGen6Stats(level, baseStats);
			default:
				return null;
		}
	}

	private static Stats createGen6Stats(int level, int[] baseStats) {
		return new Gen6Stats(level, baseStats);
	}

	public static Stats fromBundle(int gen, Bundle bundle) {
		Stats stats;
		switch (gen) {
			case 6:
				stats = new Gen6Stats(bundle.getInt(Stats.ARG_LEVEL),
						bundle.getIntArray(Stats.ARG_BASE));
				int[] array = bundle.getIntArray(Stats.ARG_EVS);
				stats.setEvs(array[0], array[1], array[2], array[3], array[4], array[5]);
				array = bundle.getIntArray(Stats.ARG_IVS);
				stats.setIvs(array[0], array[1], array[2], array[3], array[4], array[5]);
				break;
			default:
				stats = null;
		}
		return stats;
	}
}
