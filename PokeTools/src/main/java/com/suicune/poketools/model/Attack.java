package com.suicune.poketools.model;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Attack {
	public Type type();
	public boolean hasSpecialTreatment();
	public String category();
	public int power();
	public int accuracy();
	public int nameResId();
	public int descriptionResId();
}
