package com.grumpus.rogue.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grumpus.rogue.RogueGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// set screen config
		config.width = 1008;
		config.height = 1008;
//		config.fullscreen = true;

		Gdx.app = new LwjglApplication(new RogueGame(), config);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
//		Gdx.app.setLogLevel(Application.LOG_ERROR);
	}
}
