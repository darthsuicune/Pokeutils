package com.suicune.poketools.model;

import android.os.Bundle;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Ability {
	public static final String ARG_NAME = "name";
	public static final String ARG_CODE = "code";
	public static final String ARG_DESCRIPTION = "description";
	public int id();
	public String name();
	public String description();
	public Bundle save();
}
