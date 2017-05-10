package com.grumpus.rogue.actor;

public abstract class Action {

    protected Actor actor;

    public Action(Actor actor) {
        this.actor = actor;
    }

    public abstract void execute();

}
