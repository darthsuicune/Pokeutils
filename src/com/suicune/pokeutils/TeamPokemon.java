package com.suicune.pokeutils;

import android.database.Cursor;

import com.suicune.pokeutils.tools.IVTools;

public class TeamPokemon extends Pokemon {
	public TeamPokemon(long id, String mName, int mNumber, int mForm, int mType1,
			int mType2, int mBaseHP, int mBaseAtt, int mBaseDef,
			int mBaseSpAtt, int mBaseSpDef, int mBaseSpeed, int mAbility1,
			int mAbility2, int mAbilityDw) {
		super(id, mName, mNumber, mForm, mType1, mType2, mBaseHP, mBaseAtt,
				mBaseDef, mBaseSpAtt, mBaseSpDef, mBaseSpeed, mAbility1,
				mAbility2, mAbilityDw);
	}

	public TeamPokemon(Cursor cursor) {
		super(cursor);
	}

	public final static int INDEX_HP = 0;
	public final static int INDEX_ATT = 1;
	public final static int INDEX_DEF = 2;
	public final static int INDEX_SP_ATT = 3;
	public final static int INDEX_SP_DEF = 4;
	public final static int INDEX_SPEED = 5;

	public Attack mAttack1 = null;
	public Attack mAttack2 = null;
	public Attack mAttack3 = null;
	public Attack mAttack4 = null;

	public Ability mAbility;
	public Item mAttachedItem;

	public String mNickname;

	public int[] mIvs = new int[6];
	public int[] mEvs = new int[6];
	public int[] mStats = new int[6];
	public double[] mStatsModifier = new double[6];
	public int mLevel;
	public int mNature;

	public void setIvs(int[] ivs) {
		mIvs = ivs;
	}

	public void setEvs(int[] evs) {
		mEvs = evs;
	}

	public void setStats() {
		if (mIvs == null || mEvs == null) {
			return;
		}

		mStats[INDEX_HP] = IVTools.getHpValue(mBaseHP, mEvs[INDEX_HP],
				mIvs[INDEX_HP], mLevel);
		mStats[INDEX_ATT] = IVTools.getStatValue(mBaseAtt, mEvs[INDEX_ATT],
				mIvs[INDEX_ATT], mLevel, getNatureModifier(mNature, INDEX_ATT));
		mStats[INDEX_DEF] = IVTools.getStatValue(mBaseDef, mEvs[INDEX_DEF],
				mIvs[INDEX_HP], mLevel, getNatureModifier(mNature, INDEX_DEF));
		mStats[INDEX_SP_ATT] = IVTools.getStatValue(mBaseSpAtt,
				mEvs[INDEX_SP_ATT], mIvs[INDEX_HP], mLevel,
				getNatureModifier(mNature, INDEX_SP_ATT));
		mStats[INDEX_SP_DEF] = IVTools.getStatValue(mBaseSpDef,
				mEvs[INDEX_SP_DEF], mIvs[INDEX_HP], mLevel,
				getNatureModifier(mNature, INDEX_SP_DEF));
		mStats[INDEX_SPEED] = IVTools.getStatValue(mBaseSpeed,
				mEvs[INDEX_SPEED], mIvs[INDEX_HP], mLevel,
				getNatureModifier(mNature, INDEX_SPEED));
	}

	public int getNatureModifier(int nature, int stat) {
		switch (stat) {
		case INDEX_ATT:
			if (nature == Natures.ADAMANT || nature == Natures.LONELY
					|| nature == Natures.BRAVE || nature == Natures.NAUGHTY) {
				return 110;
			} else if (nature == Natures.BOLD || nature == Natures.TIMID
					|| nature == Natures.MODEST || nature == Natures.CALM) {
				return 90;
			}
			break;
		case INDEX_DEF:
			if (nature == Natures.BOLD || nature == Natures.RELAXED
					|| nature == Natures.IMPISH || nature == Natures.LAX) {
				return 110;
			} else if (nature == Natures.LONELY || nature == Natures.HASTY
					|| nature == Natures.MILD || nature == Natures.GENTLE) {
				return 90;
			}
			break;
		case INDEX_SP_ATT:
			if (nature == Natures.MODEST || nature == Natures.MILD
					|| nature == Natures.QUIET || nature == Natures.RASH) {
				return 110;
			} else if (nature == Natures.ADAMANT || nature == Natures.IMPISH
					|| nature == Natures.JOLLY || nature == Natures.CAREFUL) {
				return 90;
			}
			break;
		case INDEX_SP_DEF:
			if (nature == Natures.CALM || nature == Natures.GENTLE
					|| nature == Natures.SASSY || nature == Natures.CAREFUL) {
				return 110;
			} else if (nature == Natures.NAUGHTY || nature == Natures.LAX
					|| nature == Natures.NAIVE || nature == Natures.RASH) {
				return 90;
			}
			break;
		case INDEX_SPEED:
			if (nature == Natures.TIMID || nature == Natures.HASTY
					|| nature == Natures.JOLLY || nature == Natures.NAIVE) {
				return 110;
			} else if (nature == Natures.BRAVE || nature == Natures.RELAXED
					|| nature == Natures.QUIET || nature == Natures.SASSY) {
				return 90;
			}
			break;
		}
		return 100;
	}
}
