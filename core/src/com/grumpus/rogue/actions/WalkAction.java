package com.grumpus.rogue.actions;

import com.badlogic.gdx.Gdx;
import com.grumpus.rogue.Actor;
import com.grumpus.rogue.RogueGame;

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
        actor.x += dirX * RogueGame.TILE_SIZE;
        actor.y += dirY * RogueGame.TILE_SIZE;
        Gdx.app.debug(this.getClass().getSimpleName(), "Just walked.");
    }

}
