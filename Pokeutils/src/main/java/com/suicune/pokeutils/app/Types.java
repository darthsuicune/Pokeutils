package com.suicune.pokeutils.app;

import com.suicune.pokeutils.App;
import com.suicune.pokeutils.R;

import java.util.Arrays;
import java.util.List;


public class Types {

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

    private enum TypeList {
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
    }

    public enum Type {

        NONE(R.string.type_none, null, null, null),
        NORMAL(R.string.type_normal, Arrays.asList(TypeList.FIGHTING),
                Arrays.asList(new TypeList[]{}),
                Arrays.asList(TypeList.GHOST)),
        FIGHTING(R.string.type_fighting, Arrays.asList(TypeList.FLYING, TypeList.PSYCHIC, TypeList.FAIRY),
                Arrays.asList(TypeList.ROCK, TypeList.BUG, TypeList.DARK),
                Arrays.asList(new TypeList[]{})),
        FLYING(R.string.type_flying, Arrays.asList(TypeList.ROCK, TypeList.ELECTRIC, TypeList.ICE),
                Arrays.asList(TypeList.FIGHTING, TypeList.BUG, TypeList.GRASS),
                Arrays.asList(TypeList.GROUND)),
        POISON(R.string.type_poison, Arrays.asList(TypeList.GROUND, TypeList.PSYCHIC),
                Arrays.asList(TypeList.FIGHTING, TypeList.POISON, TypeList.BUG,
                        TypeList.GRASS, TypeList.FAIRY),
                Arrays.asList(new TypeList[]{})),
        GROUND(R.string.type_ground, Arrays.asList(TypeList.WATER, TypeList.GRASS, TypeList.ICE),
                Arrays.asList(TypeList.POISON, TypeList.ROCK),
                Arrays.asList(TypeList.ELECTRIC)),
        ROCK(R.string.type_rock, Arrays.asList(TypeList.FIGHTING, TypeList.GROUND, TypeList.STEEL,
                TypeList.WATER, TypeList.GRASS),
                Arrays.asList(TypeList.NORMAL, TypeList.FLYING, TypeList.POISON,
                        TypeList.FIRE),
                Arrays.asList(new TypeList[]{})),
        BUG(R.string.type_bug, Arrays.asList(TypeList.FLYING, TypeList.ROCK, TypeList.FIRE),
                Arrays.asList(TypeList.FIGHTING, TypeList.GROUND, TypeList.GRASS),
                Arrays.asList(new TypeList[]{})),
        GHOST(R.string.type_ghost, Arrays.asList(TypeList.GHOST, TypeList.DARK),
                Arrays.asList(TypeList.POISON, TypeList.BUG),
                Arrays.asList(TypeList.NORMAL, TypeList.FIGHTING)),
        STEEL(R.string.type_steel, Arrays.asList(TypeList.FIGHTING, TypeList.GROUND, TypeList.FIRE),
                Arrays.asList(TypeList.NORMAL, TypeList.FLYING, TypeList.ROCK,
                        TypeList.BUG, TypeList.STEEL, TypeList.GRASS, TypeList.PSYCHIC,
                        TypeList.ICE, TypeList.DRAGON, TypeList.FAIRY),
                Arrays.asList(TypeList.POISON)),
        FIRE(R.string.type_fire, Arrays.asList(TypeList.GROUND, TypeList.ROCK, TypeList.WATER),
                Arrays.asList(TypeList.BUG, TypeList.STEEL, TypeList.FIRE,
                        TypeList.GRASS, TypeList.ICE, TypeList.FAIRY),
                Arrays.asList(new TypeList[]{})),
        WATER(R.string.type_water, Arrays.asList(TypeList.GRASS, TypeList.ELECTRIC),
                Arrays.asList(TypeList.STEEL, TypeList.FIRE, TypeList.WATER,
                        TypeList.ICE),
                Arrays.asList(new TypeList[]{})),
        GRASS(R.string.type_grass, Arrays.asList(TypeList.FLYING, TypeList.POISON, TypeList.BUG,
                TypeList.FIRE, TypeList.ICE),
                Arrays.asList(TypeList.GROUND, TypeList.WATER, TypeList.GRASS,
                        TypeList.ELECTRIC),
                Arrays.asList(new TypeList[]{})),
        ELECTRIC(R.string.type_electric, Arrays.asList(TypeList.GROUND),
                Arrays.asList(TypeList.FLYING, TypeList.STEEL, TypeList.ELECTRIC),
                Arrays.asList(new TypeList[]{})),
        PSYCHIC(R.string.type_psychic, Arrays.asList(TypeList.BUG, TypeList.GHOST, TypeList.DARK),
                Arrays.asList(TypeList.FIGHTING, TypeList.PSYCHIC),
                Arrays.asList(new TypeList[]{})),
        ICE(R.string.type_ice, Arrays.asList(TypeList.FIGHTING, TypeList.ROCK, TypeList.STEEL,
                TypeList.FIRE),
                Arrays.asList(TypeList.ICE),
                Arrays.asList(new TypeList[]{})),
        DRAGON(R.string.type_dragon, Arrays.asList(TypeList.ICE, TypeList.DRAGON, TypeList.FAIRY),
                Arrays.asList(TypeList.FIRE, TypeList.WATER, TypeList.GRASS,
                        TypeList.ELECTRIC),
                Arrays.asList(new TypeList[]{})),
        DARK(R.string.type_dark, Arrays.asList(TypeList.FIGHTING, TypeList.BUG, TypeList.FAIRY),
                Arrays.asList(TypeList.GHOST, TypeList.DARK),
                Arrays.asList(TypeList.PSYCHIC)),
        FAIRY(R.string.type_fairy, Arrays.asList(TypeList.POISON, TypeList.STEEL),
                Arrays.asList(TypeList.FIGHTING, TypeList.DARK, TypeList.BUG),
                Arrays.asList(TypeList.DRAGON));

        @Override
        public String toString(){
            return App.getResourceString(mName);
        }

        public final int mName;
        public final List<TypeList> mWeakness;
        public final List<TypeList> mResistances;
        public final List<TypeList> mInmunities;

        private Type(int name, List<TypeList> weakness, List<TypeList> resistances, List<TypeList> inmunities) {
            mName = name;
            mWeakness = weakness;
            mResistances = resistances;
            mInmunities = inmunities;
        }
    }
}