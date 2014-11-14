package com.suicune.poketools.model.gen6;

import android.os.Bundle;

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
	/**
	 * Immutable properties
	 */
	public final int mPokedexNumber;
	public final int mForm;
	public final int mFormCount;
	public final double mFemaleRatio;
	public final double mMaleRatio;
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
	public final List<Attack> mAttackList;

	/**
	 * Subproperties are modifiable
	 */
	public final Stats mStats;

	/**
	 * Mutable properties
	 */
	public int mLevel;
	public List<Attack> mAttackSet;
	public Ability mAbility;
	public int mHappiness = 70;
	public Type mAdditionalType;
	public Nature mNature;
	public String mNickname;
	public String mName;

	public Gen6Pokemon(int level, JSONObject data, Stats stats, Type[] types, Ability[] abilities,
					   int formCount, String name, List<Attack> attackList) throws JSONException {
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
		mName = name;
		mNickname = name;
		mAttackList = attackList;
	}

	public Gen6Pokemon(Bundle bundle, Stats stats, Type type1, Type type2, Ability ability1,
					   Ability ability2, Ability hiddenAbility, List<Attack> attackList) {
		mLevel = bundle.getInt(ARG_LEVEL);
		mPokedexNumber = bundle.getInt(ARG_DEX_NUMBER);
		mForm = bundle.getInt(ARG_FORM);
		mFormCount = bundle.getInt(ARG_FORM_COUNT);
		mFemaleRatio = 0;
		mMaleRatio = 0;
		mStats = stats;
		mType1 = type1;
		mType2 = type2;
		mHeight = 0;
		mWeight = 0;
		mClassification = "";
		mCaptureRate = 0;
		mBaseEggSteps = 0;
		mAbility1 = ability1;
		mAbility2 = ability2;
		mAbilityHidden = hiddenAbility;
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
		mName = bundle.getString(ARG_NAME);
		mNickname = bundle.getString(ARG_NICKNAME);
		mAttackList = attackList;
	}

	@Override public int gen() { return 6; }

	@Override public String name() {
		return mName;
	}

	@Override public Pokemon setIvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		mStats.setIvs(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon setEvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		mStats.setEvs(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon setStats(int hp, int attack, int defense, int spattack, int spdefense,
								int speed) {
		mStats.setCurrentValues(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon addAttack(Attack attack, int position) {
		if (position >= 0 && position <= 3) {
			mAttackSet.add(position, attack);
		}
		return this;
	}

	@Override public Map<Stats.Stat, Integer> baseStats() {
		return stats().base();
	}

	@Override public Pokemon setAbility(Ability ability) {
		mAbility = ability;
		return this;
	}

	@Override public Pokemon setHappiness(int happiness) {
		mHappiness = happiness;
		return this;
	}

	@Override public int happiness() {
		return mHappiness;
	}

	@Override public Nature nature() {
		return mNature;
	}

	@Override public Pokemon setNature(Nature nature) {
		this.mNature = nature;
		this.mStats.setNature(nature);
		return this;
	}

	@Override public Type additionalType() {
		return mAdditionalType;
	}

	@Override public Pokemon addAdditionalType(Type type) {
		mAdditionalType = type;
		return this;
	}

	@Override public Pokemon setCurrentAttacks(List<Attack> attacks) {
		mAttackSet = attacks;
		return this;
	}

	@Override public Pokemon setCurrentAbility(Ability ability) {
		mAbility = ability;
		return this;
	}


	@Override public Pokemon calculateIvs() {
		IvTools.getIvs(6, this);
		return this;
	}

	@Override public Pokemon calculateStats() {
		mStats.updateWith(IvTools.calculateStats(this));
		return this;
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

	@Override public Pokemon setLevel(int level) {
		this.mLevel = level;
		mStats.setValuesFromStats(level);
		return this;
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

	@Override public String nickname() {
		return mNickname;
	}

	@Override public Pokemon setNickname(String nickname) {
		this.mNickname = nickname;
		return this;
	}

	@Override public Bundle save() {
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_GEN, gen());
		bundle.putInt(ARG_LEVEL, level());
		bundle.putInt(ARG_DEX_NUMBER, dexNumber());
		bundle.putInt(ARG_FORM, formNumber());
		bundle.putInt(ARG_FORM_COUNT, mFormCount);
		bundle.putBundle(ARG_STATS, mStats.save());
		bundle.putInt(ARG_TYPE_1, mType1.save());
		bundle.putInt(ARG_TYPE_2, mType2.save());
		bundle.putString(ARG_NAME, mName);
		bundle.putString(ARG_NICKNAME, mNickname);
		bundle.putBundle(ARG_ABILITY_1, mAbility1.save());
		bundle.putBundle(ARG_ABILITY_2, mAbility2.save());
		bundle.putBundle(ARG_ABILITY_HIDDEN, mAbilityHidden.save());
		return bundle;
	}

	@Override public Pokemon load(Bundle bundle) {
		//TODO: do.
		return null;
	}

	@Override public Map<Stats.Stat, Integer> evs() {
		return stats().evs();
	}

	@Override public Map<Stats.Stat, Integer> ivs() {
		return stats().ivs();
	}

	@Override public Map<Stats.Stat, Integer> currentStats() {
		return stats().currentValues();
	}
}
