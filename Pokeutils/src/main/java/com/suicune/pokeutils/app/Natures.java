package com.suicune.pokeutils.app;


import com.suicune.pokeutils.R;

public class Natures {
	public static final int NATURES_COUNT = 25;

	// Neutral natures
	public static final int HARDY = 0;
	public static final int BASHFUL = 1;
	public static final int DOCILE = 2;
	public static final int QUIRKY = 3;
	public static final int SERIOUS = 4;

	// +Attack
	public static final int LONELY = 5; // -Defense
	public static final int BRAVE = 6; // -Speed
	public static final int ADAMANT = 7; // -Special attack
	public static final int NAUGHTY = 8; // -Special defense

	// +Defense
	public static final int BOLD = 9; // -Attack
	public static final int RELAXED = 10; // -Speed
	public static final int IMPISH = 11; // -Special attack
	public static final int LAX = 12; // -Special defense

	// +Speed
	public static final int TIMID = 13; // -Attack
	public static final int HASTY = 14; // -Defense
	public static final int JOLLY = 15; // -Special attack
	public static final int NAIVE = 16; // -Special defense

	// +Special attack
	public static final int MODEST = 17; // -Attack
	public static final int MILD = 18; // -Defense
	public static final int QUIET = 19; // -Speed
	public static final int RASH = 20; // -Special defense

	// +Special defense
	public static final int CALM = 21; // -Attack
	public static final int GENTLE = 22; // -Defense
	public static final int SASSY = 23; // -Speed
	public static final int CAREFUL = 24; // -Special attack

	public static int getNatureName(int nature) {
		switch (nature) {
		case HARDY:
			return Hardy.NAME;
		case BASHFUL:
			return Bashful.NAME;
		case DOCILE:
			return Docile.NAME;
		case QUIRKY:
			return Quirky.NAME;
		case SERIOUS:
			return Serious.NAME;
		case LONELY:
			return Lonely.NAME;
		case BRAVE:
			return Brave.NAME;
		case ADAMANT:
			return Adamant.NAME;
		case NAUGHTY:
			return Naughty.NAME;
		case BOLD:
			return Bold.NAME;
		case RELAXED:
			return Relaxed.NAME;
		case IMPISH:
			return Impish.NAME;
		case LAX:
			return Lax.NAME;
		case TIMID:
			return Timid.NAME;
		case HASTY:
			return Hasty.NAME;
		case JOLLY:
			return Jolly.NAME;
		case NAIVE:
			return Naive.NAME;
		case MODEST:
			return Modest.NAME;
		case MILD:
			return Mild.NAME;
		case QUIET:
			return Quiet.NAME;
		case RASH:
			return Rash.NAME;
		case CALM:
			return Calm.NAME;
		case GENTLE:
			return Gentle.NAME;
		case SASSY:
			return Sassy.NAME;
		case CAREFUL:
			return Careful.NAME;
		}
		return 0;
	}

	public static class Hardy {
		public static int NAME = R.string.nature_hardy;
	}

	public static class Bashful {
		public static int NAME = R.string.nature_bashful;
	}

	public static class Docile {
		public static int NAME = R.string.nature_docile;
	}

	public static class Quirky {
		public static int NAME = R.string.nature_quirky;
	}

	public static class Serious {
		public static int NAME = R.string.nature_serious;
	}

	public static class Lonely {
		public static int NAME = R.string.nature_lonely;
	}

	public static class Brave {
		public static int NAME = R.string.nature_brave;
	}

	public static class Adamant {
		public static int NAME = R.string.nature_adamant;
	}

	public static class Naughty {
		public static int NAME = R.string.nature_naughty;
	}

	public static class Bold {
		public static int NAME = R.string.nature_bold;
	}

	public static class Relaxed {
		public static int NAME = R.string.nature_relaxed;
	}

	public static class Impish {
		public static int NAME = R.string.nature_impish;
	}

	public static class Lax {
		public static int NAME = R.string.nature_lax;
	}

	public static class Timid {
		public static int NAME = R.string.nature_timid;
	}

	public static class Hasty {
		public static int NAME = R.string.nature_hasty;
	}

	public static class Jolly {
		public static int NAME = R.string.nature_jolly;
	}

	public static class Naive {
		public static int NAME = R.string.nature_naive;
	}

	public static class Modest {
		public static int NAME = R.string.nature_modest;
	}

	public static class Mild {
		public static int NAME = R.string.nature_mild;
	}

	public static class Quiet {
		public static int NAME = R.string.nature_quiet;
	}

	public static class Rash {
		public static int NAME = R.string.nature_rash;
	}

	public static class Calm {
		public static int NAME = R.string.nature_calm;
	}

	public static class Gentle {
		public static int NAME = R.string.nature_gentle;
	}

	public static class Sassy {
		public static int NAME = R.string.nature_sassy;
	}

	public static class Careful {
		public static int NAME = R.string.nature_careful;
	}
}
