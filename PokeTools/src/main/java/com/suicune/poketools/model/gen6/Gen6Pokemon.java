package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.factories.StatsFactory;
import com.suicune.poketools.utils.IvTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Pokemon implements Pokemon {
	/**
	 * Immutable properties
	 */
	public final int mRaceResId;
	public final int mPokedexNumber;
	public final int mForm;
	public final double mFemaleRatio;
	public final double mMaleRatio;
	public final Stats mStats;
	public final Type mType1;
	public final Type mType2;
	public final double mHeight;
	public final double mWeight;
	public final String mClassification;
	public final int mCaptureRate;
	public final int mBaseEggSteps;
	public final Gen6Ability mAbility1;
	public final Gen6Ability mAbility2;
	public final Gen6Ability mAbilityHidden;
	public final boolean isHiddenAbilityAvailable;
	public final int mExperienceGrowth;
	public final int mBaseHappiness;
	public final Map<Gen6Stats.Stat, Integer> mEvsEarned;
	public final EggGroup mEggGroup1;
	public final EggGroup mEggGroup2;
	public final Map<Integer, Attack> mLevelAttacks;
	public final Map<String, Attack> mTmAttacks;
	public final List<Attack> mEggMoves;
	public final Map<String, Attack> mTutorMoves;
	public final Map<String, Attack> mTransferAttacks;

	/**
	 * Mutable properties
	 */
	public int mLevel;
	public List<Attack> mAttackSet;
	public Gen6Ability mAbility;
	public int mHappiness = 70;

	public Gen6Pokemon(int resId, int level) {
		mLevel = level;
		mRaceResId = resId;
		mPokedexNumber = 0;
		mForm = 0;
		mFemaleRatio = 0;
		mMaleRatio = 0;
		mStats = StatsFactory.createStats(6, level);
		mType1 = null;
		mType2 = null;
		mHeight = 0;
		mWeight = 0;
		mClassification = "";
		mCaptureRate = 0;
		mBaseEggSteps = 0;
		mAbility1 = null;
		mAbility2 = null;
		mAbilityHidden = null;
		mExperienceGrowth = 0;
		mBaseHappiness = 0;
		mEvsEarned = new HashMap<>();
		mEggGroup1 = null;
		mEggGroup2 = null;
		this.isHiddenAbilityAvailable = false;
		mLevelAttacks = new HashMap<>();
		mTmAttacks = new HashMap<>();
		mEggMoves = new ArrayList<>();
		mTutorMoves = new HashMap<>();
		mTransferAttacks = new HashMap<>();
		mAttackSet = new ArrayList<>();
	}

	public Gen6Pokemon(int raceResId, int pokedexNumber, int form, double femaleRatio, double maleRatio,
				Stats baseStats, Type type1, Type type2, double height, double weight,
				String classification, int captureRate, int baseEggSteps, Gen6Ability ability1,
				Gen6Ability ability2, Gen6Ability abilityHidden, int experienceGrowth,
				int baseHappiness, Map<Gen6Stats.Stat, Integer> evsEarned, EggGroup eggGroup1,
				EggGroup eggGroup2, boolean isHiddenAbilityAvailable,
				Map<Integer, Attack> levelAttacks, Map<String, Attack> tmAttacks,
				List<Attack> eggMoves, Map<String, Attack> tutorMoves,
				Map<String, Attack> transferAttacks, int level) {

		mRaceResId = raceResId;
		mPokedexNumber = pokedexNumber;
		mForm = form;
		mFemaleRatio = femaleRatio;
		mMaleRatio = maleRatio;
		mStats = baseStats;
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
		mAttackSet = new ArrayList<>();
	}

	public Gen6Pokemon setIvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		mStats.setIvs(hp, attack, defense, spattack, spdefense, speed);
		calculateStats();
		return this;
	}

	public Gen6Pokemon setEvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		mStats.setEvs(hp, attack, defense, spattack, spdefense, speed);
		calculateStats();
		return this;
	}

	public Gen6Pokemon setStats(int hp, int attack, int defense, int spattack, int spdefense,
								int speed) {
		mStats.setCurrentValues(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	public Gen6Pokemon setAttack(Attack attack, int position) {
		if (position > 0 && position < 3) {
			mAttackSet.add(position, attack);
		}
		return this;
	}

	public Gen6Pokemon setAbility(Gen6Ability ability) {
		mAbility = ability;
		return this;
	}

	public Gen6Pokemon setHappiness(int happiness) {
		mHappiness = happiness;
		return this;
	}

	public Stats calculateIvs() {
		return IvTools.getIvs(this);
	}

	private void calculateStats() {
		mStats.updateWith(IvTools.calculateStats(this));
	}

	@Override public int raceId() {
		return mRaceResId;
	}

	@Override public int dexNumber() {
		return mPokedexNumber;
	}

	@Override public int formNumber() {
		return mForm;
	}

	@Override public double femaleRatio() {
		return mFemaleRatio;
	}

	@Override public double maleRatio() {
		return mMaleRatio;
	}

	@Override public Stats stats() {
		return mStats;
	}

	@Override public List<Type> types() {
		List<Type> types = new ArrayList<>();
		types.add(mType1);
		types.add(mType2);
		return types;
	}

	@Override public Ability ability1() {
		return mAbility1;
	}

	@Override public Ability ability2() {
		return mAbility2;
	}

	@Override public Ability abilityHidden() {
		return mAbilityHidden;
	}

	@Override public boolean isHiddenAbilityAvailable() {
		return isHiddenAbilityAvailable;
	}

	@Override public Map<Integer, Attack> levelAttacks() {
		return mLevelAttacks;
	}

	@Override public Map<String, Attack> tmAttacks() {
		return mTmAttacks;
	}

	@Override public List<Attack> eggAttacks() {
		return mEggMoves;
	}

	@Override public Map<String, Attack> tutorAttacks() {
		return mTutorMoves;
	}

	@Override public Map<String, Attack> transferAttacks() {
		return mTransferAttacks;
	}

	@Override public int level() {
		return mLevel;
	}

	@Override public List<Attack> currentAttacks() {
		return mAttackSet;
	}

	@Override public Ability currentAbility() {
		return mAbility;
	}
}
