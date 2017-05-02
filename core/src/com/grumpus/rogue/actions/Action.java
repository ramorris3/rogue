package com.grumpus.rogue.actions;

import com.grumpus.rogue.Actor;

public abstract class Action {

    protected Actor actor;

    public Action(Actor actor) {
        this.actor = actor;
    }

    public abstract void execute();

}
