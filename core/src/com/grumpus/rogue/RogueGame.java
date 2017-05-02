package com.grumpus.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RogueGame extends Game {

    public static final int VIEW_WIDTH = 320;
    public static final int VIEW_HEIGHT = 240;

    public static final int TILE_SIZE = 16;

	public SpriteBatch batch;
	public Texture tileset;

	@Override
	public void create () {
		// create spritebatch and load tileset texture
	    batch = new SpriteBatch();
		tileset = new Texture("tileset.png");

		// start game screen
        setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
