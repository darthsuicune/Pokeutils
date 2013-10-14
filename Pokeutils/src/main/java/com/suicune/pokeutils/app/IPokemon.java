package com.suicune.pokeutils.app;

import android.database.Cursor;
import android.os.Bundle;
import com.suicune.pokeutils.database.PokeContract;

/**
 * Created by lapuente on 14.10.13.
 */
public abstract class IPokemon {
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

    public static final int ABILITY_INDEX_1 = 0;
    public static final int ABILITY_INDEX_2 = 1;
    public static final int ABILITY_INDEX_DW_1 = 2;
    public static final int ABILITY_INDEX_DW_2 = 3;

    public int[] mAbilities = new int[4];

    protected IPokemon(Cursor cursor){

    }
    protected IPokemon(Bundle args){

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
                mAbilities[ABILITY_INDEX_DW_1]);
    }
}
