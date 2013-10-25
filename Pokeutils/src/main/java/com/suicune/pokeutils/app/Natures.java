package com.suicune.pokeutils.app;


import com.suicune.pokeutils.App;
import com.suicune.pokeutils.R;

public class Natures {
    public static final int NATURES_COUNT = 25;
    public static final int MODIFIER_POSITIVE = 110;
    public static final int MODIFIER_NEUTRAL = 100;
    public static final int MODIFIER_NEGATIVE = 90;

    public static int getModifier(Nature nature, int stat){
        if(nature == null){
            nature = Nature.DOCILE;
        }
        switch (stat) {
            case Pokemon.STAT_INDEX_ATT:
                if (nature == Natures.Nature.ADAMANT || nature == Natures.Nature.LONELY
                        || nature == Natures.Nature.BRAVE || nature == Natures.Nature.NAUGHTY) {
                    return MODIFIER_POSITIVE;
                } else if (nature == Natures.Nature.BOLD || nature == Natures.Nature.TIMID
                        || nature == Natures.Nature.MODEST || nature == Natures.Nature.CALM) {
                    return MODIFIER_NEGATIVE;
                }
                break;
            case Pokemon.STAT_INDEX_DEF:
                if (nature == Natures.Nature.BOLD || nature == Natures.Nature.RELAXED
                        || nature == Natures.Nature.IMPISH || nature == Natures.Nature.LAX) {
                    return MODIFIER_POSITIVE;
                } else if (nature == Natures.Nature.LONELY || nature == Natures.Nature.HASTY
                        || nature == Natures.Nature.MILD || nature == Natures.Nature.GENTLE) {
                    return MODIFIER_NEGATIVE;
                }
                break;
            case Pokemon.STAT_INDEX_SP_ATT:
                if (nature == Natures.Nature.MODEST || nature == Natures.Nature.MILD
                        || nature == Natures.Nature.QUIET || nature == Natures.Nature.RASH) {
                    return MODIFIER_POSITIVE;
                } else if (nature == Natures.Nature.ADAMANT || nature == Natures.Nature.IMPISH
                        || nature == Natures.Nature.JOLLY || nature == Natures.Nature.CAREFUL) {
                    return MODIFIER_NEGATIVE;
                }
                break;
            case Pokemon.STAT_INDEX_SP_DEF:
                if (nature == Natures.Nature.CALM || nature == Natures.Nature.GENTLE
                        || nature == Natures.Nature.SASSY || nature == Natures.Nature.CAREFUL) {
                    return MODIFIER_POSITIVE;
                } else if (nature == Natures.Nature.NAUGHTY || nature == Natures.Nature.LAX
                        || nature == Natures.Nature.NAIVE || nature == Natures.Nature.RASH) {
                    return MODIFIER_NEGATIVE;
                }
                break;
            case Pokemon.STAT_INDEX_SPEED:
                if (nature == Natures.Nature.TIMID || nature == Natures.Nature.HASTY
                        || nature == Natures.Nature.JOLLY || nature == Natures.Nature.NAIVE) {
                    return MODIFIER_POSITIVE;
                } else if (nature == Natures.Nature.BRAVE || nature == Natures.Nature.RELAXED
                        || nature == Natures.Nature.QUIET || nature == Natures.Nature.SASSY) {
                    return MODIFIER_NEGATIVE;
                }
                break;
        }
        return MODIFIER_NEUTRAL;
    }

    public static Nature getNature(int id){
        return Nature.values()[id];
    }

    public enum Nature {
        // Neutral natures
        HARDY(R.string.nature_hardy),
        BASHFUL(R.string.nature_bashful),
        DOCILE(R.string.nature_docile),
        QUIRKY(R.string.nature_quirky),
        SERIOUS(R.string.nature_serious),

        // +Attack
        LONELY(R.string.nature_lonely), // -Defense
        BRAVE(R.string.nature_brave), // -Speed
        ADAMANT(R.string.nature_adamant), // -Special attack
        NAUGHTY(R.string.nature_naughty), // -Special defense

        // +Defense
        BOLD(R.string.nature_bold), // -Attack
        RELAXED(R.string.nature_relaxed), // -Speed
        IMPISH(R.string.nature_impish), // -Special attack
        LAX(R.string.nature_lax), // -Special defense

        // +Speed
        TIMID(R.string.nature_timid), // -Attack
        HASTY(R.string.nature_hasty), // -Defense
        JOLLY(R.string.nature_jolly), // -Special attack
        NAIVE(R.string.nature_naive), // -Special defense

        // +Special attack
        MODEST(R.string.nature_modest), // -Attack
        MILD(R.string.nature_mild), // -Defense
        QUIET(R.string.nature_quiet), // -Speed
        RASH(R.string.nature_rash), // -Special defense

        // +Special defense
        CALM(R.string.nature_calm), // -Attack
        GENTLE(R.string.nature_gentle), // -Defense
        SASSY(R.string.nature_sassy), // -Speed
        CAREFUL(R.string.nature_careful); // -Special attack

        public final int mName;

        @Override
        public String toString(){
            return App.getResourceString(mName);
        }

        private Nature(int name) {
            mName = name;
        }
    }
}
