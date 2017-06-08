package com.grumpus.rogue.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.dungeon.Dungeon;

public class PlayScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private FitViewport viewport;

    public Dungeon dungeon;
    private Player player;

    public PlayScreen() {
        // load camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(RogueGame.VIEW_WIDTH, RogueGame.VIEW_HEIGHT, camera);

        // hardcode player for now
        // TODO: figure out player loading/placement on room transition
        player = new Player(
                new TextureRegion(
                        RogueGame.tileset,
                        4 * RogueGame.TILE_SIZE,
                        2 * RogueGame.TILE_SIZE,
                        RogueGame.TILE_SIZE, RogueGame.TILE_SIZE),
                5 * RogueGame.TILE_SIZE, RogueGame.TILE_SIZE);

        // TODO: remove this dungeon generation test
        dungeon = new Dungeon("foo", player);
    }

    /**
     * Main game loop.  This is where all the frame-by-frame logic and all drawing happens.
     * @param delta The time since the last frame.
     */
    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update camera
        camera.update();
        RogueGame.batch.setProjectionMatrix(camera.combined);

        // update player if not dead
        if (!player.isDead()) {
            player.processInput(dungeon);
            if (player.hasNextAction()) {
                player.getNextAction().execute();
                player.setNextAction(null);

                // update monsters only if player has taken a turn
                dungeon.room.updateMonsters();
            }
        }

        RogueGame.batch.begin();

        // draw message log
        RogueGame.messageLog.draw();

        // draw level and monsters
        dungeon.room.drawLevel();
        dungeon.room.drawMonsters();

        // draw player if not dead
        if (!player.isDead()) {
            player.draw();
        }

        // draw effects
        dungeon.room.drawEffects(delta);

        // draw UI
        RogueGame.font.draw(RogueGame.batch,
                "HP: " + player.hp + "/" + player.maxHp,
                2, RogueGame.HUD_Y);

        RogueGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
