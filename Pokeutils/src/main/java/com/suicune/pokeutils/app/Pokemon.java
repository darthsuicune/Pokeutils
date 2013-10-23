package com.suicune.pokeutils.app;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import com.suicune.pokeutils.database.PokeContract;

import java.util.ArrayList;

public class Pokemon{
    //Indexes for tables
    public static final int ABILITY_INDEX_1 = 0;
    public static final int ABILITY_INDEX_2 = 1;
    public static final int ABILITY_INDEX_DW = 2;
    public static final int STAT_INDEX_HP = 0;
    public static final int STAT_INDEX_ATT = 1;
    public static final int STAT_INDEX_DEF = 2;
    public static final int STAT_INDEX_SP_ATT = 3;
    public static final int STAT_INDEX_SP_DEF = 4;
    public static final int STAT_INDEX_SPEED = 5;

    public long mId;
    public String mName;
    public int mNumber;
    public int mForm;
    public int mType1;
    public int mType2;
    public int[] mBaseStats;

    public ArrayList<Ability> mAbilities;
	public ArrayList<Attack> mAttacksList;

    public Pokemon(int id, Context context){

    }

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
			mBaseStats[STAT_INDEX_HP] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_HP)));
			mBaseStats[STAT_INDEX_ATT] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_ATT)));
			mBaseStats[STAT_INDEX_DEF] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_DEF)));
			mBaseStats[STAT_INDEX_SP_ATT] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SP_ATT)));
			mBaseStats[STAT_INDEX_SP_DEF] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SP_DEF)));
			mBaseStats[STAT_INDEX_SPEED] = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.PokemonBaseStats.BASE_SPEED)));
			mAbilities.add(ABILITY_INDEX_1, new Ability(Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbility1.ABILITY_1)))));
			mAbilities.add(ABILITY_INDEX_2, new Ability(Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbility2.ABILITY_2)))));
			mAbilities.add(ABILITY_INDEX_DW, new Ability(Integer
					.parseInt(cursor.getString(cursor
							.getColumnIndex(PokeContract.PokemonAbilityDW.ABILITY_DW)))));
		} else {
			return;
		}
		mAttacksList = new ArrayList<Attack>();
	}

	public Pokemon(Bundle args) {
		mId = args.getLong(PokeContract.PokemonBaseStats._ID);
		mName = args.getString(PokeContract.PokemonName.NAME);
		mNumber = args.getInt(PokeContract.PokemonBaseStats.NUMBER);
		mForm = args.getInt(PokeContract.PokemonBaseStats.FORM);
		mType1 = args.getInt(PokeContract.PokemonType1.TYPE);
		mType2 = args.getInt(PokeContract.PokemonType2.TYPE);
		mBaseStats = args.getIntArray(PokeContract.PokemonBaseStats.BASE_HP);
		mAbilities.add(ABILITY_INDEX_1, new Ability(args
				.getInt(PokeContract.PokemonAbility1.ABILITY_1)));
        mAbilities.add(ABILITY_INDEX_2, new Ability(args
				.getInt(PokeContract.PokemonAbility2.ABILITY_2)));
		mAbilities.add(ABILITY_INDEX_DW, new Ability(args
				.getInt(PokeContract.PokemonAbilityDW.ABILITY_DW)));
		mAttacksList = new ArrayList<Attack>();
	}

    public void addAttacks(ArrayList<Attack> Attacks) {
		mAttacksList = Attacks;
	}

	public void addAttack(Attack Attack) {
		mAttacksList.add(Attack);
	}

    public void saveStatus(Bundle status) {
        status.putLong(PokeContract.PokemonBaseStats._ID, mId);
        status.putString(PokeContract.PokemonName.NAME, mName);
        status.putInt(PokeContract.PokemonBaseStats.NUMBER, mNumber);
        status.putInt(PokeContract.PokemonBaseStats.FORM, mForm);
        status.putInt(PokeContract.PokemonType1.TYPE, mType1);
        status.putInt(PokeContract.PokemonType2.TYPE, mType2);
        status.putIntArray(PokeContract.PokemonBaseStats.BASE_HP, mBaseStats);
        status.putInt(PokeContract.PokemonAbility1.ABILITY_1,
                mAbilities.get(ABILITY_INDEX_1).mId);
        status.putInt(PokeContract.PokemonAbility2.ABILITY_2,
                mAbilities.get(ABILITY_INDEX_2).mId);
        status.putInt(PokeContract.PokemonAbilityDW.ABILITY_DW,
                mAbilities.get(ABILITY_INDEX_DW).mId);
    }
}
