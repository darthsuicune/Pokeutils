package com.suicune.poketools.model.gen6;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Pokemon;
import com.suicune.poketools.model.Stats;
import com.suicune.poketools.model.Type;
import com.suicune.poketools.model.factories.AbilityFactory;
import com.suicune.poketools.model.factories.AttackFactory;
import com.suicune.poketools.model.factories.NatureFactory;
import com.suicune.poketools.model.factories.StatsFactory;
import com.suicune.poketools.model.factories.TypeFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.suicune.poketools.model.Stats.Stat;

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
	public final Map<Stat, Integer> mEvsEarned;
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
	public Map<Integer, Attack> mAttackSet;
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
		mAttackSet = new HashMap<>();
		mName = name;
		mNickname = name;
		mAttackList = attackList;
		mAbility = mAbility1;
		mAdditionalType = TypeFactory.getDefault(6);
		mNature = NatureFactory.getDefault(6);
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
		if (position >= 1 && position <= 4) {
			mAttackSet.put(position, attack);
		}
		return this;
	}

	@Override public Pokemon addAttack(Attack attack) {
		if(mAttackSet.size() < 4) {
			mAttackSet.put(mAttackSet.size(), attack);
		}
		return this;
	}

	@Override public Pokemon setIv(Stat stat, int value) {
		mStats.ivs().put(stat, value);
		return this;
	}

	@Override public Pokemon setEv(Stat stat, int value) {
		mStats.evs().put(stat, value);
		return this;
	}

	@Override public Pokemon setValue(Stat stat, int value) {
		mStats.currentValues().put(stat, value);
		return this;
	}

	@Override public Map<Stat, Integer> baseStats() {
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

	@Override public Pokemon setCurrentAttacks(Map<Integer, Attack> attacks) {
		mAttackSet = attacks;
		return this;
	}

	@Override public Pokemon setCurrentAbility(Ability ability) {
		mAbility = ability;
		return this;
	}


	@Override public Map<Stat, List<Integer>> calculateIvs() {
		return mStats.calculateIvs();
	}

	@Override public Stats calculateStats() {
		return mStats.calculateStats();
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

	@Override public Type type1() {
		return mType1;
	}

	@Override public Type type2() {
		return mType2;
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

	@Override public List<Attack> attackList() {
		return mAttackList;
	}

	@Override public Pokemon setLevel(int level) {
		this.mLevel = level;
		mStats.setValuesFromStats(level);
		return this;
	}

	@Override public int level() {
		return mLevel;
	}

	@Override public Map<Integer, Attack> currentAttacks() {
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
		bundle.putInt(ARG_LEVEL, level());
		bundle.putInt(ARG_GEN, gen());
		bundle.putInt(ARG_DEX_NUMBER, dexNumber());
		bundle.putInt(ARG_FORM, formNumber());
		bundle.putBundle(ARG_STATS, mStats.save());
		bundle.putString(ARG_NAME, mName);
		bundle.putString(ARG_NICKNAME, mNickname);
		bundle.putInt(ARG_HAPPINESS, mHappiness);
		bundle.putBundle(ARG_ABILITY, mAbility.save());
		bundle.putInt(ARG_ADDITIONAL_TYPE, mAdditionalType.save());
		bundle.putInt(ARG_NATURE, mNature.save());
		int[] currentAttacks = new int[4];
		for(int i = 1; i <= 4; i++) {
			currentAttacks[i - 1] = (mAttackSet.get(i) != null) ? mAttackSet.get(i).id() : 0;
		}
		bundle.putIntArray(ARG_CURRENT_ATTACKS, currentAttacks);
		return bundle;
	}

	@Override public Pokemon load(Context context, Bundle bundle)
			throws IOException, JSONException {
		if(!bundle.getString(ARG_NAME).equals(bundle.getString(ARG_NICKNAME))) {
			this.mNickname = bundle.getString(ARG_NICKNAME);
		}
		this.mAbility = AbilityFactory.fromBundle(6, bundle.getBundle(ARG_ABILITY));
		int[] currentAttacks = bundle.getIntArray(ARG_CURRENT_ATTACKS);
		for(int i = 1; i <= 4; i++) {
			if(currentAttacks[i - 1] != 0) {
				mAttackSet.put(i, AttackFactory.create(context, 6, currentAttacks[i - 1]));
			}
		}
		this.mHappiness = bundle.getInt(ARG_HAPPINESS);
		this.mAdditionalType = TypeFactory.createType(6, bundle.getInt(ARG_ADDITIONAL_TYPE));
		this.mNature = NatureFactory.get(6, bundle.getInt(ARG_NATURE));
		this.stats().updateWith(StatsFactory.fromBundle(6, bundle.getBundle(ARG_STATS)));
		return this;
	}

	@Override public Map<Stat, Integer> evs() {
		return stats().evs();
	}

	@Override public Map<Stat, Integer> ivs() {
		return stats().ivs();
	}

	@Override public Map<Stat, Integer> currentStats() {
		return stats().currentValues();
	}
}
