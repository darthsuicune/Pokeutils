package com.suicune.pokeutils;

public class TeamPokemon extends Pokemon {
	public TeamPokemon(String mName, int mForm, String mType1, String mType2,
			String mBaseHP, String mBaseAtt, String mBaseDef,
			String mBaseSpAtt, String mBaseSpDef, String mBaseSpeed,
			String mAbility1, String mAbility2, String mAbilityDW) {
		super(mName, mForm, mType1, mType2, mBaseHP, mBaseAtt, mBaseDef, mBaseSpAtt,
				mBaseSpDef, mBaseSpeed, mAbility1, mAbility2, mAbilityDW);
		// TODO Auto-generated constructor stub
	}

	public static final int HP = 0;
	public static final int ATT = 1;
	public static final int DEF = 2;
	public static final int SPATT = 3;
	public static final int SPDEF = 4;
	public static final int SPEED = 5;
	
	private Attack mAttack1 = null;
	private Attack mAttack2 = null;
	private Attack mAttack3 = null;
	private Attack mAttack4 = null;

	private Ability mAbility;

	private Item mAttachedItem;

	public String mNickname;

	public int mLevel;

	public int[] mIVs;
	public int[] mEVs;

	public void setAttack(int number, Attack attack) {
		switch (number) {
		case 1:
			mAttack1 = attack;
			break;
		case 2:
			mAttack2 = attack;
			break;
		case 3:
			mAttack3 = attack;
			break;
		case 4:
			mAttack4 = attack;
			break;
		}
	}

	public Attack getAttack(int number) {
		switch (number) {
		case 1:
			return mAttack1;
		case 2:
			return mAttack2;
		case 3:
			return mAttack3;
		case 4:
			return mAttack4;
		default:
			return null;
		}
	}

	public void setIv(int type, int iv) {
		mIVs[type] = iv;
	}

	public void setEv(int type, int ev) {
		mEVs[type] = ev;
	}

	public void setAbility(Ability ability) {
		mAbility = ability;
	}

	public Ability getAbility() {
		return mAbility;
	}

	public void setItem(Item item) {
		mAttachedItem = item;
	}

	public Item getItem() {
		return mAttachedItem;
	}

	public void setNickname(String nickname) {
		mNickname = nickname;
	}

	public void setLevel(int level) {
		mLevel = level;
	}
}
