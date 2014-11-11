package com.suicune.poketools.model;

import java.util.List;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Type {
	public int nameResId();
	public List<Type> weaknesses();
	public List<Type> resistances();
	public List<Type> immunities();
	public int save();
}
