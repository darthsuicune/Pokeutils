package com.suicune.pokeutils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.suicune.pokeutils.database.PokeContract;

public class Ability {
	public String mName;
	public String mDescription;
	
	public Ability(String name, String description){
		mName = name;
		mDescription = description;
	}
	
	public Uri writeToDb(ContentResolver cr){
		ContentValues values = new ContentValues();
		values.put(PokeContract.AbilitiesTable.ABILITY_NAME, mName);
		values.put(PokeContract.AbilitiesTable.ABILITY_DESCRIPTION, mDescription);
		return cr.insert(PokeContract.CONTENT_ABILITY, values);
	}

}
