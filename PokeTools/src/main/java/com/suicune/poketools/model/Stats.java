package com.suicune.poketools.model;

import java.util.Map;

/**
 * Created by denis on min1.min1.14.
 */
public class Stats {
    private static final int MAX_EV = 255;
    private static final int MAX_IV = 31;
    private static final int MAX_BASE = 255;
    private static final int MIN_EV = 0;
    private static final int MIN_IV = 0;
    private static final int MIN_BASE = 1;
    public Map<Stat, Integer> values;

    public StatType statType;

    public Stats(StatType statType){
        this.statType = statType;
        if(statType == StatType.BASE || statType == StatType.VALUE){
            values.put(Stat.HP, MIN_BASE);
            values.put(Stat.ATTACK, MIN_BASE);
            values.put(Stat.DEFENSE, MIN_BASE);
            values.put(Stat.SPECIAL_ATTACK, MIN_BASE);
            values.put(Stat.SPECIAL_DEFENSE, MIN_BASE);
            values.put(Stat.SPECIAL_ATTACK, MIN_BASE);
        }
    }

    public Stats(StatType statType, int hp, int attack, int defense, int specialAttack,
                 int specialDefense, int speed) {
        values.put(Stat.HP, hp);
        values.put(Stat.ATTACK, attack);
        values.put(Stat.DEFENSE, defense);
        values.put(Stat.SPECIAL_ATTACK, specialAttack);
        values.put(Stat.SPECIAL_DEFENSE, specialDefense);
        values.put(Stat.SPEED, speed);
        checkForValidValues();
    }

    private void checkForValidValues() {
        switch (statType) {
            case EV:
                checkForValidValues(MAX_EV, MIN_EV);
                break;
            case IV:
                checkForValidValues(MAX_IV, MIN_IV);
                break;
            case BASE:
                checkForValidValues(MAX_BASE, MIN_BASE);
                break;
            default:
                break;
        }
    }
    
    private void checkForValidValues(int max, int min){
        if (values.get(Stat.HP) > max || values.get(Stat.HP) < min) {
            values.put(Stat.HP, min);
        }
        if (values.get(Stat.ATTACK) > max || values.get(Stat.ATTACK) < min) {
            values.put(Stat.ATTACK, min);
        }
        if (values.get(Stat.DEFENSE) > max || values.get(Stat.DEFENSE) < min) {
            values.put(Stat.DEFENSE, min);
        }
        if (values.get(Stat.SPECIAL_ATTACK) > max || values.get(Stat.SPECIAL_ATTACK) < min) {
            values.put(Stat.SPECIAL_ATTACK, min);
        }
        if (values.get(Stat.SPECIAL_DEFENSE) > max || values.get(Stat.SPECIAL_DEFENSE) < min) {
            values.put(Stat.SPECIAL_DEFENSE, min);
        }
        if (values.get(Stat.SPEED) > max || values.get(Stat.SPEED) < min) {
            values.put(Stat.SPEED, min);
        }
    }

    public enum StatType {
        EV,
        IV,
        BASE,
        VALUE
    }

    public enum Stat {
        HP,
        ATTACK,
        DEFENSE,
        SPECIAL_ATTACK,
        SPECIAL_DEFENSE,
        SPEED;
    }

}
