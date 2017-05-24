package com.grumpus.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.grumpus.rogue.screen.PlayScreen;
import com.grumpus.rogue.ui.MessageLog;

public class RogueGame extends Game {

    // ui constants
    public static final int TILE_SIZE = 16;
    public static final int LOG_HEIGHT = 80;
    public static final int STAGE_Y = LOG_HEIGHT;
    public static final int VIEW_WIDTH = 320;
    public static final int VIEW_HEIGHT = 240 + TILE_SIZE + LOG_HEIGHT;
    public static final int HUD_Y = VIEW_HEIGHT - 5;


	public static SpriteBatch batch;
	public static BitmapFont font;
	public static Texture tileset;
	public static MessageLog messageLog;

	public static Gson gson;

	@Override
	public void create () {
        batch = new SpriteBatch();

        font = new BitmapFont(
        		Gdx.files.internal("fonts/press_start.fnt"),
				Gdx.files.internal("fonts/press_start.png"),
				false);
        font.getData().setScale(0.25f);

        tileset = new Texture("img/tileset.png");

        messageLog = new MessageLog();

        gson = new Gson();

		// start game screen
        setScreen(new PlayScreen());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
