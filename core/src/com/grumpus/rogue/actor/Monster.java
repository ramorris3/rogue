package com.grumpus.rogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.ai.AI;
import com.grumpus.rogue.ai.WanderAI;
import com.grumpus.rogue.data.MonsterData;
import com.grumpus.rogue.stage.Stage;

public class Monster extends Actor {

    private AI ai;

    public Monster(Player player, String name, TextureRegion textureRegion, int x, int y, MonsterData data) {
        super("the " + name, textureRegion, x, y, data.hp, data.attack, data.defense, data.agility);
        // TODO: load AI from data instead of hardcoding it
        this.ai = new WanderAI(this, player);
    }

    @Override
    public void die(Stage stage) {
        // TODO: add death animation here or something.
        stage.removeMonster(this);
    }

    public void processAI(Stage stage) {
        setNextAction(ai.getNextAction(stage));
    }
}
