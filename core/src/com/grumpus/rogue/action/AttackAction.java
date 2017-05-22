package com.grumpus.rogue.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.stage.Stage;

public class AttackAction extends Action {

    private final String TAG = this.getClass().getSimpleName();

    private Actor attacker;
    private Actor defender;
    private Stage stage;

    public AttackAction(Actor attacker, Actor defender, Stage stage) {
        super(attacker);
        this.attacker = attacker;
        this.defender = defender;
        this.stage = stage;
    }

    @Override
    public void execute() {
        // check if defender dodged the attack (d20 roll against d6 roll)
        if (roll(20, attacker.agility) < roll(6, defender.agility)) {
            Gdx.app.debug(TAG, attacker + " missed " + defender + "!");
            return;
        }

        // roll for damage done (d6 roll)
        int atk = roll(6, attacker.attack);

        // roll for 1 / 20 chance of critical hit
        if (MathUtils.random(19) == 19) {
            // critical hit, triple damage
            atk *= 3;
            Gdx.app.debug(TAG, attacker + " got a critical hit!");
        }

        // calculate damage, attack roll - defender's defense
        int damage = Math.max(1, atk - defender.defense); // can't do less than 1 dmg

        defender.hp -= damage;
        Gdx.app.debug(TAG, attacker + " dealt " + damage + " damage to " + defender + ".");

        if (defender.hp <= 0) {
            defender.hp = 0;
            defender.die(stage);
            Gdx.app.debug(TAG, defender + " died.");
        }
    }

    private int roll(int base, int modifier) {
        return MathUtils.random(1, base) + modifier;
    }
}
