package com.grumpus.rogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.action.AttackAction;
import com.grumpus.rogue.action.OpenDoorAction;
import com.grumpus.rogue.action.WalkAction;
import com.grumpus.rogue.dungeon.Doorway;
import com.grumpus.rogue.dungeon.Dungeon;
import com.grumpus.rogue.dungeon.Room;

public class Player extends Actor {

    private boolean dead;

    public Player(TextureRegion textureRegion, int x, int y) {
        super("you", textureRegion, x, y, 15, 5, 5, 1);
        dead = false;
    }

    @Override
    public void die(Room room) {
        // TODO: add player death animation here
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Input processing function for handling player input.  Gets the player action's nextAction
     * based on input.
     */
    public void processInput(Dungeon dungeon) {
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
        int currX = getTileX();
        int currY = getTileY();
        int tarX = currX + xDir;
        int tarY = currY + yDir;

        // check if trying to move outside the screen, and standing on a doorway
        if (dungeon.room.isLayerAt("doorway", currX, currY)
                && dungeon.room.isOutOfBounds(tarX, tarY)) {
            // get the doorway instance and perform room transition
            Doorway dway = (Doorway)dungeon.room.getTextureRegion("doorway", currX, currY);
            dungeon.changeRooms(dway);
            return;
        }

        // check if blocked
        if (dungeon.room.isBlockedForPlayer(tarX, tarY)) {
            // check for monster
            Monster m = dungeon.room.getMonsterAt(tarX, tarY);
            if (m != null) {
                setNextAction(new AttackAction(this, m, dungeon.room,
                        Color.WHITE, Color.YELLOW));
            // check for door
            } else if (dungeon.room.isLayerAt("door", tarX, tarY)) {
                setNextAction(new OpenDoorAction(dungeon.room, tarX, tarY));
            }
            // otherwise, it's solid, don't do anything
        } else {
            // walk to next tile
            setNextAction(new WalkAction(this, xDir, yDir));
        }
    }

}
