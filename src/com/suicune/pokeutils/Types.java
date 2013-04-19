package com.suicune.pokeutils;

public class Types {
	public static final int NONE = 0;
	public static final int NORMAL = 1;
	public static final int FIGHTING = 2;
	public static final int FLYING = 3;
	public static final int POISON = 4;
	public static final int GROUND = 5;
	public static final int ROCK = 6;
	public static final int BUG = 7;
	public static final int GHOST = 8;
	public static final int STEEL = 9;
	public static final int FIRE = 10;
	public static final int WATER = 11;
	public static final int GRASS = 12;
	public static final int ELECTRIC = 13;
	public static final int PSYCHIC = 14;
	public static final int ICE = 15;
	public static final int DRAGON = 16;
	public static final int DARK = 17;
	public static final int COUNT = 17;

	public static int getTypeName(int type) {
		switch (type) {
		case NORMAL:
			return Normal.NAME;
		case FIGHTING:
			return Fighting.NAME;
		case FLYING:
			return Flying.NAME;
		case POISON:
			return Poison.NAME;
		case GROUND:
			return Ground.NAME;
		case ROCK:
			return Rock.NAME;
		case BUG:
			return Bug.NAME;
		case GHOST:
			return Ghost.NAME;
		case STEEL:
			return Steel.NAME;
		case FIRE:
			return Fire.NAME;
		case WATER:
			return Water.NAME;
		case GRASS:
			return Grass.NAME;
		case ELECTRIC:
			return Electric.NAME;
		case PSYCHIC:
			return Psychic.NAME;
		case ICE:
			return Ice.NAME;
		case DRAGON:
			return Dragon.NAME;
		case DARK:
			return Dark.NAME;
		}
		return 0;
	}

	public static class Normal {
		public static int NAME = R.string.type_normal;
		public static int[] weaknesses = { FIGHTING };
		public static int[] resistances = {};
		public static int[] inmunities = { GHOST };
	}

	public static class Fighting {
		public static int NAME = R.string.type_fighting;
		public static int[] weaknesses = { FLYING, PSYCHIC };
		public static int[] resistances = { ROCK, BUG, DARK };
		public static int[] inmunities = {};
	}

	public static class Flying {
		public static int NAME = R.string.type_flying;
		public static int[] weaknesses = { ROCK, ELECTRIC, ICE };
		public static int[] resistances = { FIGHTING, BUG, GRASS };
		public static int[] inmunities = { GROUND };
	}

	public static class Poison {
		public static int NAME = R.string.type_poison;
		public static int[] weaknesses = { GROUND, PSYCHIC };
		public static int[] resistances = { FIGHTING, POISON, BUG, GRASS };
		public static int[] inmunities = {};
	}

	public static class Ground {
		public static int NAME = R.string.type_ground;
		public static int[] weaknesses = { WATER, GRASS, ICE };
		public static int[] resistances = { POISON, ROCK };
		public static int[] inmunities = { ELECTRIC };
	}

	public static class Rock {
		public static int NAME = R.string.type_rock;
		public static int[] weaknesses = { FIGHTING, GROUND, STEEL, WATER,
				GRASS };
		public static int[] resistances = { NORMAL, FLYING, POISON, FIRE };
		public static int[] inmunities = {};
	}

	public static class Bug {
		public static int NAME = R.string.type_bug;
		public static int[] weaknesses = { FLYING, ROCK, FIRE };
		public static int[] resistances = { FIGHTING, GROUND, GRASS };
		public static int[] inmunities = {};
	}

	public static class Ghost {
		public static int NAME = R.string.type_ghost;
		public static int[] weaknesses = { GHOST, DARK };
		public static int[] resistances = { POISON, BUG };
		public static int[] inmunities = { NORMAL, FIGHTING };
	}

	public static class Steel {
		public static int NAME = R.string.type_steel;
		public static int[] weaknesses = { FIGHTING, GROUND, FIRE };
		public static int[] resistances = { NORMAL, FLYING, ROCK, BUG, GHOST,
				STEEL, GRASS, PSYCHIC, ICE, DRAGON, DARK };
		public static int[] inmunities = { POISON };
	}

	public static class Fire {
		public static int NAME = R.string.type_fire;
		public static int[] weaknesses = { GROUND, ROCK, WATER };
		public static int[] resistances = { BUG, STEEL, FIRE, GRASS, ICE };
		public static int[] inmunities = {};
	}

	public static class Water {
		public static int NAME = R.string.type_water;
		public static int[] weaknesses = { GRASS, ELECTRIC };
		public static int[] resistances = { STEEL, FIRE, WATER, ICE };
		public static int[] inmunities = {};
	}

	public static class Grass {
		public static int NAME = R.string.type_grass;
		public static int[] weaknesses = { FLYING, POISON, BUG, FIRE, ICE };
		public static int[] resistances = { GROUND, WATER, GRASS, ELECTRIC };
		public static int[] inmunities = {};
	}

	public static class Electric {
		public static int NAME = R.string.type_electric;
		public static int[] weaknesses = { GROUND };
		public static int[] resistances = { FLYING, STEEL, ELECTRIC };
		public static int[] inmunities = {};
	}

	public static class Psychic {
		public static int NAME = R.string.type_psychic;
		public static int[] weaknesses = { BUG, GHOST, DARK };
		public static int[] resistances = { FIGHTING, PSYCHIC };
		public static int[] inmunities = {};
	}

	public static class Ice {
		public static int NAME = R.string.type_ice;
		public static int[] weaknesses = { FIGHTING, ROCK, STEEL, FIRE };
		public static int[] resistances = { ICE };
		public static int[] inmunities = {};
	}

	public static class Dragon {
		public static int NAME = R.string.type_dragon;
		public static int[] weaknesses = { ICE, DRAGON };
		public static int[] resistances = { FIRE, WATER, GRASS, ELECTRIC };
		public static int[] inmunities = {};
	}

	public static class Dark {
		public static int NAME = R.string.type_dark;
		public static int[] weaknesses = { FIGHTING, BUG };
		public static int[] resistances = { GHOST, DARK };
		public static int[] inmunities = { PSYCHIC };
	}
}