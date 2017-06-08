package com.grumpus.rogue.dungeon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.grumpus.rogue.RogueGame;

import java.util.HashMap;

/**
 * Special tiles that handle room transitions.
 */
public class Doorway extends TextureRegion {

    /** Four different positions that a doorway can exist in */
    public enum Positions { LEFT, UP, RIGHT, DOWN }

    /**
     * Entry points are positions in the room where a doorway to a
     * new room can be carved.  The coordinates are the actual position
     * of the door, i.e. if the player walks onto this position (and
     * it's a door), they will go to the next room.
     */
    public static final HashMap<Positions, Vector2> entryPoints;
    static {
        entryPoints = new HashMap<>();
        entryPoints.put(Positions.LEFT, new Vector2(
                0,
                RogueGame.ROOM_TILE_HEIGHT / 2
        ));
        entryPoints.put(Positions.UP, new Vector2(
                RogueGame.ROOM_TILE_WIDTH / 2,
                RogueGame.ROOM_TILE_HEIGHT - 1
        ));
        entryPoints.put(Positions.RIGHT, new Vector2(
                RogueGame.ROOM_TILE_WIDTH - 1,
                RogueGame.ROOM_TILE_HEIGHT / 2
        ));
        entryPoints.put(Positions.DOWN, new Vector2(
                RogueGame.ROOM_TILE_WIDTH / 2,
                0
        ));
    }

    /**
     * Exit points are positions in the next room where the player
     * will appear if they walk through a doorway.  They are not the
     * actual position of the doorway itself, they are space just "in
     * front" of the doorway by one tile.
     */
    public static final HashMap<Positions, Vector2> exitPoints;
    static {
        exitPoints = new HashMap<>();
        // when you go in the left door, you come out the right door
        // when you go in the up door, you come out the down door, etc.
        exitPoints.put(Positions.LEFT, new Vector2(
                RogueGame.ROOM_TILE_WIDTH - 1,
                RogueGame.ROOM_TILE_HEIGHT / 2
        ));
        exitPoints.put(Positions.UP, new Vector2(
                RogueGame.ROOM_TILE_WIDTH / 2,
                0
        ));
        exitPoints.put(Positions.RIGHT, new Vector2(
                0,
                RogueGame.ROOM_TILE_HEIGHT / 2
        ));
        exitPoints.put(Positions.DOWN, new Vector2(
                RogueGame.ROOM_TILE_WIDTH / 2,
                RogueGame.ROOM_TILE_HEIGHT - 1
        ));
    }

    public Positions position;
    public Room toRoom;

    public Doorway(TextureRegion textureRegion, Positions position,
                   Room toRoom) {
        setRegion(textureRegion);
        this.position = position;
        this.toRoom = toRoom;
    }
}
