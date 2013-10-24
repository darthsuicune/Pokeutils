package com.suicune.pokeutils.app;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.suicune.pokeutils.R;
import com.suicune.pokeutils.database.PokeContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokemon{
    private static final String TYPE = "array";
    private static final String POKEMON_FORM_PATTERN = "pokemon_form_";
    private static final String POKEMON_TYPES_PATTERN = "pokemon_types_";
    private static final String POKEMON_BASE_STATS_PATTERN = "pokemon_base_stats_";
    private static final String POKEMON_ABILITIES_PATTERN = "pokemon_abilities_";
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

    final public long mId;
    final public String mName;
    final public int mNumber;
    final public int mForm;
    final public Types.Type mType1;
    final public Types.Type mType2;
    final public int[] mBaseStats;

    final public List<Ability> mAbilities;
	public ArrayList<Attack> mAttacksList;

    /**
     * WARNING:
     * Creation of the pokemon involves reflection. This might (And probably should) be modified
     * by future self, but at this point in time creating the huge database from scratch is a pain
     *
     * @param id
     * @param context
     */
    public Pokemon(Context context, int id){
        mId = id;
        Resources res = context.getResources();
        mName = res.getStringArray(R.array.pokemon_names)[id];
        int formsId = res.getIdentifier(POKEMON_FORM_PATTERN + id, context.getPackageName(), TYPE);
        int typesId = res.getIdentifier(POKEMON_FORM_PATTERN + id, context.getPackageName(), TYPE);
        int statsId = res.getIdentifier(POKEMON_FORM_PATTERN + id, context.getPackageName(), TYPE);
        int abilitiesId = res.getIdentifier(POKEMON_FORM_PATTERN + id, context.getPackageName(), TYPE);
        mNumber = res.getIntArray(formsId)[0];
        mForm = res.getIntArray(formsId)[1];
        mType1 = Types.getType(res.getIntArray(typesId)[0]);
        mType2 = Types.getType(res.getIntArray(typesId)[1]);
        mBaseStats = res.getIntArray(statsId);
        Ability ability1 = new Ability(context, res.getIntArray(abilitiesId)[0]);
        Ability ability2 = new Ability(context, res.getIntArray(abilitiesId)[1]);
        Ability abilityDW = new Ability(context, res.getIntArray(abilitiesId)[2]);
        mAbilities = Arrays.asList(ability1, ability2, abilityDW);

    }

    public void saveStatus(Bundle status) {
        status.putLong(PokeContract.PokemonBaseStats._ID, mId);
        status.putString(PokeContract.PokemonName.NAME, mName);
        status.putInt(PokeContract.PokemonBaseStats.NUMBER, mNumber);
        status.putInt(PokeContract.PokemonBaseStats.FORM, mForm);
//        status.putInt(PokeContract.PokemonType1.TYPE, mType1);
//        status.putInt(PokeContract.PokemonType2.TYPE, mType2);
        status.putIntArray(PokeContract.PokemonBaseStats.BASE_HP, mBaseStats);
        status.putInt(PokeContract.PokemonAbility1.ABILITY_1,
                mAbilities.get(ABILITY_INDEX_1).mId);
        status.putInt(PokeContract.PokemonAbility2.ABILITY_2,
                mAbilities.get(ABILITY_INDEX_2).mId);
        status.putInt(PokeContract.PokemonAbilityDW.ABILITY_DW,
                mAbilities.get(ABILITY_INDEX_DW).mId);
    }
}
