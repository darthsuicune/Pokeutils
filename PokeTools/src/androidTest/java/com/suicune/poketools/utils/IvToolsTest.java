package com.suicune.poketools.utils;

import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;
import static org.junit.Assert.assertEquals;

public class IvToolsTest {

	@Test public void ivsAreCorrectlyCalculatedWithoutEvs() {
		assertEquals(expected1(), calculated1());
	}

	private TestSubject calculated1() {
		List<Integer> hps;
		List<Integer> atts;
		List<Integer> defs;
		List<Integer> spatts;
		List<Integer> spdefs;
		List<Integer> speeds;

		TestSubject subject = new TestSubject(30, );
		return null;
	}

	private TestSubject expected1() {
		return new TestSubject(30, {});
	}


	private class TestSubject {
		int level;
		Nature nature;
		Map<Stats.Stat, List<Integer>> possibleValues = new HashMap<>();

		public TestSubject(int level, Integer[] hp, Integer[] att, Integer[] def, Integer[] spatt,
						   Integer[] spdef, Integer[] speed, Nature nature) {
			this.level = level;
			this.nature = nature;
			possibleValues.put(Stats.Stat.HP, Arrays.asList(hp));
			possibleValues.put(Stats.Stat.ATTACK, Arrays.asList(att));
			possibleValues.put(Stats.Stat.DEFENSE, Arrays.asList(def));
			possibleValues.put(Stats.Stat.SPECIAL_ATTACK, Arrays.asList(spatt));
			possibleValues.put(Stats.Stat.SPECIAL_DEFENSE, Arrays.asList(spdef));
			possibleValues.put(Stats.Stat.SPEED, Arrays.asList(speed));
		}

		@Override public boolean equals(Object o) {
			TestSubject subject = (TestSubject) o;
			boolean matches = true;
			for(Stats.Stat stat : possibleValues.keySet()) {
				List<Integer> current = possibleValues.get(stat);
				List<Integer> expected = subject.possibleValues.get(stat);
				sort(current);
				sort(expected);
				matches = current.equals(expected);
			}
			return matches;
		}

	}
}