package com.grumpus.rogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grumpus.rogue.actor.Action;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.actor.OpenDoorAction;
import com.grumpus.rogue.actor.WalkAction;
import com.grumpus.rogue.stage.Stage;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private FitViewport viewport;

    private Stage stage;
    private ArrayList<Actor> actors;
    private Actor player;

    public GameScreen() {
        // load camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(RogueGame.VIEW_WIDTH, RogueGame.VIEW_HEIGHT, camera);

        // load map and stage
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("test.tmx");
        stage = new Stage(map);

        // for now, hardcode a player's position
        actors = new ArrayList<Actor>();
        player = new Actor("player", 1, 1);
        actors.add(player);
    }

    private Action processInput() {
        // get direction from key presses
        int xDir = 0;
        int yDir = 0;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            xDir = -1;
            yDir = 0;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            xDir = 1;
            yDir = 0;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            xDir = 0;
            yDir = -1;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            xDir = 0;
            yDir = 1;
        }

        // get target tile position
        int tx = player.getTileX() + xDir;
        int ty = player.getTileY() + yDir;


        // first, check if there's a door there
        if (stage.isLayerAt("door", tx, ty)) {
            // there's a door here, so return an open-door action
            return new OpenDoorAction(stage, tx, ty);
        }

        // now, check for solids here
        if (!stage.isLayerAt("solid", tx, ty)) {
            // there's no solid here, so player can move
            return new WalkAction(player, xDir, yDir);
        }

        // default, return no action
        return null;
    }

    private void updateActors(float delta) {
        for (Actor actor : actors ) {
            // get action for actor
            Action currentAction = actor.getAction();

            // consume action, remove from actor
            if (currentAction != null) {
                currentAction.execute();
                actor.setAction(null);
            }

        }
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update camera
        camera.update();
        RogueGame.batch.setProjectionMatrix(camera.combined);

        // process player input
        Action playerAction = processInput();
        player.setAction(playerAction);

        // update all actors
        updateActors(delta);

        RogueGame.batch.begin();

        // draw stage
        stage.draw();

        // draw all actors
        for (Actor actor : actors) {
            actor.draw(delta);
        }

        // draw UI
        RogueGame.font.draw(RogueGame.batch,
                "HP: " + player.hp + "/" + player.maxHp,
                0, RogueGame.VIEW_HEIGHT - 5);

        RogueGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
