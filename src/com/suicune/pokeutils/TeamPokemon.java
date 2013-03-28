package com.suicune.pokeutils;

import android.database.Cursor;
import android.os.Bundle;

import com.suicune.pokeutils.tools.IVTools;

public class TeamPokemon extends Pokemon {
	private static final String ITEM = "item";
	private static final String SELECTED_ABILITY = "selectedAbility";
	private static final String NICK_NAME = "nickName";
	private static final String IVS = "ivs";
	private static final String EVS = "evs";
	private static final String STATS = "stats";
	private static final String STATS_MODIFIER = "statsModifier";
	private static final String LEVEL = "level";
	private static final String NATURE = "nature";

	public TeamPokemon(Bundle args) {
		super(args);
		mAttachedItem = args.getInt(ITEM);
		mSelectedAbility = args.getInt(SELECTED_ABILITY);
		mNickname = args.getString(NICK_NAME);
		mIvs = args.getIntArray(IVS);
		mEvs = args.getIntArray(EVS);
		mStats = args.getIntArray(STATS);
		mStatsModifier = args.getIntArray(STATS_MODIFIER);
		mLevel = args.getInt(LEVEL);
		mNature = args.getInt(NATURE);
		setStats();
	}

	public TeamPokemon(Cursor cursor) {
		super(cursor);
		setStats();
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

	public int mAttachedItem;
	public int mSelectedAbility;

	public String mNickname;

	public int[] mIvs = new int[] { 31, 31, 31, 31, 31, 31 };
	public int[] mEvs = new int[] { 255, 255, 255, 255, 255, 255 };
	public int[] mStats = new int[6];
	public int[] mStatsModifier = new int[] { 6, 6, 6, 6, 6, 6 };
	public int mLevel = 100;
	public int mNature = 0;

	public void setStats() {
		if (mIvs == null || mEvs == null) {
			return;
		}

		mStats[INDEX_HP] = IVTools.getHpValue(mBaseHP, mEvs[INDEX_HP],
				mIvs[INDEX_HP], mLevel);
		mStats[INDEX_ATT] = IVTools.getStatValue(mBaseAtt, mEvs[INDEX_ATT],
				mIvs[INDEX_ATT], mLevel, getNatureModifier(mNature, INDEX_ATT));
		mStats[INDEX_DEF] = IVTools.getStatValue(mBaseDef, mEvs[INDEX_DEF],
				mIvs[INDEX_DEF], mLevel, getNatureModifier(mNature, INDEX_DEF));
		mStats[INDEX_SP_ATT] = IVTools.getStatValue(mBaseSpAtt,
				mEvs[INDEX_SP_ATT], mIvs[INDEX_SP_ATT], mLevel,
				getNatureModifier(mNature, INDEX_SP_ATT));
		mStats[INDEX_SP_DEF] = IVTools.getStatValue(mBaseSpDef,
				mEvs[INDEX_SP_DEF], mIvs[INDEX_SP_DEF], mLevel,
				getNatureModifier(mNature, INDEX_SP_DEF));
		mStats[INDEX_SPEED] = IVTools.getStatValue(mBaseSpeed,
				mEvs[INDEX_SPEED], mIvs[INDEX_SPEED], mLevel,
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

	public void saveStatus(Bundle status) {
		super.saveStatus(status);
		status.putInt(ITEM, mAttachedItem);
		status.putInt(SELECTED_ABILITY, mSelectedAbility);
		status.putString(NICK_NAME, mNickname);
		status.putIntArray(IVS, mIvs);
		status.putIntArray(EVS, mEvs);
		status.putIntArray(STATS, mStats);
		status.putIntArray(STATS_MODIFIER, mStatsModifier);
		status.putInt(LEVEL, mLevel);
		status.putInt(NATURE, mNature);
	}
}
