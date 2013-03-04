package com.suicune.pokeutils;

public class Types {
	public static final int NORMAL = 0;
	public static final int FIGHTING = 1;
	public static final int FLYING = 2;
	public static final int POISON = 3;
	public static final int GROUND = 4;
	public static final int ROCK = 5;
	public static final int BUG = 6;
	public static final int GHOST = 7;
	public static final int STEEL = 8;
	public static final int FIRE = 9;
	public static final int WATER = 10;
	public static final int GRASS = 11;
	public static final int ELECTRIC = 12;
	public static final int PSYCHIC = 13;
	public static final int ICE = 14;
	public static final int DRAGON = 15;
	public static final int DARK = 16;

	public static class Normal {
		public static int[] weaknesses = { FIGHTING };

		public static int[] resistances = {};

		public static int[] inmunities = { GHOST };
	}

	public static class Fighting {
		public static int[] weaknesses = { FLYING, PSYCHIC };

		public static int[] resistances = { ROCK, BUG, DARK };

		public static int[] inmunities = {};
	}

	public static class Flying {
		public static int[] weaknesses = { ROCK, ELECTRIC, ICE };

		public static int[] resistances = { FIGHTING, BUG, GRASS };

		public static int[] inmunities = { GROUND };
	}

	public static class Poison {
		public static int[] weaknesses = { GROUND, PSYCHIC };

		public static int[] resistances = { FIGHTING, POISON, BUG, GRASS };

		public static int[] inmunities = {};
	}

	public static class Ground {
		public static int[] weaknesses = { WATER, GRASS, ICE };

		public static int[] resistances = { POISON, ROCK };

		public static int[] inmunities = { ELECTRIC };
	}

	public static class Rock {
		public static int[] weaknesses = { FIGHTING, GROUND, STEEL, WATER,
				GRASS };

		public static int[] resistances = { NORMAL, FLYING, POISON, FIRE };

		public static int[] inmunities = {};
	}

	public static class Bug {
		public static int[] weaknesses = { FLYING, ROCK, FIRE };

		public static int[] resistances = { FIGHTING, GROUND, GRASS };

		public static int[] inmunities = {};
	}

	public static class Ghost {
		public static int[] weaknesses = { GHOST, DARK };

		public static int[] resistances = { POISON, BUG };

		public static int[] inmunities = { NORMAL, FIGHTING };
	}

	public static class Steel {
		public static int[] weaknesses = { FIGHTING, GROUND, FIRE };

		public static int[] resistances = { NORMAL, FLYING, ROCK, BUG, GHOST,
				STEEL, GRASS, PSYCHIC, ICE, DRAGON, DARK };

		public static int[] inmunities = { POISON };
	}

	public static class Fire {
		public static int[] weaknesses = { GROUND, ROCK, WATER };

		public static int[] resistances = { BUG, STEEL, FIRE, GRASS, ICE };

		public static int[] inmunities = {};
	}

	public static class Water {
		public static int[] weaknesses = { GRASS, ELECTRIC };

		public static int[] resistances = { STEEL, FIRE, WATER, ICE };

		public static int[] inmunities = {};
	}

	public static class Grass {
		public static int[] weaknesses = { FLYING, POISON, BUG, FIRE, ICE };

		public static int[] resistances = { GROUND, WATER, GRASS, ELECTRIC };

		public static int[] inmunities = {};
	}

	public static class Electric {
		public static int[] weaknesses = { GROUND };

		public static int[] resistances = { FLYING, STEEL, ELECTRIC };

		public static int[] inmunities = {};
	}

	public static class Psychic {
		public static int[] weaknesses = { BUG, GHOST, DARK };

		public static int[] resistances = { FIGHTING, PSYCHIC };

		public static int[] inmunities = {};
	}

	public static class Ice {
		public static int[] weaknesses = { FIGHTING, ROCK, STEEL, FIRE };

		public static int[] resistances = { ICE };

		public static int[] inmunities = {};
	}

	public static class Dragon {
		public static int[] weaknesses = { ICE, DRAGON };

		public static int[] resistances = { FIRE, WATER, GRASS, ELECTRIC };

		public static int[] inmunities = {};
	}

	public static class Dark {
		public static int[] weaknesses = { FIGHTING, BUG };

		public static int[] resistances = { GHOST, DARK };

		public static int[] inmunities = { PSYCHIC };
	}
}