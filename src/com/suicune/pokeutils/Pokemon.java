package com.suicune.pokeutils;

import java.util.ArrayList;

public class Pokemon {
	public String mName;
	public int mForm;
	public String mType1;
	public String mType2;
	public String mBaseHP;
	public String mBaseAtt;
	public String mBaseDef;
	public String mBaseSpAtt;
	public String mBaseSpDef;
	public String mBaseSpeed;
	public String mAbility1;
	public String mAbility2;
	public String mAbilityDW;
	public ArrayList<PokemonAttack> mAttacksList;
	public ArrayList<PokemonAttack> mCurrentAttacks;
	
	
	public Pokemon(String mName, int mForm, String mType1, String mType2,
			String mBaseHP, String mBaseAtt, String mBaseDef,
			String mBaseSpAtt, String mBaseSpDef, String mBaseSpeed,
			String mAbility1, String mAbility2, String mAbilityDW) {
		this.mName = mName;
		this.mForm = mForm;
		this.mType1 = mType1;
		this.mType2 = mType2;
		this.mBaseHP = mBaseHP;
		this.mBaseAtt = mBaseAtt;
		this.mBaseDef = mBaseDef;
		this.mBaseSpAtt = mBaseSpAtt;
		this.mBaseSpDef = mBaseSpDef;
		this.mBaseSpeed = mBaseSpeed;
		this.mAbility1 = mAbility1;
		this.mAbility2 = mAbility2;
		this.mAbilityDW = mAbilityDW;
		mAttacksList = new ArrayList<Pokemon.PokemonAttack>();
		mCurrentAttacks = new ArrayList<Pokemon.PokemonAttack>();
	}


	public class PokemonAttack{
		public String mAttackType;
		public String mAttackName;
		public String mPP;
		public String mAttackDescription;
		public String mAttack;
		
		
		public PokemonAttack(String mAttackType, String mAttackName,
				String mPP, String mAttackDescription, String mAttack) {
			this.mAttackType = mAttackType;
			this.mAttackName = mAttackName;
			this.mPP = mPP;
			this.mAttackDescription = mAttackDescription;
			this.mAttack = mAttack;
		}
	}

}
