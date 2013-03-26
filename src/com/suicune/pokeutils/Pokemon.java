package com.suicune.pokeutils;

import java.util.ArrayList;

import android.database.Cursor;

import com.suicune.pokeutils.database.PokeContract;

public class Pokemon {
	public long mId;
	public String mName;
	public int mNumber;
	public int mForm;
	public int mType1;
	public int mType2;
	public int mBaseHP;
	public int mBaseAtt;
	public int mBaseDef;
	public int mBaseSpAtt;
	public int mBaseSpDef;
	public int mBaseSpeed;
	public int mAbility1;
	public int mAbility2;
	public int mAbilityDw;
	public ArrayList<Attack> mAttacksList;

	public Pokemon(Cursor cursor) {
		if (cursor.moveToFirst()) {
			mId = cursor.getInt(cursor
					.getColumnIndex(PokeContract.PokemonName._ID));
			mName = cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonName.NAME));
			mNumber = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonName.NUMBER)));
			mForm = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonName.FORM)));
			mType1 = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonType1.TYPE)));
			mType2 = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonType2.TYPE)));
			mBaseHP = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_HP)));
			mBaseAtt = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_ATT)));
			mBaseDef = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_DEF)));
			mBaseSpAtt = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPATT)));
			mBaseSpDef = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPDEF)));
			mBaseSpeed = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPEED)));
			mAbility1 = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonAbility1.ABILITY_1)));
			mAbility2 = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonAbility2.ABILITY_2)));
			mAbilityDw = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonAbilityDW.ABILITY_DW)));
		} else {
			return;
		}
		mAttacksList = new ArrayList<Attack>();
	}

	public Pokemon(long id, String name, int number, int norm, int type1,
			int type2, int baseHP, int baseAtt, int baseDef,
			int baseSpAtt, int baseSpDef, int baseSpeed, int ability1,
			int ability2, int abilityDw) {
		mId = id;
		mName = name;
		mForm = norm;
		mType1 = type1;
		mType2 = type2;
		mBaseHP = baseHP;
		mBaseAtt = baseAtt;
		mBaseDef = baseDef;
		mBaseSpAtt = baseSpAtt;
		mBaseSpDef = baseSpDef;
		mBaseSpeed = baseSpeed;
		mAbility1 = ability1;
		mAbility2 = ability2;
		mAbilityDw = abilityDw;
		mAttacksList = new ArrayList<Attack>();
	}

	public void addAttacks(ArrayList<Attack> attacks) {
		mAttacksList = attacks;
	}

	public void addAttack(Attack attack) {
		mAttacksList.add(attack);
	}
}
