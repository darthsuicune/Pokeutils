package com.suicune.poketools.model.gen6;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Type;

import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01.01.14.
 */

public enum Gen6Type implements Type {
	NONE(0, R.string.type_none),
	NORMAL(1, R.string.type_normal),
	FIGHTING(2, R.string.type_fighting),
	FLYING(3, R.string.type_flying),
	POISON(4, R.string.type_poison),
	GROUND(5, R.string.type_ground),
	ROCK(6, R.string.type_rock),
	BUG(7, R.string.type_bug),
	GHOST(8, R.string.type_ghost),
	STEEL(9, R.string.type_steel),
	FIRE(10, R.string.type_fire),
	WATER(11, R.string.type_water),
	GRASS(12, R.string.type_grass),
	ELECTRIC(13, R.string.type_electric),
	PSYCHIC(14, R.string.type_psychic),
	ICE(15, R.string.type_ice),
	DRAGON(16, R.string.type_dragon),
	DARK(17, R.string.type_dark),
	FAIRY(18, R.string.type_fairy);

	private Gen6Type(int code, int nameResId) {
		mCode = code;
		mNameResId = nameResId;
	}

	public List<Type> mWeaknesses;
	public List<Type> mResistances;
	public List<Type> mImmunities;
	public int mNameResId;
	public int mCode;

	/**
	 * Set all the values
	 */
	static {
		NONE.mWeaknesses = Arrays.asList();
		NONE.mResistances = Arrays.asList();
		NONE.mImmunities = Arrays.asList();

		NORMAL.mWeaknesses = Arrays.asList((Type) FIGHTING);
		NORMAL.mResistances = Arrays.asList();
		NORMAL.mImmunities = Arrays.asList((Type) GHOST);

		FIGHTING.mWeaknesses = Arrays.asList((Type) FLYING, PSYCHIC, FAIRY);
		FIGHTING.mResistances = Arrays.asList((Type) ROCK, BUG, DARK);
		FIGHTING.mImmunities = Arrays.asList();

		FLYING.mWeaknesses = Arrays.asList((Type) ROCK, ELECTRIC, ICE);
		FLYING.mResistances = Arrays.asList((Type) FIGHTING, BUG, GRASS);
		FLYING.mImmunities = Arrays.asList((Type) GROUND);

		POISON.mWeaknesses = Arrays.asList((Type) GROUND, PSYCHIC);
		POISON.mResistances = Arrays.asList((Type) FIGHTING, POISON, BUG, GRASS, FAIRY);
		POISON.mImmunities = Arrays.asList();

		GROUND.mWeaknesses = Arrays.asList((Type) WATER, GRASS, ICE);
		GROUND.mResistances = Arrays.asList((Type) POISON, ROCK);
		GROUND.mImmunities = Arrays.asList((Type) ELECTRIC);

		ROCK.mWeaknesses = Arrays.asList((Type) FIGHTING, GROUND, STEEL, WATER, GRASS);
		ROCK.mResistances = Arrays.asList((Type) NORMAL, FLYING, POISON, FIRE);
		ROCK.mImmunities = Arrays.asList();

		BUG.mWeaknesses = Arrays.asList((Type) FLYING, ROCK, FIRE);
		BUG.mResistances = Arrays.asList((Type) FIGHTING, GROUND, GRASS);
		BUG.mImmunities = Arrays.asList();

		GHOST.mWeaknesses = Arrays.asList((Type) GHOST, DARK);
		GHOST.mResistances = Arrays.asList((Type) POISON, BUG);
		GHOST.mImmunities = Arrays.asList((Type) NORMAL, FIGHTING);

		STEEL.mWeaknesses = Arrays.asList((Type) FIGHTING, GROUND, FIRE);
		STEEL.mResistances =
				Arrays.asList((Type) NORMAL, FLYING, ROCK, BUG, STEEL, GRASS, PSYCHIC, ICE, DRAGON,
						FAIRY);
		STEEL.mImmunities = Arrays.asList((Type) POISON);

		FIRE.mWeaknesses = Arrays.asList((Type) GROUND, ROCK, WATER);
		FIRE.mResistances = Arrays.asList((Type) BUG, STEEL, FIRE, GRASS, ICE, FAIRY);
		FIRE.mImmunities = Arrays.asList();

		WATER.mWeaknesses = Arrays.asList((Type) GRASS, ELECTRIC);
		WATER.mResistances = Arrays.asList((Type) STEEL, FIRE, WATER, ICE);
		WATER.mImmunities = Arrays.asList();

		GRASS.mWeaknesses = Arrays.asList((Type) FLYING, POISON, BUG, FIRE, ICE);
		GRASS.mResistances = Arrays.asList((Type) GROUND, WATER, GRASS, ELECTRIC);
		GRASS.mImmunities = Arrays.asList();

		ELECTRIC.mWeaknesses = Arrays.asList((Type) GROUND);
		ELECTRIC.mResistances = Arrays.asList((Type) FLYING, STEEL, ELECTRIC);
		ELECTRIC.mImmunities = Arrays.asList();

		PSYCHIC.mWeaknesses = Arrays.asList((Type) BUG, GHOST, DARK);
		PSYCHIC.mResistances = Arrays.asList((Type) FIGHTING, PSYCHIC);
		PSYCHIC.mImmunities = Arrays.asList();

		ICE.mWeaknesses = Arrays.asList((Type) FIGHTING, ROCK, STEEL, FIRE);
		ICE.mResistances = Arrays.asList((Type) ICE);
		ICE.mImmunities = Arrays.asList();

		DRAGON.mWeaknesses = Arrays.asList((Type) ICE, DRAGON, FAIRY);
		DRAGON.mResistances = Arrays.asList((Type) FIRE, WATER, GRASS, ELECTRIC);
		DRAGON.mImmunities = Arrays.asList();

		DARK.mWeaknesses = Arrays.asList((Type) FIGHTING, BUG, FAIRY);
		DARK.mResistances = Arrays.asList((Type) GHOST, DARK);
		DARK.mImmunities = Arrays.asList((Type) PSYCHIC);

		FAIRY.mWeaknesses = Arrays.asList((Type) POISON, STEEL);
		FAIRY.mResistances = Arrays.asList((Type) FIGHTING, DARK, BUG);
		FAIRY.mImmunities = Arrays.asList((Type) DRAGON);
	}

	@Override public List<Type> weaknesses() {
		return mWeaknesses;
	}

	@Override public List<Type> resistances() {
		return mResistances;
	}

	@Override public List<Type> immunities() {
		return mImmunities;
	}

	@Override public int nameResId() {
		return mNameResId;
	}

	@Override public int save() {
		return mCode;
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
