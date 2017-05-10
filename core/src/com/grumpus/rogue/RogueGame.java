package com.grumpus.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RogueGame extends Game {

    public static final int VIEW_WIDTH = 320;
    public static final int VIEW_HEIGHT = 240;
    public static final int TILE_SIZE = 16;

	public static SpriteBatch batch;
	public static Texture tileset;

	@Override
	public void create () {
		// instantiate batch and tileset
        batch = new SpriteBatch();
        tileset = new Texture("tileset.png");

		// start game screen
        setScreen(new GameScreen());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
