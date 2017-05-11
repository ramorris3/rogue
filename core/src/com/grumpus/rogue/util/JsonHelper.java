package com.grumpus.rogue.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.grumpus.rogue.RogueGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class of helpful methods for loading/saving JSON.
 */
public class JsonHelper {

    private static final String TAG = "JsonHelper";

    private static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            Gdx.app.error(TAG, e.getMessage());
            return null;
        }
    }

    public static JsonElement getJsonElement(String filename, String key) {
        String jsonStr = JsonHelper.readFile(filename);
        JsonObject jsonObj = RogueGame.gson.fromJson(jsonStr, JsonObject.class);
        return jsonObj.get(key);
    }

}
