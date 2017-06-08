package com.grumpus.rogue.action;

import com.grumpus.rogue.dungeon.Room;
import com.grumpus.rogue.util.TileGraphics;

public class OpenDoorAction extends Action {

    private Room room;
    private int tx;
    private int ty;

    public OpenDoorAction(Room room, int doorTileX, int doorTileY) {
        super(null);
        this.room = room;
        tx = doorTileX;
        ty = doorTileY;
    }

    @Override
    public void execute() {
        // delete door from door layer at tile position (tx, ty)
        room.setTextureRegion("door", tx, ty, null);

        // set bg tile at (tx, ty) to open door texture region
        room.setTextureRegion("background", tx, ty, TileGraphics.OPEN_DOOR);

    }

}
