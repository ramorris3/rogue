package com.grumpus.rogue.ai;

import com.grumpus.rogue.action.Action;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.actor.Monster;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.stage.Stage;

public abstract class AI {

    protected Monster monster;
    protected Actor player;

    public AI(Monster monster, Player player) {
        this.monster = monster;
        this.player = player;
    }

    public abstract Action getNextAction(Stage stage);
}
