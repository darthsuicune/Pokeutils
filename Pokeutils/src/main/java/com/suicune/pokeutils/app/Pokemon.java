package com.suicune.pokeutils.app;

import android.database.Cursor;
import android.os.Bundle;
import com.suicune.pokeutils.database.PokeContract;

import java.util.ArrayList;

public class Pokemon extends IPokemon{

	public ArrayList<Attack> mAttacksList;

	public Pokemon(Cursor cursor) {
        super(cursor);
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
			mAbilities[ABILITY_INDEX_DW_1] = Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbilityDW.ABILITY_DW)));
		} else {
			return;
		}
		mAttacksList = new ArrayList<Attack>();
	}

	public Pokemon(Bundle args) {
        super(args);
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
		mAbilities[ABILITY_INDEX_DW_1] = args
				.getInt(PokeContract.PokemonAbilityDW.ABILITY_DW);
		mAttacksList = new ArrayList<Attack>();
	}

	public void addAttacks(ArrayList<Attack> attacks) {
		mAttacksList = attacks;
	}

	public void addAttack(Attack attack) {
		mAttacksList.add(attack);
	}
}
