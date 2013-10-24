package com.suicune.pokeutils.app;

import android.content.Context;
import com.suicune.pokeutils.R;

public class Ability {
    final public int mId;
	final public String mName;

    public Ability(Context context, final int id){
        mId = id;
        mName = context.getResources().getStringArray(R.array.abilities)[id];
    }
}
