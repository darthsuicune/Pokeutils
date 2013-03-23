package com.suicune.pokeutils;

import com.suicune.pokeutils.database.PokeContract;

import android.database.Cursor;

public class Attack {
	public static final int CLASS_OTHER = 0;
	public static final int CLASS_PYHISICAL = 1;
	public static final int CLASS_SPECIAL = 2;

	public int mId;
	public int mPp;
	public int mPower;
	public int mAccuracy;
	public int mType;
	public int mPriority;
	public int mAttackClass;

	public Attack(Cursor cursor) {
		if (cursor.moveToFirst()) {
			mId = cursor
					.getInt(cursor.getColumnIndex(PokeContract.Attacks._ID));
			mPp = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.PP)));
			mPower = cursor.getInt(cursor
					.getColumnIndex(PokeContract.Attacks.POWER));
			mAccuracy = cursor.getInt(cursor
					.getColumnIndex(PokeContract.Attacks.ACCURACY));
			mType = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.TYPE)));
			mPriority = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.PRIORITY)));
			mAttackClass = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex(PokeContract.Attacks.CLASS)));
		}
	}

	public Attack(int id, int pp, int power, int accuracy, int type, int priority,
			int attackClass) {
		mId = id;
		mPp = pp;
		mPower = power;
		mAccuracy = accuracy;
		mType = type;
		mPriority = priority;
		mAttackClass = attackClass;
	}
}
