package com.suicune.pokeutils;


public class Attack {
	public String mName;
	public int mPP;
	public int mPower;
	public int mAccuracy;
	public String mTarget;
	public String mDescription;

	public Attack(String name, int pp, int power, int accuracy, String target,
			String description) {
		mName = name;
		mPP = pp;
		mPower = power;
		mAccuracy = accuracy;
		mTarget = target;
		mDescription = description;
	}
}
