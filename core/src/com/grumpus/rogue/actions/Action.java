package com.grumpus.rogue.actions;

public abstract class Action {

    protected Actor actor;

    public Action(Actor actor) {
        this.actor = actor;
    }

    public abstract void execute();

}
