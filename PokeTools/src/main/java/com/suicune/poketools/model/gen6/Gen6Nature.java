package com.suicune.poketools.model.gen6;

import com.suicune.poketools.R;
import com.suicune.poketools.model.Nature;
import com.suicune.poketools.model.Stats;

/**
 * Created by denis on 20.09.14.
 */
public enum Gen6Nature implements Nature {
	HARDY(R.string.nature_hardy, null, null), //Nothing
	BASHFUL(R.string.nature_bashful, null, null), //Nothing
	DOCILE(R.string.nature_docile, null, null),//Nothing
	QUIRKY(R.string.nature_quirky, null, null),//Nothing
	SERIOUS(R.string.nature_serious, null, null),//Nothing
	LONELY(R.string.nature_lonely, Stats.Stat.ATTACK, Stats.Stat.DEFENSE),
	ADAMANT(R.string.nature_adamant, Stats.Stat.ATTACK, Stats.Stat.SPECIAL_ATTACK),
	NAUGHTY(R.string.nature_naughty, Stats.Stat.ATTACK, Stats.Stat.SPECIAL_DEFENSE),
	BRAVE(R.string.nature_brave, Stats.Stat.ATTACK, Stats.Stat.SPEED),
	BOLD(R.string.nature_bold, Stats.Stat.DEFENSE, Stats.Stat.ATTACK),
	IMPISH(R.string.nature_impish, Stats.Stat.DEFENSE, Stats.Stat.SPECIAL_ATTACK),
	LAX(R.string.nature_lax, Stats.Stat.DEFENSE, Stats.Stat.SPECIAL_DEFENSE),
	RELAXED(R.string.nature_relaxed, Stats.Stat.DEFENSE, Stats.Stat.SPEED),
	MODEST(R.string.nature_modest, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.ATTACK),
	MILD(R.string.nature_mild, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.DEFENSE),
	RASH(R.string.nature_rash, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.SPECIAL_DEFENSE),
	QUIET(R.string.nature_quiet, Stats.Stat.SPECIAL_ATTACK, Stats.Stat.SPEED),
	CALM(R.string.nature_calm, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.ATTACK),
	GENTLE(R.string.nature_gentle, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.DEFENSE),
	CAREFUL(R.string.nature_careful, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.SPECIAL_ATTACK),
	SASSY(R.string.nature_sassy, Stats.Stat.SPECIAL_DEFENSE, Stats.Stat.SPEED),
	TIMID(R.string.nature_timid, Stats.Stat.SPEED, Stats.Stat.ATTACK),
	HASTY(R.string.nature_hasty, Stats.Stat.SPEED, Stats.Stat.DEFENSE),
	JOLLY(R.string.nature_jolly, Stats.Stat.SPEED, Stats.Stat.SPECIAL_ATTACK),
	NAIVE(R.string.nature_naive, Stats.Stat.SPEED, Stats.Stat.SPECIAL_DEFENSE);

	public int mNameResId;
	public Stats.Stat mIncreasedStat;
	public Stats.Stat mDecreasedStat;

	private Gen6Nature(int nameResId, Stats.Stat increasedStat, Stats.Stat decreasedStat) {
		mNameResId = nameResId;
		mIncreasedStat = increasedStat;
		mDecreasedStat = decreasedStat;
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

}
