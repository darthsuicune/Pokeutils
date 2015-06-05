package com.suicune.poketools.model.factories;

import android.os.Bundle;

import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.gen6.Gen6Stats;

import org.json.JSONArray;
import org.json.JSONException;

public class StatsFactory {
	public static Stats createStats(int gen, int level, int[] baseStats) {
		switch (gen) {
			case 6:
			default:
				return createGen6Stats(level, baseStats);
		}
	}

	private static Stats createGen6Stats(int level, int[] baseStats) {
		return new Gen6Stats(level, baseStats);
	}

	public static Stats fromBundle(int gen, Bundle bundle) {
		Stats stats;
		switch (gen) {
			case 6:
			default:
				stats = new Gen6Stats(bundle.getInt(Stats.ARG_LEVEL),
						bundle.getIntArray(Stats.ARG_BASE));
				int[] array = bundle.getIntArray(Stats.ARG_EVS);
				stats.setEvs(array[0], array[1], array[2], array[3], array[4], array[5]);
				array = bundle.getIntArray(Stats.ARG_IVS);
				stats.setIvs(array[0], array[1], array[2], array[3], array[4], array[5]);
				break;
		}
		return stats;
	}

	public static Stats baseFromJson(int gen, int level, JSONArray statsArray)
			throws JSONException {
		switch (gen) {
			case 6:
			default:
				int[] baseStats = new int[6];
				for (int i = 0; i < 6; i++) {
					baseStats[i] = statsArray.getInt(i);
				}
				return createGen6Stats(level, baseStats);
		}
	}
}
