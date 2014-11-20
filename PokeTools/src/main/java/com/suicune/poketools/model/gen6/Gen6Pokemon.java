package com.suicune.poketools.model.gen6;

import android.content.Context;
import android.os.Bundle;

import com.suicune.poketools.model.Ability;
import com.suicune.poketools.model.Attack;
import com.suicune.poketools.model.EggGroup;
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
 * [{"dex_number":1,"form":0,"egg_group_1":2,"egg_group_2":7,"height":0.7,"weight":6.9,"abilities":[65,0,null],"min_level":1,"baseStats":[45,49,49,65,65,45],"types":[12,4],"attacks":[14,15,20,22,29,33,34,36,38,45,70,73,74,75,76,77,79,80,81,92,102,104,111,113,124,130,133,148,156,164,173,174,182,188,189,202,203,204,206,207,210,213,214,216,218,219,230,235,237,241,249,263,267,275,282,290,311,320,331,335,338,345,363,388,402,412,437,438,445,447,474,496,497,520,585,593]}]
 * Created by denis on 01.01.14.
 */
public class Gen6Pokemon extends Pokemon {
	/**
	 * Immutable properties
	 */
	public final int pokedexNumber;
	public final int form;
	public final int formCount;
	public final double femaleRatio;
	public final double maleRatio;
	public final Type type1;
	public final Type type2;
	public final double height;
	public final double weight;
	public final String classification;
	public final int captureRate;
	public final int baseEggSteps;
	public final int minLevel;
	public final Ability ability1;
	public final Ability ability2;
	public final Ability abilityHidden;
	public final boolean isHiddenAbilityAvailable;
	public final int experienceGrowth;
	public final int baseHappiness;
	public final Map<Stat, Integer> evsEarned;
	public final EggGroup eggGroup1;
	public final EggGroup eggGroup2;
	public final Map<Integer, Attack> levelAttacks;
	public final Map<String, Attack> tmAttacks;
	public final List<Attack> eggMoves;
	public final Map<String, Attack> tutorMoves;
	public final Map<String, Attack> transferAttacks;
	public final List<Attack> attackList;

	/**
	 * Subproperties are modifiable
	 */
	public final Stats stats;

	/**
	 * Mutable properties
	 */
	public int level;
	public Map<Integer, Attack> attackSet;
	public Ability ability;
	public int happiness = 70;
	public Type additionalType;
	public Nature nature;
	public String nickname;
	public String name;

	public Gen6Pokemon(int level, JSONObject data, Stats stats, Type[] types, Ability[] abilities,
					   int formCount, String name, List<Attack> attackList) throws JSONException {
		this.level = level;
		pokedexNumber = data.getInt(ARG_DEX_NUMBER);
		form = data.getInt(ARG_FORM);
		this.formCount = formCount;
		femaleRatio = 0;
		maleRatio = 0;
		this.stats = stats;
		type1 = types[0];
		type2 = types[1];
		height = (data.isNull(ARG_HEIGHT)) ? 0 : data.getDouble(ARG_HEIGHT);
		weight = (data.isNull(ARG_WEIGHT)) ? 0 : data.getDouble(ARG_WEIGHT);
		classification = "";
		captureRate = 0;
		baseEggSteps = 0;
		ability1 = abilities[0];
		ability2 = abilities[1];
		abilityHidden = abilities[2];
		experienceGrowth = 0;
		baseHappiness = 0;
		minLevel = data.getInt(ARG_MIN_LEVEL);
		evsEarned = new HashMap<>();
		eggGroup1 = EggGroup.fromValue((data.isNull(ARG_EGG_GROUP_1)) ? 0 : data.getInt(ARG_EGG_GROUP_1));
		eggGroup2 = EggGroup.fromValue((data.isNull(ARG_EGG_GROUP_2)) ? 0 : data.getInt(ARG_EGG_GROUP_2));
		this.isHiddenAbilityAvailable = false;
		levelAttacks = new HashMap<>();
		tmAttacks = new HashMap<>();
		eggMoves = new ArrayList<>();
		tutorMoves = new HashMap<>();
		transferAttacks = new HashMap<>();
		attackSet = new HashMap<>();
		this.name = name;
		nickname = name;
		this.attackList = attackList;
		ability = ability1;
		additionalType = TypeFactory.getDefault(6);
		nature = NatureFactory.getDefault(6);
	}

	@Override public int gen() { return 6; }

	@Override public String name() {
		return name;
	}

	@Override public Pokemon setIvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		stats.setIvs(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon setEvs(int hp, int attack, int defense, int spattack, int spdefense,
							  int speed) {
		stats.setEvs(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon setStats(int hp, int attack, int defense, int spattack, int spdefense,
								int speed) {
		stats.setCurrentValues(hp, attack, defense, spattack, spdefense, speed);
		return this;
	}

	@Override public Pokemon addAttack(Attack attack, int position) {
		if (position >= 1 && position <= 4) {
			attackSet.put(position, attack);
		}
		return this;
	}

	@Override public Pokemon addAttack(Attack attack) {
		if(attackSet.size() < 4) {
			attackSet.put(attackSet.size(), attack);
		}
		return this;
	}

	@Override public Pokemon setIv(Stat stat, int value) {
		stats.putIv(stat, value);
		return this;
	}

	@Override public Pokemon setEv(Stat stat, int value) {
		stats.putEv(stat, value);
		return this;
	}

	@Override public Pokemon setValue(Stat stat, int value) {
		stats.currentValues().put(stat, value);
		return this;
	}

	@Override public Map<Stat, Integer> baseStats() {
		return stats().base();
	}

	@Override public Pokemon setAbility(Ability ability) {
		this.ability = ability;
		return this;
	}

	@Override public Pokemon setHappiness(int happiness) {
		this.happiness = happiness;
		return this;
	}

	@Override public int happiness() {
		return happiness;
	}

	@Override public Nature nature() {
		return nature;
	}

	@Override public Pokemon setNature(Nature nature) {
		this.nature = nature;
		this.stats.setNature(nature);
		return this;
	}

	@Override public Type additionalType() {
		return additionalType;
	}

	@Override public Pokemon addAdditionalType(Type type) {
		additionalType = type;
		return this;
	}

	@Override public Pokemon setCurrentAttacks(Map<Integer, Attack> attacks) {
		attackSet = attacks;
		return this;
	}

	@Override public Pokemon setCurrentAbility(Ability ability) {
		this.ability = ability;
		return this;
	}


	@Override public Map<Stat, List<Integer>> calculateIvs() {
		return stats.calculateIvs();
	}

	@Override public Stats calculateStats() {
		return stats.calculateStats();
	}

	@Override public int dexNumber() {
		return pokedexNumber;
	}

	@Override public int formNumber() {
		return form;
	}

	@Override public double femaleRatio() {
		return femaleRatio;
	}

	@Override public double maleRatio() {
		return maleRatio;
	}

	@Override public Stats stats() {
		return stats;
	}

	@Override public Type type1() {
		return type1;
	}

	@Override public Type type2() {
		return type2;
	}

	@Override public Ability ability1() {
		return ability1;
	}

	@Override public Ability ability2() {
		return ability2;
	}

	@Override public Ability abilityHidden() {
		return abilityHidden;
	}

	@Override public boolean isHiddenAbilityAvailable() {
		return isHiddenAbilityAvailable;
	}

	@Override public Map<Integer, Attack> levelAttacks() {
		return levelAttacks;
	}

	@Override public Map<String, Attack> tmAttacks() {
		return tmAttacks;
	}

	@Override public List<Attack> eggAttacks() {
		return eggMoves;
	}

	@Override public Map<String, Attack> tutorAttacks() {
		return tutorMoves;
	}

	@Override public Map<String, Attack> transferAttacks() {
		return transferAttacks;
	}

	@Override public List<Attack> attackList() {
		return attackList;
	}

	@Override public EggGroup eggGroup1() {
		return eggGroup1;
	}

	@Override public EggGroup eggGroup2() {
		return eggGroup2;
	}

	@Override public double height() {
		return height;
	}

	@Override public double weight() {
		return weight;
	}

	@Override public int minLevel() {
		return minLevel;
	}

	@Override public Pokemon setLevel(int level) {
		this.level = level;
		stats.setValuesFromStats(level);
		return this;
	}

	@Override public int level() {
		return level;
	}

	@Override public Map<Integer, Attack> currentAttacks() {
		return attackSet;
	}

	@Override public Ability currentAbility() {
		return ability;
	}

	@Override public String nickname() {
		return nickname;
	}

	@Override public Pokemon setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	@Override public Bundle save() {
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_LEVEL, level());
		bundle.putInt(ARG_GEN, gen());
		bundle.putInt(ARG_DEX_NUMBER, dexNumber());
		bundle.putInt(ARG_FORM, formNumber());
		bundle.putBundle(ARG_STATS, stats.save());
		bundle.putString(ARG_NAME, name);
		bundle.putString(ARG_NICKNAME, nickname);
		bundle.putInt(ARG_HAPPINESS, happiness);
		bundle.putBundle(ARG_ABILITY, ability.save());
		bundle.putInt(ARG_ADDITIONAL_TYPE, additionalType.save());
		bundle.putInt(ARG_NATURE, nature.save());
		int[] currentAttacks = new int[4];
		for(int i = 1; i <= 4; i++) {
			currentAttacks[i - 1] = (attackSet.get(i) != null) ? attackSet.get(i).id() : 0;
		}
		bundle.putIntArray(ARG_CURRENT_ATTACKS, currentAttacks);
		return bundle;
	}

	@Override public Pokemon load(Context context, Bundle bundle)
			throws IOException, JSONException {
		if(!bundle.getString(ARG_NAME).equals(bundle.getString(ARG_NICKNAME))) {
			this.nickname = bundle.getString(ARG_NICKNAME);
		}
		this.ability = AbilityFactory.fromBundle(6, bundle.getBundle(ARG_ABILITY));
		int[] currentAttacks = bundle.getIntArray(ARG_CURRENT_ATTACKS);
		for(int i = 1; i <= 4; i++) {
			if(currentAttacks[i - 1] != 0) {
				attackSet.put(i, AttackFactory.create(context, 6, currentAttacks[i - 1]));
			}
		}
		this.happiness = bundle.getInt(ARG_HAPPINESS);
		this.additionalType = TypeFactory.createType(6, bundle.getInt(ARG_ADDITIONAL_TYPE));
		this.nature = NatureFactory.get(6, bundle.getInt(ARG_NATURE));
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

	@Override public int color() {
		return type1.color();
	}
}
