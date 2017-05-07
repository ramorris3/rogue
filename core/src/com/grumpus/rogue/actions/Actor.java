package com.grumpus.rogue.actions;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;

public class Actor {

    private RogueGame game;
    private TextureRegion textureRegion;
    public int x;
    public int y;

    private Action action;

    public Actor(RogueGame game, int gridX, int gridY, int tileX, int tileY) {
        this.game = game;
        textureRegion = new TextureRegion(game.tileset,
                tileX * RogueGame.TILE_SIZE,
                tileY * RogueGame.TILE_SIZE,
                RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);
        this.x = gridX * RogueGame.TILE_SIZE;
        this.y = gridY * RogueGame.TILE_SIZE;
    }

    public int getTileX() {
        return x / RogueGame.TILE_SIZE;
    }

    public int getTileY() {
        return y / RogueGame.TILE_SIZE;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void draw(float deltaTime) {
        game.batch.draw(textureRegion, x, y);
    }

}
