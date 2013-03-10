package com.suicune.pokeutils;

import java.util.ArrayList;

import com.suicune.pokeutils.database.PokeContract;

import android.database.Cursor;

public class Pokemon {
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
	public int mAbilityDW;
	public ArrayList<Attack> mAttacksList;

	public Pokemon(Cursor cursor) {
		if (cursor.moveToFirst()) {
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
			mAbilityDW = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonAbilityDW.ABILITY_DW)));
		} else {
			return;
		}
		mAttacksList = new ArrayList<Attack>();
	}

	public Pokemon(String mName, int mNumber, int mForm, int mType1,
			int mType2, int mBaseHP, int mBaseAtt, int mBaseDef,
			int mBaseSpAtt, int mBaseSpDef, int mBaseSpeed, int mAbility1,
			int mAbility2, int mAbilityDW) {
		this.mName = mName;
		this.mForm = mForm;
		this.mType1 = mType1;
		this.mType2 = mType2;
		this.mBaseHP = mBaseHP;
		this.mBaseAtt = mBaseAtt;
		this.mBaseDef = mBaseDef;
		this.mBaseSpAtt = mBaseSpAtt;
		this.mBaseSpDef = mBaseSpDef;
		this.mBaseSpeed = mBaseSpeed;
		this.mAbility1 = mAbility1;
		this.mAbility2 = mAbility2;
		this.mAbilityDW = mAbilityDW;
		mAttacksList = new ArrayList<Attack>();
	}
	
	public void addAttacks(ArrayList<Attack> attacks){
		mAttacksList = attacks;
	}
	
	public void addAttack(Attack attack){
		mAttacksList.add(attack);
	}
}
