package com.suicune.poketools.model.factories;

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
}
