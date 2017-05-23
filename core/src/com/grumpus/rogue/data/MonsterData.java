package com.grumpus.rogue.data;

import com.grumpus.rogue.actor.Actor;

/**
 * Serializable bag of data for action.  Useful for creating {@link Actor} instance from json.
 */
public class MonsterData {
    public int hp;
    public int attack;
    public int defense;
    public int agility;
}
