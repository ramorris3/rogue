package com.grumpus.rogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonElement;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.data.ActorData;
import com.grumpus.rogue.util.JsonHelper;

public class Actor {

    private TextureRegion textureRegion;
    private int x;
    private int y;

    public int hp;
    public int maxHp;

    public int attack;

    private Action action;

    public Actor(String key, int gridX, int gridY) {
        loadFromJson(key);
        this.x = gridX * RogueGame.TILE_SIZE;
        this.y = gridY * RogueGame.TILE_SIZE;
    }

    private void loadFromJson(String key) {
        // load the actor object from json
        JsonElement jsonElm = JsonHelper.getJsonElement("data/creatures.json", key);
        ActorData data = RogueGame.gson.fromJson(jsonElm, ActorData.class);

        // extract needed variables
        textureRegion = new TextureRegion(RogueGame.tileset,
                data.tileX * RogueGame.TILE_SIZE,
                data.tileY * RogueGame.TILE_SIZE,
                RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);
        hp = data.hp;
        maxHp = data.hp;
        attack = data.attack;
    }

    public void setTileX(int tx) {
        x = tx * RogueGame.TILE_SIZE;
    }

    public int getTileX() {
        return x / RogueGame.TILE_SIZE;
    }

    public int getTileY() {
        return y / RogueGame.TILE_SIZE;
    }

    public void setTileY(int ty) {
        y = ty * RogueGame.TILE_SIZE;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void draw(float deltaTime) {
        RogueGame.batch.draw(textureRegion, x, y);
    }

}
