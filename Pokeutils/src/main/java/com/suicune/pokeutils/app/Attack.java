package com.suicune.pokeutils.app;

import android.content.Context;
import com.suicune.pokeutils.R;

public class Attack {
	public static final int CLASS_OTHER = 0;
	public static final int CLASS_PHYSICAL = 1;
	public static final int CLASS_SPECIAL = 2;

	final public int mId;
    final public String mName;
	final public int mPp;
	public int mPower;
	final public int mAccuracy;
	final public Types.Type mType;
	final public int mPriority;
	final public int mAttackClass;

    public Attack(Context context, final int id){
        mId = id;
        //TODO: ALL
        mName = context.getResources().getStringArray(R.array.attack_names)[id];
        context.getResources().getIntArray(R.array.attack_names);
        mPp = 0;
        mPower = 0;
        mAccuracy = 0;
        mType = Types.Type.NONE;
        mPriority = -1;
        mAttackClass = CLASS_OTHER;
    }
}
