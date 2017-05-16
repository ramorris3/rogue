package com.grumpus.rogue.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.action.Action;

public class Actor {

    private TextureRegion textureRegion;
    private int x;
    private int y;

    public int hp;
    public int maxHp;

    public int attack;
    public int defense;
    public int agility;

    protected Action nextAction;

    public Actor(TextureRegion textureRegion, int x, int y,
                 int hp, int attack, int defense, int agility) {
        // set graphic and position
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;

        // load stats from constructor
        maxHp = hp;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
    }

    public void setTileX(int tx) {
        x = tx * RogueGame.TILE_SIZE;
    }

    public int getTileX() {
        return x / RogueGame.TILE_SIZE;
    }

    public int getTileY() {
        return y / RogueGame.TILE_SIZE;
    }

    public void setTileY(int ty) {
        y = ty * RogueGame.TILE_SIZE;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public boolean hasNextAction() { return nextAction != null; }

    public void draw() {
        RogueGame.batch.draw(textureRegion, x, y);
    }

}
