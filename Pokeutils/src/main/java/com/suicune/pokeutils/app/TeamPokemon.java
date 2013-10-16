package com.suicune.pokeutils.app;

import android.database.Cursor;
import android.os.Bundle;
import com.suicune.pokeutils.tools.IVTools;

public class TeamPokemon extends Pokemon {
    private static final String ATTACKS = "attacks";
    private static final String ITEM = "item";
    private static final String SELECTED_ABILITY = "selectedAbility";
    private static final String NICK_NAME = "nickName";
    private static final String IVS = "ivs";
    private static final String EVS = "evs";
    private static final String STATS = "stats";
    private static final String STATS_MODIFIER = "statsModifier";
    private static final String LEVEL = "level";
    private static final String NATURE = "nature";

    public Attack[] mAttacks = { new Attack(0), new Attack(0), new Attack(0), new Attack(0) };

    public Item mAttachedItem;
    public Ability mSelectedAbility;

    public String mNickname;

    public int[] mIvs = { 31, 31, 31, 31, 31, 31 };
    public int[] mEvs = { 0, 0, 0, 0, 0, 0 };
    public int[] mStats = new int[6];
    public int[] mStatsModifier = { 6, 6, 6, 6, 6, 6 };
    public int mLevel = 100;
    public int mNature = 0;

    public TeamPokemon(Cursor cursor) {
        super(cursor);
		setStats();
	}

    public TeamPokemon(Bundle args) {
        super(args);
        mAttachedItem = new Item(args.getInt(ITEM));
        mSelectedAbility = new Ability(args.getInt(SELECTED_ABILITY));
        mNickname = args.getString(NICK_NAME);
        mIvs = args.getIntArray(IVS);
        mEvs = args.getIntArray(EVS);
        mStats = args.getIntArray(STATS);
        mStatsModifier = args.getIntArray(STATS_MODIFIER);
        mLevel = args.getInt(LEVEL);
        mNature = args.getInt(NATURE);
        mAttacks[0] = new Attack(args.getIntArray(ATTACKS)[0]);
        mAttacks[1] = new Attack(args.getIntArray(ATTACKS)[1]);
        mAttacks[2] = new Attack(args.getIntArray(ATTACKS)[2]);
        mAttacks[3] = new Attack(args.getIntArray(ATTACKS)[3]);
        setStats();
    }

    public void useAttack(Attack attack, TeamPokemon target) {
        useAttack(attack, target, null);
    }

    public void receiveAttack(Attack attack, TeamPokemon source) {
        receiveAttack(attack, source, null);
    }

    public void useAttack(Attack attack, TeamPokemon target, Bundle conditions) {

    }

    public void receiveAttack(Attack attack, TeamPokemon source, Bundle conditions) {

    }

    public void getInBattle(){

    }

    public void retreat(){

    }

    public void showStats(){
        setStats();
    }

    public void setAbility(int abilityId){
        Ability ability = new Ability(abilityId);
        if(mAbilities.contains(ability)){
            mSelectedAbility = ability;
        }
    }





	private void setStats() {
		if (mIvs == null || mEvs == null) {
			return;
		}

		mStats[STAT_INDEX_HP] = IVTools.getHpValue(mBaseStats[STAT_INDEX_HP], mEvs[STAT_INDEX_HP],
				mIvs[STAT_INDEX_HP], mLevel);
		mStats[STAT_INDEX_ATT] = IVTools.getStatValue(mBaseStats[STAT_INDEX_ATT], mEvs[STAT_INDEX_ATT],
				mIvs[STAT_INDEX_ATT], mLevel, getNatureModifier(mNature, STAT_INDEX_ATT));
		mStats[STAT_INDEX_DEF] = IVTools.getStatValue(mBaseStats[STAT_INDEX_DEF], mEvs[STAT_INDEX_DEF],
				mIvs[STAT_INDEX_DEF], mLevel, getNatureModifier(mNature, STAT_INDEX_DEF));
		mStats[STAT_INDEX_SP_ATT] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SP_ATT],
				mEvs[STAT_INDEX_SP_ATT], mIvs[STAT_INDEX_SP_ATT], mLevel,
				getNatureModifier(mNature, STAT_INDEX_SP_ATT));
		mStats[STAT_INDEX_SP_DEF] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SP_DEF],
				mEvs[STAT_INDEX_SP_DEF], mIvs[STAT_INDEX_SP_DEF], mLevel,
				getNatureModifier(mNature, STAT_INDEX_SP_DEF));
		mStats[STAT_INDEX_SPEED] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SPEED],
				mEvs[STAT_INDEX_SPEED], mIvs[STAT_INDEX_SPEED], mLevel,
				getNatureModifier(mNature, STAT_INDEX_SPEED));
	}

	private int getNatureModifier(int nature, int stat) {
		switch (stat) {
		case STAT_INDEX_ATT:
			if (nature == Natures.ADAMANT || nature == Natures.LONELY
					|| nature == Natures.BRAVE || nature == Natures.NAUGHTY) {
				return 110;
			} else if (nature == Natures.BOLD || nature == Natures.TIMID
					|| nature == Natures.MODEST || nature == Natures.CALM) {
				return 90;
			}
			break;
		case STAT_INDEX_DEF:
			if (nature == Natures.BOLD || nature == Natures.RELAXED
					|| nature == Natures.IMPISH || nature == Natures.LAX) {
				return 110;
			} else if (nature == Natures.LONELY || nature == Natures.HASTY
					|| nature == Natures.MILD || nature == Natures.GENTLE) {
				return 90;
			}
			break;
		case STAT_INDEX_SP_ATT:
			if (nature == Natures.MODEST || nature == Natures.MILD
					|| nature == Natures.QUIET || nature == Natures.RASH) {
				return 110;
			} else if (nature == Natures.ADAMANT || nature == Natures.IMPISH
					|| nature == Natures.JOLLY || nature == Natures.CAREFUL) {
				return 90;
			}
			break;
		case STAT_INDEX_SP_DEF:
			if (nature == Natures.CALM || nature == Natures.GENTLE
					|| nature == Natures.SASSY || nature == Natures.CAREFUL) {
				return 110;
			} else if (nature == Natures.NAUGHTY || nature == Natures.LAX
					|| nature == Natures.NAIVE || nature == Natures.RASH) {
				return 90;
			}
			break;
		case STAT_INDEX_SPEED:
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
		status.putInt(ITEM, mAttachedItem.mId);
		status.putInt(SELECTED_ABILITY, mSelectedAbility.mId);
		status.putString(NICK_NAME, mNickname);
		status.putIntArray(IVS, mIvs);
		status.putIntArray(EVS, mEvs);
		status.putIntArray(STATS, mStats);
		status.putIntArray(STATS_MODIFIER, mStatsModifier);
		status.putInt(LEVEL, mLevel);
		status.putInt(NATURE, mNature);
        int[] attackIds = {mAttacks[0].mId, mAttacks[1].mId, mAttacks[2].mId, mAttacks[3].mId};
        status.putIntArray(ATTACKS, attackIds);
	}
}
