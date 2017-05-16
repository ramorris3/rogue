package com.grumpus.rogue.ai;

import com.badlogic.gdx.math.MathUtils;
import com.grumpus.rogue.action.Action;
import com.grumpus.rogue.actor.Monster;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.action.WalkAction;
import com.grumpus.rogue.stage.Stage;

public class WanderAI extends AI {

    public WanderAI(Monster monster, Player player) {
        super(monster, player);
    }

    @Override
    public Action getNextAction(Stage stage) {

        int dx = MathUtils.random(-1, 1);
        int dy = MathUtils.random(-1, 1);

        return new WalkAction(monster, dx, dy);
    }
}
