package com.suicune.pokeutils.app;

import android.content.Context;
import android.os.Bundle;

import com.suicune.pokeutils.tools.IVTools;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamPokemon extends Pokemon {
    private static final int STAT_MODIFIER_0 = 6;
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

    public Attack[] mAttacks;

    public Item mAttachedItem;
    public Ability mCurrentAbility;

    public String mNickname;

    public int[] mIvs = { 31, 31, 31, 31, 31, 31 };
    public int[] mEvs = { 0, 0, 0, 0, 0, 0 };
    public int[] mStats = new int[6];
    public HashMap<Integer, ArrayList<Integer>> mPossibleIvs;
    public HashMap<Integer, ArrayList<Integer>> mPreviousPossibleIvs;
    public int[] mStatsModifier = { STAT_MODIFIER_0, STAT_MODIFIER_0, STAT_MODIFIER_0,
            STAT_MODIFIER_0, STAT_MODIFIER_0, STAT_MODIFIER_0 };
    public int mLevel = 100;
    public Natures.Nature mNature;

    public TeamPokemon(Context context, long id) {
        super(context, id);
        mPossibleIvs = new HashMap<Integer, ArrayList<Integer>>();
        mPreviousPossibleIvs = new HashMap<Integer, ArrayList<Integer>>();
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

    public void levelUp(){
        mPreviousPossibleIvs.putAll(mPossibleIvs);
        mLevel++;
    }

    public void getInBattle(Battlefield field){

    }

    public void retreat(Battlefield field){

    }

    public HashMap<Integer, String> getIvsAsString(){
        calculatePossibleIvs();
        HashMap<Integer, String> ivs = new HashMap<Integer, String>();
        for(int i = 0; i < 6; i++) {
            ivs.put(i, IVTools.getIVsAsString(mPossibleIvs.get(i)));
        }
        return ivs;
    }

    public void showStats(int[] evs, int[] ivs){
        mEvs = evs;
        mIvs = ivs;
        setStats();
    }

    public void setAbility(Context context, int abilityId){
        Ability ability = new Ability(context, abilityId);
        if(mAbilities.contains(ability)){
            mCurrentAbility = ability;
        }
    }

    private void calculatePossibleIvs() {
        for(int i = 0; i < 6; i++){
            if (mPreviousPossibleIvs != null && mPreviousPossibleIvs.get(i) != null) {
                mPossibleIvs.put(i, IVTools.calculatePossibleIVs(i, mNature, mStats[i], mEvs[i],
                        mLevel, mBaseStats[i], mPreviousPossibleIvs.get(i)));
            } else {
                mPossibleIvs.put(i, IVTools.calculatePossibleIVs(i, mNature, mStats[i], mEvs[i],
                        mLevel, mBaseStats[i]));
            }
        }
    }

	private void setStats() {
		if (mIvs == null || mEvs == null) {
			return;
		}
        if (mNature == null) {
            mNature = Natures.Nature.DOCILE;
        }
		mStats[STAT_INDEX_HP] = IVTools.getHpValue(mBaseStats[STAT_INDEX_HP], mEvs[STAT_INDEX_HP],
				mIvs[STAT_INDEX_HP], mLevel);
		mStats[STAT_INDEX_ATT] = IVTools.getStatValue(mBaseStats[STAT_INDEX_ATT],
                mEvs[STAT_INDEX_ATT], mIvs[STAT_INDEX_ATT], mLevel,
                Natures.getModifier(mNature, STAT_INDEX_ATT));
		mStats[STAT_INDEX_DEF] = IVTools.getStatValue(mBaseStats[STAT_INDEX_DEF],
                mEvs[STAT_INDEX_DEF], mIvs[STAT_INDEX_DEF], mLevel,
                Natures.getModifier(mNature, STAT_INDEX_DEF));
		mStats[STAT_INDEX_SP_ATT] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SP_ATT],
				mEvs[STAT_INDEX_SP_ATT], mIvs[STAT_INDEX_SP_ATT], mLevel,
				Natures.getModifier(mNature, STAT_INDEX_SP_ATT));
		mStats[STAT_INDEX_SP_DEF] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SP_DEF],
				mEvs[STAT_INDEX_SP_DEF], mIvs[STAT_INDEX_SP_DEF], mLevel,
				Natures.getModifier(mNature, STAT_INDEX_SP_DEF));
		mStats[STAT_INDEX_SPEED] = IVTools.getStatValue(mBaseStats[STAT_INDEX_SPEED],
				mEvs[STAT_INDEX_SPEED], mIvs[STAT_INDEX_SPEED], mLevel,
				Natures.getModifier(mNature, STAT_INDEX_SPEED));
	}
}
