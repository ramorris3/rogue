package com.grumpus.rogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;

public class Actor {

    private TextureRegion textureRegion;
    private int x;
    private int y;

    private Action action;

    public Actor(int gridX, int gridY, int tileX, int tileY) {
        textureRegion = new TextureRegion(RogueGame.tileset,
                tileX * RogueGame.TILE_SIZE,
                tileY * RogueGame.TILE_SIZE,
                RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);
        this.x = gridX * RogueGame.TILE_SIZE;
        this.y = gridY * RogueGame.TILE_SIZE;
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
