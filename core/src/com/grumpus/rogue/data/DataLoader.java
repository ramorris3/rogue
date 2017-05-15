package com.grumpus.rogue.data;

import com.google.gson.JsonElement;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.util.JsonHelper;

/**
 * A class with helpful static functions for
 * loading objects from JSON data.
 */
public class DataLoader {

    public static ActorData loadActorData(String key) {
        // load the actor data object from json
        JsonElement jsonElm = JsonHelper.getJsonElement("data/creatures.json", key);
        return RogueGame.gson.fromJson(jsonElm, ActorData.class);
    }

}
