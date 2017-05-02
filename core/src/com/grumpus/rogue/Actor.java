package com.grumpus.rogue;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.actions.Action;

public class Actor {

    private RogueGame game;
    private TextureRegion textureRegion;
    public int x;
    public int y;

    private Action action;

    public Actor(RogueGame game, int tileX, int tileY, int regionTileX, int regionTileY) {
        this.game = game;
        textureRegion = new TextureRegion(game.tileset,
                regionTileX * RogueGame.TILE_SIZE,
                regionTileY * RogueGame.TILE_SIZE,
                RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);
        this.x = tileX * RogueGame.TILE_SIZE;
        this.y = tileY * RogueGame.TILE_SIZE;
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
