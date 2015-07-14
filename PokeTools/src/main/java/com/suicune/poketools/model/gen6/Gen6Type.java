package com.suicune.poketools.model.gen6;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.view.Coloreable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01.01.14.
 */

public enum Gen6Type implements Type, Coloreable {
	NONE(0, R.string.type_none, R.color.none),
	NORMAL(1, R.string.type_normal, R.color.normal),
	FIGHTING(2, R.string.type_fighting, R.color.fighting),
	FLYING(3, R.string.type_flying, R.color.flying),
	POISON(4, R.string.type_poison, R.color.poison),
	GROUND(5, R.string.type_ground, R.color.ground),
	ROCK(6, R.string.type_rock, R.color.rock),
	BUG(7, R.string.type_bug, R.color.bug),
	GHOST(8, R.string.type_ghost, R.color.ghost),
	STEEL(9, R.string.type_steel, R.color.steel),
	FIRE(10, R.string.type_fire, R.color.fire),
	WATER(11, R.string.type_water, R.color.water),
	GRASS(12, R.string.type_grass, R.color.grass),
	ELECTRIC(13, R.string.type_electric, R.color.electric),
	PSYCHIC(14, R.string.type_psychic, R.color.psychic),
	ICE(15, R.string.type_ice, R.color.ice),
	DRAGON(16, R.string.type_dragon, R.color.dragon),
	DARK(17, R.string.type_dark, R.color.dark),
	FAIRY(18, R.string.type_fairy, R.color.fairy);

	private Gen6Type(int code, int nameResId, int color) {
		this.code = code;
		this.nameResId = nameResId;
		this.color = color;
	}

	public List<Type> weaknesses;
	public List<Type> resistances;
	public List<Type> immunities;
	public int nameResId;
	public int code;
	public int color;

	/**
	 * Set all the values
	 */
	static {
		NONE.weaknesses = Arrays.asList();
		NONE.resistances = Arrays.asList();
		NONE.immunities = Arrays.asList();

		NORMAL.weaknesses = Arrays.asList((Type) FIGHTING);
		NORMAL.resistances = Arrays.asList();
		NORMAL.immunities = Arrays.asList((Type) GHOST);

		FIGHTING.weaknesses = Arrays.asList((Type) FLYING, PSYCHIC, FAIRY);
		FIGHTING.resistances = Arrays.asList((Type) ROCK, BUG, DARK);
		FIGHTING.immunities = Arrays.asList();

		FLYING.weaknesses = Arrays.asList((Type) ROCK, ELECTRIC, ICE);
		FLYING.resistances = Arrays.asList((Type) FIGHTING, BUG, GRASS);
		FLYING.immunities = Arrays.asList((Type) GROUND);

		POISON.weaknesses = Arrays.asList((Type) GROUND, PSYCHIC);
		POISON.resistances = Arrays.asList((Type) FIGHTING, POISON, BUG, GRASS, FAIRY);
		POISON.immunities = Arrays.asList();

		GROUND.weaknesses = Arrays.asList((Type) WATER, GRASS, ICE);
		GROUND.resistances = Arrays.asList((Type) POISON, ROCK);
		GROUND.immunities = Arrays.asList((Type) ELECTRIC);

		ROCK.weaknesses = Arrays.asList((Type) FIGHTING, GROUND, STEEL, WATER, GRASS);
		ROCK.resistances = Arrays.asList((Type) NORMAL, FLYING, POISON, FIRE);
		ROCK.immunities = Arrays.asList();

		BUG.weaknesses = Arrays.asList((Type) FLYING, ROCK, FIRE);
		BUG.resistances = Arrays.asList((Type) FIGHTING, GROUND, GRASS);
		BUG.immunities = Arrays.asList();

		GHOST.weaknesses = Arrays.asList((Type) GHOST, DARK);
		GHOST.resistances = Arrays.asList((Type) POISON, BUG);
		GHOST.immunities = Arrays.asList((Type) NORMAL, FIGHTING);

		STEEL.weaknesses = Arrays.asList((Type) FIGHTING, GROUND, FIRE);
		STEEL.resistances =
				Arrays.asList((Type) NORMAL, FLYING, ROCK, BUG, STEEL, GRASS, PSYCHIC, ICE, DRAGON,
						FAIRY);
		STEEL.immunities = Arrays.asList((Type) POISON);

		FIRE.weaknesses = Arrays.asList((Type) GROUND, ROCK, WATER);
		FIRE.resistances = Arrays.asList((Type) BUG, STEEL, FIRE, GRASS, ICE, FAIRY);
		FIRE.immunities = Arrays.asList();

		WATER.weaknesses = Arrays.asList((Type) GRASS, ELECTRIC);
		WATER.resistances = Arrays.asList((Type) STEEL, FIRE, WATER, ICE);
		WATER.immunities = Arrays.asList();

		GRASS.weaknesses = Arrays.asList((Type) FLYING, POISON, BUG, FIRE, ICE);
		GRASS.resistances = Arrays.asList((Type) GROUND, WATER, GRASS, ELECTRIC);
		GRASS.immunities = Arrays.asList();

		ELECTRIC.weaknesses = Arrays.asList((Type) GROUND);
		ELECTRIC.resistances = Arrays.asList((Type) FLYING, STEEL, ELECTRIC);
		ELECTRIC.immunities = Arrays.asList();

		PSYCHIC.weaknesses = Arrays.asList((Type) BUG, GHOST, DARK);
		PSYCHIC.resistances = Arrays.asList((Type) FIGHTING, PSYCHIC);
		PSYCHIC.immunities = Arrays.asList();

		ICE.weaknesses = Arrays.asList((Type) FIGHTING, ROCK, STEEL, FIRE);
		ICE.resistances = Arrays.asList((Type) ICE);
		ICE.immunities = Arrays.asList();

		DRAGON.weaknesses = Arrays.asList((Type) ICE, DRAGON, FAIRY);
		DRAGON.resistances = Arrays.asList((Type) FIRE, WATER, GRASS, ELECTRIC);
		DRAGON.immunities = Arrays.asList();

		DARK.weaknesses = Arrays.asList((Type) FIGHTING, BUG, FAIRY);
		DARK.resistances = Arrays.asList((Type) GHOST, DARK);
		DARK.immunities = Arrays.asList((Type) PSYCHIC);

		FAIRY.weaknesses = Arrays.asList((Type) POISON, STEEL);
		FAIRY.resistances = Arrays.asList((Type) FIGHTING, DARK, BUG);
		FAIRY.immunities = Arrays.asList((Type) DRAGON);
	}

	@Override public List<Type> weaknesses() {
		return weaknesses;
	}

	@Override public List<Type> resistances() {
		return resistances;
	}

	@Override public List<Type> immunities() {
		return immunities;
	}

	@Override public int nameResId() {
		return nameResId;
	}

	@Override public int save() {
		return code;
	}

	@Override public int color() {
		return color;
	}

	@Override public double modifierAgainst(Type type) {
		if(weaknesses.contains(type)) {
			return 2.0;
		} else if (resistances.contains(type)){
			return 0.5;
		} else if (immunities.contains(type)) {
			return 0;
		}
		return 1;
	}

	public static Gen6Type getType(int typeId) {
		switch (typeId) {
			case 0:
				return Gen6Type.NONE;
			case 1:
				return Gen6Type.NORMAL;
			case 2:
				return Gen6Type.FIGHTING;
			case 3:
				return Gen6Type.FLYING;
			case 4:
				return Gen6Type.POISON;
			case 5:
				return Gen6Type.GROUND;
			case 6:
				return Gen6Type.ROCK;
			case 7:
				return Gen6Type.BUG;
			case 8:
				return Gen6Type.GHOST;
			case 9:
				return Gen6Type.STEEL;
			case 10:
				return Gen6Type.FIRE;
			case 11:
				return Gen6Type.WATER;
			case 12:
				return Gen6Type.GRASS;
			case 13:
				return Gen6Type.ELECTRIC;
			case 14:
				return Gen6Type.PSYCHIC;
			case 15:
				return Gen6Type.ICE;
			case 16:
				return Gen6Type.DRAGON;
			case 17:
				return Gen6Type.DARK;
			case 18:
				return Gen6Type.FAIRY;
			default:
				return null;
		}
	}
}
