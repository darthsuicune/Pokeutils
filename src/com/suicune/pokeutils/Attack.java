package com.suicune.pokeutils;

import com.suicune.pokeutils.database.PokeContract;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

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

	public Uri writeToDb(ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(PokeContract.AttacksTable.ATTACK_ACCURACY, mAccuracy);
		values.put(PokeContract.AttacksTable.ATTACK_DESCRIPTION, mDescription);
		values.put(PokeContract.AttacksTable.ATTACK_NAME, mName);
		values.put(PokeContract.AttacksTable.ATTACK_POWER, mPower);
		values.put(PokeContract.AttacksTable.ATTACK_PP, mPP);
		values.put(PokeContract.AttacksTable.ATTACK_TARGET, mTarget);
		return cr.insert(PokeContract.CONTENT_ATTACK, values);
	}
}
