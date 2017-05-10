package com.grumpus.rogue.actor;

import com.grumpus.rogue.stage.Stage;
import com.grumpus.rogue.util.TileGraphics;

public class OpenDoorAction extends Action {

    private Stage stage;
    private int tx;
    private int ty;

    public OpenDoorAction(Stage stage, int doorTileX, int doorTileY) {
        super(null);
        this.stage = stage;
        tx = doorTileX;
        ty = doorTileY;
    }

    @Override
    public void execute() {
        // delete door cell from door layer at position tile position (tx, ty)
        stage.setTextureRegion("door", tx, ty, null);

        // set bg tile at (tx, ty) to open door texture region
        stage.setTextureRegion("background", tx, ty, TileGraphics.OPEN_DOOR);

    }

}
