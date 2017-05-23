package com.grumpus.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.grumpus.rogue.screen.PlayScreen;

public class RogueGame extends Game {

    public static final int VIEW_WIDTH = 320;
    public static final int VIEW_HEIGHT = 256;
    public static final int TILE_SIZE = 16;

	public static SpriteBatch batch;
	public static BitmapFont font;
	public static Texture tileset;

	public static Gson gson;

	@Override
	public void create () {
		// instantiate batch, font and tileset
        batch = new SpriteBatch();
        font = new BitmapFont(
        		Gdx.files.internal("fonts/press_start.fnt"),
				Gdx.files.internal("fonts/press_start.png"),
				false);
        font.getData().setScale(0.25f);
        tileset = new Texture("img/tileset.png");

        // instantiate static gson instance
        gson = new Gson();

		// start game screen
        setScreen(new PlayScreen());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
