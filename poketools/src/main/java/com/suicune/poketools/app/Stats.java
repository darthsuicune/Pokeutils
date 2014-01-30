package com.suicune.poketools.app;

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
    public int hp;
    public int attack;
    public int defense;
    public int specialAttack;
    public int specialDefense;
    public int speed;

    public StatType statType;

    public Stats(StatType statType, int hp, int attack, int defense, int specialAttack,
                 int specialDefense, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
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
        if (hp > max || hp < min) {
            hp = min;
        }
        if (attack > max || attack < min) {
            attack = min;
        }
        if (defense > max || defense < min) {
            defense = min;
        }
        if (specialAttack > max || specialAttack < min) {
            specialAttack = min;
        }
        if (specialDefense > max || specialDefense < min) {
            specialDefense = min;
        }
        if (speed > max || speed < min) {
            speed = min;
        }
    }

    public enum StatType {
        EV,
        IV,
        BASE,
        VALUE
    }

}
