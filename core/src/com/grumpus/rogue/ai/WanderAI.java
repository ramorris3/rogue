package com.grumpus.rogue.ai;

import com.badlogic.gdx.math.MathUtils;
import com.grumpus.rogue.action.Action;
import com.grumpus.rogue.action.AttackAction;
import com.grumpus.rogue.actor.Monster;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.action.WalkAction;
import com.grumpus.rogue.dungeon.Room;

public class WanderAI extends AI {

    public WanderAI(Monster monster, Player player) {
        super(monster, player);
    }

    @Override
    public Action getNextAction(Room room) {

        // if player is next to this monster, attack player
        if (monster.isNextTo(player)) {
            return new AttackAction(monster, player, room);
        }

        // randomly choose a direction to go in
        int dx, dy, tx, ty;
        int attempts = 0;
        do {
            dx = MathUtils.random(-1, 1);
            dy = MathUtils.random(-1, 1);
            tx = monster.getTileX() + dx;
            ty = monster.getTileY() + dy;

            if (!room.isBlocked(tx, ty)) {
                return new WalkAction(monster, dx, dy);
            }

            attempts++;
        } while (!room.isBlocked(tx, ty) && attempts < 20);

        // after a certain number of unsuccessful attempts, just "walk" in place.
        return new WalkAction(monster, 0, 0);
    }
}
