package com.suicune.poketools.utils;

import android.support.test.runner.AndroidJUnit4;

import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.gen6.Gen6Nature;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class IvToolsTest {
	static int[] DITTO = new int[]{48, 48, 48, 48, 48, 48};

	//Ditto 36 Sassy 87,44,46,46,44,38
	@Test public void subject1() {
		assertEquals(
				expected(36, Gen6Nature.SASSY,
						new int[]{18, 19, 20},
						new int[]{13, 14, 15},
						new int[]{18, 19, 20},
						new int[]{18, 19, 20},
						new int[]{2, 3},
						new int[]{10, 11, 12}),
				calculated(36, Gen6Nature.SASSY, new int[] {87, 44, 46, 46, 44, 38}));
	}

	//Ditto 36 Hardy 85,41,49,50,48,49
	@Test public void subject2() {
		assertEquals(
				expected(36, Gen6Nature.HARDY,
						new int[]{13, 14, 15},
						new int[]{4, 5, 6},
						new int[]{27, 28},
						new int[]{29, 30, 31},
						new int[]{24, 25, 26},
						new int[]{27, 28}),
				calculated(36, Gen6Nature.HARDY, new int[]{85, 41, 49, 50, 48, 49}));
	}

	//Ditto 37 Sassy 90,49,45,44,48,45
	@Test public void subject3() {
		assertEquals(
				expected(37, Gen6Nature.SASSY,
						new int[]{21, 22},
						new int[]{23, 24, 25},
						new int[]{13, 14},
						new int[]{10, 11, 12},
						new int[]{10, 11, 12},
						new int[]{26, 27, 28, 29, 30, 31}),
				calculated(37, Gen6Nature.SASSY, new int[]{90, 49, 45, 44, 48, 45}));
	}

	//Ditto 38 Hardy 91,48,50,52,45,48
	@Test public void subject4() {
		assertEquals(
				expected(38, Gen6Nature.HARDY,
						new int[]{18, 19},
						new int[]{18, 19},
						new int[]{23, 24, 25},
						new int[]{28, 29, 30},
						new int[]{10, 11},
						new int[]{18, 19}),
				calculated(38, Gen6Nature.HARDY, new int[]{91, 48, 50, 52, 45, 48}));
	}

	//Ditto 38 Hasty 92,46,39,51,44,45
	@Test public void subject5() {
		assertEquals(
				expected(38, Gen6Nature.HASTY,
						new int[]{20, 21, 22},
						new int[]{12, 13, 14},
						new int[]{7, 8, 9},
						new int[]{26, 27},
						new int[]{7, 8, 9},
						new int[]{0, 1}),
				calculated(38, Gen6Nature.HASTY, new int[]{92, 46, 39, 51, 44, 45}));
	}

	private TestSubject expected(int level, Nature nature, int[] hp, int[] att, int[] def,
								 int[] spatt, int[] spdef, int[] speed) {
		List<Integer> hps = fillWith(hp);
		List<Integer> atts = fillWith(att);
		List<Integer> defs = fillWith(def);
		List<Integer> spatts = fillWith(spatt);
		List<Integer> spdefs = fillWith(spdef);
		List<Integer> speeds = fillWith(speed);

		return new TestSubject(level, hps, atts, defs, spatts, spdefs, speeds, nature);
	}

	private List<Integer> fillWith(int[] ints) {
		List<Integer> stats = new ArrayList<>();
		for (int i : ints) {
			stats.add(i);
		}
		return stats;
	}

	private TestSubject calculated(int level, Nature nature, int[] stats) {
		List<Integer> hps = IvTools.calculatePossibleHpIvValues(6, level, DITTO[0], stats[0], 0);
		List<Integer> atts = IvTools.calculatePossibleIvValues(6, level, DITTO[1], stats[1], 0,
				nature.statModifier(Stats.Stat.ATTACK));
		List<Integer> defs = IvTools.calculatePossibleIvValues(6, level, DITTO[2], stats[2], 0,
				nature.statModifier(Stats.Stat.DEFENSE));
		List<Integer> spatts = IvTools.calculatePossibleIvValues(6, level, DITTO[3], stats[3], 0,
				nature.statModifier(Stats.Stat.SPECIAL_ATTACK));
		List<Integer> spdefs = IvTools.calculatePossibleIvValues(6, level, DITTO[4], stats[4], 0,
				nature.statModifier(Stats.Stat.SPECIAL_DEFENSE));
		List<Integer> speeds = IvTools.calculatePossibleIvValues(6, level, DITTO[5], stats[5], 0,
				nature.statModifier(Stats.Stat.SPEED));
		return new TestSubject(level, hps, atts, defs, spatts, spdefs, speeds, nature);
	}


	private class TestSubject {
		int level;
		Nature nature;
		Map<Stats.Stat, List<Integer>> possibleValues = new HashMap<>();

		public TestSubject(int level, List<Integer> hp, List<Integer> att, List<Integer> def,
						   List<Integer> spatt, List<Integer> spdef, List<Integer> speed,
						   Nature nature) {
			this.level = level;
			this.nature = nature;
			possibleValues.put(Stats.Stat.HP, hp);
			possibleValues.put(Stats.Stat.ATTACK, att);
			possibleValues.put(Stats.Stat.DEFENSE, def);
			possibleValues.put(Stats.Stat.SPECIAL_ATTACK, spatt);
			possibleValues.put(Stats.Stat.SPECIAL_DEFENSE, spdef);
			possibleValues.put(Stats.Stat.SPEED, speed);
		}

		@Override public boolean equals(Object o) {
			if (!(o instanceof TestSubject)) {
				return false;
			}
			TestSubject subject = (TestSubject) o;
			boolean matches = true;
			for (Stats.Stat stat : possibleValues.keySet()) {
				List<Integer> current = subject.possibleValues.get(stat);
				List<Integer> expected = possibleValues.get(stat);
				sort(current);
				sort(expected);
				if(!current.equals(expected)) {
					matches = false;
				} else {
					System.out.println("" + current.get(0) + ", " + expected.get(0));
				}
			}
			return matches;
		}

	}
}