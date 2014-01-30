package com.suicune.poketools.app;

import com.suicune.poketools.utils.IvTools;

import java.util.ArrayList;
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
    public int mLevel;
    public Stats mIvs = new Stats(Stats.StatType.IV);
    public Stats mEvs = new Stats(Stats.StatType.EV);
    public Stats mCurrentStats = new Stats(Stats.StatType.VALUE);
    public Attack[] mAttackSet = new Attack[4];
    public Ability mAbility;
    public int mHappiness = 70;

    Gen6Pokemon(int pokedexNumber, int form, int femaleRatio, int maleRatio, Stats baseStats,
                Type type1, Type type2, double height, double weight, String classification,
                int captureRate, int baseEggSteps, Ability ability1, Ability ability2,
                Ability abilityHidden, int experienceGrowth, int baseHappiness, Stats evsEarned,
                EggGroup eggGroup1, EggGroup eggGroup2, boolean isHiddenAbilityAvailable,
                Map<Integer, Attack> levelAttacks, Map<String,Attack> tmAttacks,
                List<Attack> eggMoves, int raceResId, List<Attack> tutorMoves,
                Map<String,Attack> transferAttacks, int level) {

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
        mLevel = level;
    }

    public Gen6Pokemon setIvs(Stats newIvs){
        mIvs = newIvs;
        calculateStats();
        return this;
    }

    public Gen6Pokemon setEvs(Stats newEvs){
        mEvs = newEvs;
        calculateStats();
        return this;
    }

    public Gen6Pokemon setStats(Stats newStats){
        mCurrentStats = newStats;
        return this;
    }

    public Gen6Pokemon setAttack(Attack attack, int position){
        if(position > 0 && position < 3){
            mAttackSet[position] = attack;
        }
        return this;
    }

    public Gen6Pokemon setAbility(Ability ability){
        mAbility = ability;
        return this;
    }

    public Gen6Pokemon setHappiness(int happiness){
        mHappiness = happiness;
        return this;
    }

    public Map<Stats.Stat, ArrayList<Integer>> calculateIvs(){
        return IvTools.getIvs(this);
    }

    private void calculateStats(){
        mCurrentStats = IvTools.calculateStats(this);
    }
}
