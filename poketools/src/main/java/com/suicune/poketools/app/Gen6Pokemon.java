package com.suicune.poketools.app;

import java.util.List;
import java.util.Map;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Pokemon {
    /**
     * Immutable properties
     */
    public final int mRaceResId;
    public final int mPokedexNumber;
    public final int mForm;
    public final int mFemaleRatio;
    public final int mMaleRatio;
    public final Stats mBaseStats;
    public final Type mType1;
    public final Type mType2;
    public final double mHeight;
    public final double mWeight;
    public final String mClassification;
    public final int mCaptureRate;
    public final int mBaseEggSteps;
    public final Ability mAbility1;
    public final Ability mAbility2;
    public final Ability mAbilityHidden;
    public final boolean isHiddenAbilityAvailable;
    public final int mExperienceGrowth;
    public final int mBaseHappiness;
    public final Stats mEvsEarned;
    public final EggGroup mEggGroup1;
    public final EggGroup mEggGroup2;
    public final Map<Integer, Attack> mLevelAttacks;
    public final Map<String, Attack> mTmAttacks;
    public final List<Attack> mEggMoves;
    public final List<Attack> mTutorMoves;
    public final Map<String, Attack> mTransferAttacks;

    /**
     * Mutable properties
     */
    public Stats mIvs;
    public Stats mEvs;
    public Stats mCurrentStats;
    public Attack[] mAttackSet = new Attack[4];
    public Ability mAbility;
    public int mHappiness;

    Gen6Pokemon(int pokedexNumber, int form, int femaleRatio, int maleRatio, Stats baseStats,
                Type type1, Type type2, double height, double weight, String classification,
                int captureRate, int baseEggSteps, Ability ability1, Ability ability2,
                Ability abilityHidden, int experienceGrowth, int baseHappiness, Stats evsEarned,
                EggGroup eggGroup1, EggGroup eggGroup2, boolean isHiddenAbilityAvailable,
                Map<Integer, Attack> levelAttacks, Map<String,Attack> tmAttacks,
                List<Attack> eggMoves, int raceResId, List<Attack> tutorMoves,
                Map<String,Attack> transferAttacks) {

        mRaceResId = raceResId;
        mPokedexNumber = pokedexNumber;
        mForm = form;
        mFemaleRatio = femaleRatio;
        mMaleRatio = maleRatio;
        mBaseStats = baseStats;
        mType1 = type1;
        mType2 = type2;
        mHeight = height;
        mWeight = weight;
        mClassification = classification;
        mCaptureRate = captureRate;
        mBaseEggSteps = baseEggSteps;
        mAbility1 = ability1;
        mAbility2 = ability2;
        mAbilityHidden = abilityHidden;
        mExperienceGrowth = experienceGrowth;
        mBaseHappiness = baseHappiness;
        mEvsEarned = evsEarned;
        mEggGroup1 = eggGroup1;
        mEggGroup2 = eggGroup2;
        this.isHiddenAbilityAvailable = isHiddenAbilityAvailable;
        mLevelAttacks = levelAttacks;
        mTmAttacks = tmAttacks;
        mEggMoves = eggMoves;
        mTutorMoves = tutorMoves;
        mTransferAttacks = transferAttacks;
    }
}
