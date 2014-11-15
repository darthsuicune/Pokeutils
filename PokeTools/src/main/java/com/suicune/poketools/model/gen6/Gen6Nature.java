package com.suicune.poketools.model.gen6;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;

/**
 * Created by denis on 20.09.14.
 */
public enum Gen6Nature implements Nature {
	HARDY(R.string.nature_hardy, null, null, 1, 1, 1, 1, 1), //Nothing
	BASHFUL(R.string.nature_bashful, null, null, 1, 1, 1, 1, 1), //Nothing
	DOCILE(R.string.nature_docile, null, null, 1, 1, 1, 1, 1),//Nothing
	QUIRKY(R.string.nature_quirky, null, null, 1, 1, 1, 1, 1),//Nothing
	SERIOUS(R.string.nature_serious, null, null, 1, 1, 1, 1, 1),//Nothing
	LONELY(R.string.nature_lonely, Stats.Stat.ATTACK, Stats.Stat.DEFENSE, 1.1, 0.9, 1, 1, 1),
	ADAMANT(R.string.nature_adamant, Stats.Stat.ATTACK, Stats.Stat.SPECIAL_ATTACK, 1.1, 1, 0.9, 1,
			1),
	NAUGHTY(R.string.nature_naughty, Stats.Stat.ATTACK, Stats.Stat.SPECIAL_DEFENSE, 1.1, 1, 1, 0.9,
			1),
	BRAVE(R.string.nature_brave, Stats.Stat.ATTACK, Stats.Stat.SPEED, 1.1, 1, 1, 1, 0.9),
	BOLD(R.string.nature_bold, Stats.Stat.DEFENSE, Stats.Stat.ATTACK, 0.9, 1.1, 1, 1, 1),
	IMPISH(R.string.nature_impish, Stats.Stat.DEFENSE, Stats.Stat.SPECIAL_ATTACK, 1, 1.1, 0.9, 1,
			1),
	LAX(R.string.nature_lax, Stats.Stat.DEFENSE, Stats.Stat.SPECIAL_DEFENSE, 1, 1.1, 1, 0.9, 1),
	RELAXED(R.string.nature_relaxed, Stats.Stat.DEFENSE, Stats.Stat.SPEED, 1, 1.1, 1, 1, 0.9),
	MODEST(R.string.nature_modest, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.ATTACK, 0.9, 1, 1.1, 1, 1),
	MILD(R.string.nature_mild, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.DEFENSE, 1, 0.9, 1.1, 1, 1),
	RASH(R.string.nature_rash, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.SPECIAL_DEFENSE, 1, 1, 1.1,
			0.9, 1),
	QUIET(R.string.nature_quiet, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.SPEED, 1, 1, 1.1, 1, 0.9),
	CALM(R.string.nature_calm, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.ATTACK, 0.9, 1, 1, 1.1, 1),
	GENTLE(R.string.nature_gentle, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.DEFENSE, 1, 0.9, 1, 1.1,
			1),
	CAREFUL(R.string.nature_careful, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.SPECIAL_ATTACK, 1, 1,
			0.9, 1.1, 1),
	SASSY(R.string.nature_sassy, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.SPEED, 1, 1, 1, 1.1, 0.9),
	TIMID(R.string.nature_timid, Stats.Stat.SPEED, Stats.Stat.ATTACK, 0.9, 1, 1, 1, 1.1),
	HASTY(R.string.nature_hasty, Stats.Stat.SPEED, Stats.Stat.DEFENSE, 1, 0.9, 1, 1, 1.1),
	JOLLY(R.string.nature_jolly, Stats.Stat.SPEED, Stats.Stat.SPECIAL_ATTACK, 1, 1, 0.9, 1, 1.1),
	NAIVE(R.string.nature_naive, Stats.Stat.SPEED, Stats.Stat.SPECIAL_DEFENSE, 1, 1, 1, 0.9, 1.1);


	public int mNameResId;
	public Stats.Stat mIncreasedStat;
	public Stats.Stat mDecreasedStat;
	public double mAttackModifier;
	public double mDefenseModifier;
	public double mSpecialAttackModifier;
	public double mSpecialDefenseModifier;
	public double mSpeedModifier;

	private Gen6Nature(int nameResId, Stats.Stat increasedStat, Stats.Stat decreasedStat,
					   double attackModifier, double defenseModifier, double spAttackModifier,
					   double spDefenseModifier, double speedModifier) {
		mNameResId = nameResId;
		mIncreasedStat = increasedStat;
		mDecreasedStat = decreasedStat;
		mAttackModifier = attackModifier;
		mDefenseModifier = defenseModifier;
		mSpecialAttackModifier = spAttackModifier;
		mSpecialDefenseModifier = spDefenseModifier;
		mSpeedModifier = speedModifier;
	}

	@Override public int nameResId() {
		return mNameResId;
	}

	@Override public Stats.Stat increasedStat() {
		return mIncreasedStat;
	}

	@Override public Stats.Stat decreasedStat() {
		return mDecreasedStat;
	}

	@Override public double statModifier(Stats.Stat stat) {
		switch(stat) {
			case ATTACK:
				return mAttackModifier;
			case DEFENSE:
				return mDefenseModifier;
			case SPECIAL_ATTACK:
				return mSpecialAttackModifier;
			case SPECIAL_DEFENSE:
				return mSpecialDefenseModifier;
			case SPEED:
				return mSpeedModifier;
			default:
				return 1.0;
		}
	}
	public int save() {
		return this.ordinal();
	}
}
