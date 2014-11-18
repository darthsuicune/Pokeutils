package com.suicune.poketools.model;

/**
 * Created by denis on 01.01.14.
 */
public enum EggGroup {
	BUG,
	DITTO,
	DRAGON,
	FAIRY,
	FIELD,
	FLYING,
	PLANT,
	HUMANLIKE,
	AMORPHOUS,
	MINERAL,
	MONSTER,
	WATER_1,
	WATER_2,
	WATER_3,
	NONE;

	public static EggGroup fromValue(int value) {
		switch (value) {
			case 2:
				return MONSTER;
			case 3:
				return BUG;
			case 4:
				return FLYING;
			case 5:
				return FIELD;
			case 6:
				return FAIRY;
			case 7:
				return PLANT;
			case 8:
				return WATER_1;
			case 9:
				return HUMANLIKE;
			case 10:
				return WATER_3;
			case 11:
				return MINERAL;
			case 12:
				return AMORPHOUS;
			case 13:
				return WATER_2;
			case 14:
				return DITTO;
			case 15:
				return DRAGON;
			default:
				return NONE;
		}
	}
}
