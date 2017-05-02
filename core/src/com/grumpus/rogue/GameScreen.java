package com.grumpus.rogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grumpus.rogue.actions.Action;
import com.grumpus.rogue.actions.WalkAction;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private RogueGame game;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Actor player;
    private ArrayList<Actor> actors;

    public GameScreen(RogueGame game) {
        this.game = game;

        // load camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(RogueGame.VIEW_WIDTH, RogueGame.VIEW_HEIGHT, camera);

        // load map
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("test.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // for now, hardcode an actor
        actors = new ArrayList<Actor>();
        player = new Actor(game, 1, 1, 4, 2);
        actors.add(player);
    }

    public void processInput() {
        // move the player around
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.setAction(new WalkAction(player, -1, 0));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.setAction(new WalkAction(player, 1, 0));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.setAction(new WalkAction(player, 0, -1));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.setAction(new WalkAction(player, 0, 1));
        }
    }

    public void updateActors(float delta) {
        for (int i = 0; i < actors.size(); i++) {
            // get action for actor
            Actor currentActor = actors.get(i);
            Action currentAction = currentActor.getAction();

            // consume action, remove from actor
            if (currentAction != null) {
                currentAction.execute();
                currentActor.setAction(null);
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
        game.batch.setProjectionMatrix(camera.combined);

        // render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // process player input
        processInput();

        // update all actors
        updateActors(delta);

        // draw all actors
        game.batch.begin();
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).draw(delta);
        }
        game.batch.end();
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
