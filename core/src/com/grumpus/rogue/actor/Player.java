package com.grumpus.rogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.action.Action;
import com.grumpus.rogue.action.OpenDoorAction;
import com.grumpus.rogue.action.WalkAction;
import com.grumpus.rogue.stage.Stage;

public class Player extends Actor {

    public Player(TextureRegion textureRegion, int x, int y) {
        super(textureRegion, x, y, 5, 1, 1, 1);
    }

    /**
     * Input processing function for handling player input.  Gets the player action's nextAction
     * based on input.
     */
    public void processInput(Stage stage) {
        // player already has an action waiting for execution
        if (hasNextAction()) return;

        int xDir = 0;
        int yDir = 0;

        // poll for input
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
        } else {
            // no input, set action to null and return
            setNextAction(null);
            return;
        }

        // get target tile position
        int tx = getTileX() + xDir;
        int ty = getTileY() + yDir;

        // check for door, then check for solid
        if (stage.isLayerAt("door", tx, ty)) {
            // there's a door here, so return an open-door nextAction
            setNextAction(new OpenDoorAction(stage, tx, ty));
        } else if (!stage.isLayerAt("solid", tx, ty)) {
            // there's no solid here, so player can move
            setNextAction(new WalkAction(this, xDir, yDir));
        }
    }

}
