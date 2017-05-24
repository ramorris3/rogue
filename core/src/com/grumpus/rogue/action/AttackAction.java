package com.grumpus.rogue.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.effect.AttackMessageEffect;
import com.grumpus.rogue.stage.Stage;

public class AttackAction extends Action {

    private final String TAG = this.getClass().getSimpleName();

    private Actor attacker;
    private Actor defender;
    private Stage stage;

    private Color color;
    private Color critColor;

    public AttackAction(Actor attacker, Actor defender, Stage stage,
                        Color color, Color critColor) {
        super(attacker);
        this.attacker = attacker;
        this.defender = defender;
        this.stage = stage;
        this.color = color;
        this.critColor = critColor;
    }

    /** Constructor for enemies, all red */
    public AttackAction(Actor attacker, Actor defender, Stage stage) {
        this(attacker, defender, stage, Color.RED, Color.PINK);
    }

    @Override
    public void execute() {
        // check if defender dodged the attack (d20 roll against d6 roll)
        if (roll(20, attacker.agility) < roll(6, defender.agility)) {
            Gdx.app.debug(TAG, attacker + " missed " + defender + "!");
            RogueGame.messageLog.add(attacker + " missed " + defender + "!", color);
            stage.addEffect(new AttackMessageEffect(defender, "Miss!", color));
            return;
        }

        // roll for damage done (d6 roll)
        int atk = roll(6, attacker.attack);

        // roll for 1 / 20 chance of critical hit
        boolean crit = false;
        if (MathUtils.random(19) == 19) {
            // critical hit, add 2*attack rating
            crit = true;
            atk += attacker.attack * 2;
            Gdx.app.debug(TAG, attacker + " got a critical hit!");
            RogueGame.messageLog.add(attacker + " got a critical hit!", critColor);
        }

        // calculate damage, attack roll - defender's defense
        int damage = Math.max(1, atk - defender.defense); // can't do less than 1 dmg

        // apply damage to defender's hp
        defender.hp -= damage;

        // build attack message string
        String message;
        if (crit) {
            message = "Crit!\n-" + damage;
        } else {
            message = "-" + damage;
        }

        Color c = color;
        if (crit) c = critColor;

        stage.addEffect(new AttackMessageEffect(defender, message, c));
        Gdx.app.debug(TAG, attacker + " dealt " + damage + " dmg to " + defender + ".");
        RogueGame.messageLog.add(attacker + " dealt " + damage + " dmg to " + defender + ".", c);

        if (defender.hp <= 0) {
            defender.hp = 0;
            defender.die(stage);
            Gdx.app.debug(TAG, defender + " died.");
            RogueGame.messageLog.add(defender + " died.", c);
        }
    }

    private int roll(int base, int modifier) {
        return MathUtils.random(1, base) + modifier;
    }
}
