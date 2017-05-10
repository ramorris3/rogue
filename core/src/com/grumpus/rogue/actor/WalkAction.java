package com.grumpus.rogue.actor;

public class WalkAction extends Action {

    private int dirX;
    private int dirY;

    public WalkAction(Actor actor, int dirX, int dirY) {
        super(actor);
        this.dirX = dirX;
        this.dirY = dirY;
    }

    @Override
    public void execute() {
        actor.setTileX(actor.getTileX() + dirX);
        actor.setTileY(actor.getTileY() + dirY);
    }

}
