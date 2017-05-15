package com.grumpus.rogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.data.ActorData;

public class Actor {

    private TextureRegion textureRegion;
    private int x;
    private int y;

    public int hp;
    public int maxHp;

    public int attack;

    private Action action;

    public Actor(TextureRegion textureRegion, int x, int y, ActorData data) {
        // set graphic and position
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;

        // load stats from data
        maxHp = data.hp;
        hp = data.hp;
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
