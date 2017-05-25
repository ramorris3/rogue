package com.grumpus.rogue.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;

/**
 * Static class of hardcoded TextureRegion objects, for easy access
 */
public class TileGraphics {

    public static final TextureRegion OPEN_DOOR = new TextureRegion(
            RogueGame.tileset,
            6 * RogueGame.TILE_SIZE, 2 * RogueGame.TILE_SIZE,
            RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);

    public static final TextureRegion CLOSED_DOOR = new TextureRegion(
            RogueGame.tileset,
            4 * RogueGame.TILE_SIZE, 4 * RogueGame.TILE_SIZE,
            RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);
}
