package com.suicune.pokeutils.app;


public class Ability {
    final public int mId;
	public String mName;
	public String mDescription;

    public Ability(final int id){
        mId = id;
        //TODO: ALL
        //String[] abilityNames = getResources().getStringArray(R.array.abilities);
    }
	
	public Ability(String name, String description){
        mId = 0;
		mName = name;
		mDescription = description;
	}

}
