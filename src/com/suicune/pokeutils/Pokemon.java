package com.suicune.pokeutils;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;

import com.suicune.pokeutils.database.PokeContract;

public class Pokemon {
	public static final int ABILITY_INDEX_1 = 0;
	public static final int ABILITY_INDEX_2 = 1;
	public static final int ABILITY_INDEX_DW = 2;

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
	public int[] mAbilities = new int[3];
	public ArrayList<Attack> mAttacksList;
	
	public double[] mDefenseModifiers = new double[Types.COUNT];

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
			mAbilities[ABILITY_INDEX_1] = Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbility1.ABILITY_1)));
			mAbilities[ABILITY_INDEX_2] = Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbility2.ABILITY_2)));
			mAbilities[ABILITY_INDEX_DW] = Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbilityDW.ABILITY_DW)));
		} else {
			return;
		}
		mAttacksList = new ArrayList<Attack>();
		setDefenseModifiers();
	}

	public Pokemon(Bundle args) {
		mId = args.getLong(PokeContract.PokemonBaseStats._ID);
		mName = args.getString(PokeContract.PokemonName.NAME);
		mNumber = args.getInt(PokeContract.PokemonBaseStats.NUMBER);
		mForm = args.getInt(PokeContract.PokemonBaseStats.FORM);
		mType1 = args.getInt(PokeContract.PokemonType1.TYPE);
		mType2 = args.getInt(PokeContract.PokemonType2.TYPE);
		mBaseHP = args.getInt(PokeContract.PokemonBaseStats.BASE_HP);
		mBaseAtt = args.getInt(PokeContract.PokemonBaseStats.BASE_ATT);
		mBaseDef = args.getInt(PokeContract.PokemonBaseStats.BASE_DEF);
		mBaseSpAtt = args.getInt(PokeContract.PokemonBaseStats.BASE_SPATT);
		mBaseSpDef = args.getInt(PokeContract.PokemonBaseStats.BASE_SPDEF);
		mBaseSpeed = args.getInt(PokeContract.PokemonBaseStats.BASE_SPEED);
		mAbilities[ABILITY_INDEX_1] = args
				.getInt(PokeContract.PokemonAbility1.ABILITY_1);
		mAbilities[ABILITY_INDEX_2] = args
				.getInt(PokeContract.PokemonAbility2.ABILITY_2);
		mAbilities[ABILITY_INDEX_DW] = args
				.getInt(PokeContract.PokemonAbilityDW.ABILITY_DW);
		mAttacksList = new ArrayList<Attack>();
		setDefenseModifiers();
	}

	public void addAttacks(ArrayList<Attack> attacks) {
		mAttacksList = attacks;
	}

	public void addAttack(Attack attack) {
		mAttacksList.add(attack);
	}

	public void saveStatus(Bundle status) {
		status.putLong(PokeContract.PokemonBaseStats._ID, mId);
		status.putString(PokeContract.PokemonName.NAME, mName);
		status.putInt(PokeContract.PokemonBaseStats.NUMBER, mNumber);
		status.putInt(PokeContract.PokemonBaseStats.FORM, mForm);
		status.putInt(PokeContract.PokemonType1.TYPE, mType1);
		status.putInt(PokeContract.PokemonType2.TYPE, mType2);
		status.putInt(PokeContract.PokemonBaseStats.BASE_HP, mBaseHP);
		status.putInt(PokeContract.PokemonBaseStats.BASE_ATT, mBaseAtt);
		status.putInt(PokeContract.PokemonBaseStats.BASE_DEF, mBaseDef);
		status.putInt(PokeContract.PokemonBaseStats.BASE_SPATT, mBaseSpAtt);
		status.putInt(PokeContract.PokemonBaseStats.BASE_SPDEF, mBaseSpDef);
		status.putInt(PokeContract.PokemonBaseStats.BASE_SPEED, mBaseSpeed);
		status.putInt(PokeContract.PokemonAbility1.ABILITY_1,
				mAbilities[ABILITY_INDEX_1]);
		status.putInt(PokeContract.PokemonAbility2.ABILITY_2,
				mAbilities[ABILITY_INDEX_2]);
		status.putInt(PokeContract.PokemonAbilityDW.ABILITY_DW,
				mAbilities[ABILITY_INDEX_DW]);
	}

	public void setDefenseModifiers() {
		for(int i = 1; i <= Types.DARK; i++){
			mDefenseModifiers[i] = getDefenseModifiers(i);
		}
	}

	private double getDefenseModifiers(int i) {
		
		return 0;
	}
}
