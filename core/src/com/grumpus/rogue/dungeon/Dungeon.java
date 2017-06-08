package com.grumpus.rogue.dungeon;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.grumpus.rogue.actor.Player;

/** Randomly-generated area made up of 20 rooms */
public class Dungeon {

    private String habitat;
    private Player player;
    private Room[][] grid;
    private int roomCount;
    private int maxRooms;
    public Room room;

    public Dungeon(String habitat, Player player) {
        this.habitat = habitat;
        this.player = player;
        generate();
    }

    private void generate() {
        /*
         * 1. Initialize 2D 10x10 grid of null pointers.  Store starting position
         * at the middle of the grid.
         *
         * 2. Count rooms.  While room count is less than maxRooms,
         * branch out from current position up to 4 times, creating
         * adjacent rooms.
         *
         * 3. Once room count >= max rooms, loop through grid.
         * For each room with an adjacent room, carve a doorway at that position.
         */
        // initialize grid
        // TODO: Make dynamic? Hardcoded for now...
        int gridWidth = 10;
        int gridHeight = 10;
        roomCount = 0;
        maxRooms = 20;
        grid = new Room[gridWidth][gridHeight];

        // fill with unconnected rooms
        fillGridRec(gridWidth / 2, gridHeight / 2);

        // print grid for debugging
        System.out.println(this);

        // make doorways between adjacent rooms
        connectRooms();
    }

    /**
     * Recursive function for generating the dungeon.  Subroutine of
     * {@link Dungeon#generate()}.
     */
    private void fillGridRec(int currX, int currY) {
        if (roomCount >= maxRooms) return;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // don't do diagonals or current room
                if (Math.abs(i) == Math.abs(j)) {
                    continue;
                }

                int x = currX + i;
                int y = currY + j;

                // check if selected space is in grid bounds
                if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                    // room must not exist here already.  After first 5 rooms are placed,
                    // there's only a partial chance rooms will be placed.
                    if (grid[x][y] == null && (MathUtils.random(100) < 60 || roomCount < 5)) {

                        // create new room
                        // TODO: get random map filename to pass to room
                        String mapName = "maps/test.tmx";
                        Room newRoom = new Room(mapName, player);
                        if (roomCount == 0) room = newRoom;
                        roomCount++;

                        // set room in grid and recurse
                        grid[x][y] = newRoom;
                        fillGridRec(x, y);
                    }
                }
            }
        }
    }

    /** Creates doorways between rooms in grid.  Subroutine of generate function. */
    private void connectRooms() {
        // TODO: how to selectively carve doorways while making all rooms reachable?
        // connect adjacent rooms
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                // check that there's a room here
                if (grid[x][y] == null) continue;

                for (int yy = -1; yy <= 1; yy++) {
                    for (int xx = -1; xx <= 1; xx++) {
                        // don't do diagonals or current room
                        if (Math.abs(yy) == Math.abs(xx)) continue;

                        // get adjacent room index
                        int rx = x + xx;
                        int ry = y + yy;

                        // check if adjacent room is in grid bounds
                        if (rx >= 0 && rx < grid.length && ry >= 0 && ry < grid[0].length) {
                            // if room exists, make a connection
                            // TODO: selectively make connections, yet make sure all rooms are reachable
                            Room fromRoom = grid[x][y];
                            Room toRoom = grid[rx][ry];
                            if (toRoom == null) continue;
                            if (xx == -1) {
                                fromRoom.carveDoorway(Doorway.Positions.LEFT, toRoom);
                            } else if (xx == 1) {
                                fromRoom.carveDoorway(Doorway.Positions.RIGHT, toRoom);
                            } else if (yy == -1) {
                                fromRoom.carveDoorway(Doorway.Positions.DOWN, toRoom);
                            } else if (yy == 1) {
                                fromRoom.carveDoorway(Doorway.Positions.UP, toRoom);
                            }
                        }
                    }
                }
            }
        }
    }

    /** transition to a new room */
    public void changeRooms(Doorway dway) {
        // TODO: set a flag that tells the stage to skip the update loop while animating
        // set new current room for dungeon
        room = dway.toRoom;

        // update position for player
        Vector2 exitPos = Doorway.exitPoints.get(dway.position);
        player.setTileX((int)exitPos.x);
        player.setTileY((int)exitPos.y);

        // print dungeon for debugging
        System.out.println(this);
    }

    /** x = current location, 1 = room, 0 = wall */
    @Override
    public String toString() {
        String gridStr = "\n";
        for (int y = grid[0].length - 1; y >= 0; y--) {
            for (int x = 0; x <  grid.length; x++) {
                Room rm = grid[x][y];
                if (rm == room) {
                    gridStr += "x";
                } else {
                    gridStr += (rm == null) ? "0" : "1";
                }

                if (x + 1 < grid.length) {
                    gridStr += ", ";
                }
            }
            gridStr += "\n";
        }

        return gridStr;
    }

}
