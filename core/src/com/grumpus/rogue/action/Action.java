package com.grumpus.rogue.action;

import com.grumpus.rogue.actor.Actor;

public abstract class Action {

    protected Actor actor;

    public Action(Actor actor) {
        this.actor = actor;
    }

    public abstract void execute();
}
