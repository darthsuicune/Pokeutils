package com.suicune.poketools.app;

import com.suicune.PokeTools.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by denis on 01.01.14.
 */

public enum Type {
    NONE,
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY;

    public List<Type> mWeaknesses;
    public List<Type> mResistances;
    public List<Type> mImmunities;
    public int mType;

    private Type() {
    }

    /**
     * Set all the values
     */
    static {
        NONE.mType = R.string.type_none;
        NONE.mWeaknesses = Arrays.asList();
        NONE.mResistances = Arrays.asList();
        NONE.mImmunities = Arrays.asList();

        NORMAL.mType = R.string.type_normal;
        NORMAL.mWeaknesses = Arrays.asList(FIGHTING);
        NORMAL.mResistances = Arrays.asList();
        NORMAL.mImmunities = Arrays.asList(GHOST);

        FIGHTING.mType = R.string.type_fighting;
        FIGHTING.mWeaknesses = Arrays.asList(FLYING, PSYCHIC, FAIRY);
        FIGHTING.mResistances = Arrays.asList(ROCK, BUG, DARK);
        FIGHTING.mImmunities = Arrays.asList();

        FLYING.mType = R.string.type_flying;
        FLYING.mWeaknesses = Arrays.asList(ROCK, ELECTRIC, ICE);
        FLYING.mResistances = Arrays.asList(FIGHTING, BUG, GRASS);
        FLYING.mImmunities = Arrays.asList(GROUND);

        POISON.mType = R.string.type_poison;
        POISON.mWeaknesses = Arrays.asList(GROUND, PSYCHIC);
        POISON.mResistances = Arrays.asList(FIGHTING, POISON, BUG, GRASS, FAIRY);
        POISON.mImmunities = Arrays.asList();

        GROUND.mType = R.string.type_ground;
        GROUND.mWeaknesses = Arrays.asList(WATER, GRASS, ICE);
        GROUND.mResistances = Arrays.asList(POISON, ROCK);
        GROUND.mImmunities = Arrays.asList(ELECTRIC);

        ROCK.mType = R.string.type_rock;
        ROCK.mWeaknesses = Arrays.asList(FIGHTING, GROUND, STEEL, WATER, GRASS);
        ROCK.mResistances = Arrays.asList(NORMAL, FLYING, POISON, FIRE);
        ROCK.mImmunities = Arrays.asList();

        BUG.mType = R.string.type_bug;
        BUG.mWeaknesses = Arrays.asList(FLYING, ROCK, FIRE);
        BUG.mResistances = Arrays.asList(FIGHTING, GROUND, GRASS);
        BUG.mImmunities = Arrays.asList();

        GHOST.mType = R.string.type_ghost;
        GHOST.mWeaknesses = Arrays.asList(GHOST, DARK);
        GHOST.mResistances = Arrays.asList(POISON, BUG);
        GHOST.mImmunities = Arrays.asList(NORMAL, FIGHTING);

        STEEL.mType = R.string.type_steel;
        STEEL.mWeaknesses = Arrays.asList(FIGHTING, GROUND, FIRE);
        STEEL.mResistances = Arrays.asList(NORMAL, FLYING, ROCK, BUG, STEEL, GRASS, PSYCHIC, ICE,
                DRAGON, FAIRY);
        STEEL.mImmunities = Arrays.asList(POISON);

        FIRE.mType = R.string.type_fire;
        FIRE.mWeaknesses = Arrays.asList(GROUND, ROCK, WATER);
        FIRE.mResistances = Arrays.asList(BUG, STEEL, FIRE, GRASS, ICE, FAIRY);
        FIRE.mImmunities = Arrays.asList();

        WATER.mType = R.string.type_water;
        WATER.mWeaknesses = Arrays.asList(GRASS, ELECTRIC);
        WATER.mResistances = Arrays.asList(STEEL, FIRE, WATER, ICE);
        WATER.mImmunities = Arrays.asList();

        GRASS.mType = R.string.type_grass;
        GRASS.mWeaknesses = Arrays.asList(FLYING, POISON, BUG,  FIRE, ICE);
        GRASS.mResistances = Arrays.asList(GROUND, WATER, GRASS, ELECTRIC);
        GRASS.mImmunities = Arrays.asList();

        ELECTRIC.mType = R.string.type_electric;
        ELECTRIC.mWeaknesses = Arrays.asList(GROUND);
        ELECTRIC.mResistances = Arrays.asList(FLYING, STEEL, ELECTRIC);
        ELECTRIC.mImmunities = Arrays.asList();

        PSYCHIC.mType = R.string.type_psychic;
        PSYCHIC.mWeaknesses = Arrays.asList(BUG, GHOST, DARK);
        PSYCHIC.mResistances = Arrays.asList(FIGHTING, PSYCHIC);
        PSYCHIC.mImmunities = Arrays.asList();

        ICE.mType = R.string.type_ice;
        ICE.mWeaknesses = Arrays.asList(FIGHTING, ROCK, STEEL, FIRE);
        ICE.mResistances = Arrays.asList(ICE);
        ICE.mImmunities = Arrays.asList();

        DRAGON.mType = R.string.type_dragon;
        DRAGON.mWeaknesses = Arrays.asList(ICE, DRAGON, FAIRY);
        DRAGON.mResistances = Arrays.asList(FIRE, WATER, GRASS, ELECTRIC);
        DRAGON.mImmunities = Arrays.asList();

        DARK.mType = R.string.type_dark;
        DARK.mWeaknesses = Arrays.asList(FIGHTING, BUG, FAIRY);
        DARK.mResistances = Arrays.asList(GHOST, DARK);
        DARK.mImmunities = Arrays.asList(PSYCHIC);

        FAIRY.mType = R.string.type_fairy;
        FAIRY.mWeaknesses = Arrays.asList(POISON, STEEL);
        FAIRY.mResistances = Arrays.asList(FIGHTING, DARK, BUG);
        FAIRY.mImmunities = Arrays.asList(DRAGON);
    }

    public static Type getType(int typeId) {
        switch (typeId) {
            case 0:
                return Type.NONE;
            case 1:
                return Type.NORMAL;
            case 2:
                return Type.FIGHTING;
            case 3:
                return Type.FLYING;
            case 4:
                return Type.POISON;
            case 5:
                return Type.GROUND;
            case 6:
                return Type.ROCK;
            case 7:
                return Type.BUG;
            case 8:
                return Type.GHOST;
            case 9:
                return Type.STEEL;
            case 10:
                return Type.FIRE;
            case 11:
                return Type.WATER;
            case 12:
                return Type.GRASS;
            case 13:
                return Type.ELECTRIC;
            case 14:
                return Type.PSYCHIC;
            case 15:
                return Type.ICE;
            case 16:
                return Type.DRAGON;
            case 17:
                return Type.DARK;
            case 18:
                return Type.FAIRY;
            default:
                return null;
        }
    }

    private int setTypeResId() {
        switch (this) {
            case NONE:
                return R.string.type_none;
            default:
                return 0;
        }
    }
}
