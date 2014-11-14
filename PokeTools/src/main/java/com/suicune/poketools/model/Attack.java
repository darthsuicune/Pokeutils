package com.suicune.poketools.model;

/**
 * Created by lapuente on 17.09.14.
 */
public interface Attack {
	public static final String ARG_TYPE = "attacktype";
	public static final String ARG_PRIORITY = "attackpriority";
	public static final String ARG_PP = "attackpp";
	public static final String ARG_POWER = "attackpower";
	public static final String ARG_CLASS = "attackclass";
	public static final String ARG_ACCURACY = "attackaccuracy";

	public Type type();
	public boolean hasSpecialTreatment();
	public Category category();
	public int gen();
	public int attackClass();
	public int power();
	public int accuracy();
	public int pp();
	public int priority();
	public String name();
	public String description();

	public enum Category {
		PHYSICAL, SPECIAL, OTHER;

		public static Category fromClass(int attackClass) {
			switch(attackClass) {
				case 0:
					return PHYSICAL;
				case 1:
					return SPECIAL;
				default:
					return OTHER;
			}
		}
	}
}
