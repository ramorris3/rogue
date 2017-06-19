package com.grumpus.rogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.grumpus.rogue.screen.TitleScreen;
import com.grumpus.rogue.ui.MessageLog;

public class RogueGame extends Game {

    // ui constants
    public static final int TILE_SIZE = 16;
    public static final int LOG_HEIGHT = 80;
    public static final int ROOM_Y = LOG_HEIGHT;
	public static final int ROOM_WIDTH = 336;
	public static final int ROOM_TILE_WIDTH = ROOM_WIDTH / TILE_SIZE;
	public static final int ROOM_HEIGHT = 240;
	public static final int ROOM_TILE_HEIGHT = ROOM_HEIGHT / TILE_SIZE;
	public static final int VIEW_WIDTH = ROOM_WIDTH;
	public static final int VIEW_HEIGHT = ROOM_HEIGHT + TILE_SIZE + LOG_HEIGHT;
	public static final int HUD_Y = VIEW_HEIGHT - 5;


	public static SpriteBatch batch;
	public static BitmapFont font;
	public static Texture tileset;
	public static MessageLog messageLog;
	public static NinePatch border;

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

        border = new NinePatch(new Texture("img/ui/border.png"),
                16, 16, 16, 16);

        gson = new Gson();

		// start game screen
        setScreen(new TitleScreen(this));
	}

	public void goToScreen(ScreenAdapter screen, Boolean saveMessages) {
	    if (!saveMessages) {
            messageLog.clear();
        }
	    setScreen(screen);
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
