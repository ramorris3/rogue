package com.grumpus.rogue.data;

import com.google.gson.JsonElement;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.util.JsonHelper;

/**
 * A class with helpful static functions for
 * loading objects from JSON data.
 */
public class DataLoader {

    public static MonsterData loadMonsterData(String key) {
        // load the action data object from json
        JsonElement jsonElm = JsonHelper.getJsonElement("data/monsters.json", key);
        return RogueGame.gson.fromJson(jsonElm, MonsterData.class);
    }

}
