package com.suicune.poketools.model.gen6;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.utils.IvTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 01.01.14.
 */
public class Gen6Pokemon extends Pokemon {
	public static final String ARG_DEX_NUMBER = "number";
	public static final String ARG_FORM = "form";
	public static final String ARG_ABILITIES = "abilities";
	public static final String ARG_TYPES = "types";
	public static final String ARG_BASE_STATS = "baseStats";


	/**
	 * Immutable properties
	 */
	public final int mPokedexNumber;
	public final int mForm;
	public final int mFormCount;
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
	public final Ability mAbility1;
	public final Ability mAbility2;
	public final Ability mAbilityHidden;
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
	public Type mAdditionalType;
	public Nature mNature;

	public Gen6Pokemon(int level, JSONObject data, Stats stats, Type[] types,
					   Ability[] abilities, int formCount) throws JSONException {
		mLevel = level;
		mPokedexNumber = data.getInt(ARG_DEX_NUMBER);
		mForm = data.getInt(ARG_FORM);
		mFormCount = formCount;
		mFemaleRatio = 0;
		mMaleRatio = 0;
		mStats = stats;
		mType1 = types[0];
		mType2 = types[1];
		mHeight = 0;
		mWeight = 0;
		mClassification = "";
		mCaptureRate = 0;
		mBaseEggSteps = 0;
		mAbility1 = abilities[0];
		mAbility2 = abilities[1];
		mAbilityHidden = abilities[2];
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
