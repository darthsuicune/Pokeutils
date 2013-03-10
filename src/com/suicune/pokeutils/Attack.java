package com.suicune.pokeutils;

import com.suicune.pokeutils.database.PokeContract;

import android.database.Cursor;

public class Attack {
	public String mName;
	public int mPP;
	public int mPower;
	public int mAccuracy;
	public int mType;
	public String mTarget;
	public String mDescription;

	public Attack(Cursor cursor) {
		if (cursor.moveToFirst()) {
			mName = cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.NAME));
			mPP = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.PP)));
			mPower = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.POWER)));
			mAccuracy = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.ACCURACY)));
			mType = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.TYPE)));
			mTarget = cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.TARGET));
			mDescription = cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.DESCRIPTION));
		}
	}

	public Attack(String name, int pp, int power, int accuracy, int type,
			String target, String description) {
		mName = name;
		mPP = pp;
		mPower = power;
		mAccuracy = accuracy;
		mType = type;
		mTarget = target;
		mDescription = description;
	}
}
