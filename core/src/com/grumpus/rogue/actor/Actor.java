package com.grumpus.rogue.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.action.Action;
import com.grumpus.rogue.dungeon.Room;

public class Actor {

    private String name;

    private TextureRegion textureRegion;
    private int x;
    private int y;

    public int hp;
    public int maxHp;

    public int attack;
    public int defense;
    public int agility;

    protected Action nextAction;

    public Actor(String name, TextureRegion textureRegion, int x, int y,
                 int hp, int attack, int defense, int agility) {
        this.name = name;

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

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getCenterX() {
        return x + (RogueGame.TILE_SIZE / 2);
    }

    public int getCenterY() {
        return y + (RogueGame.TILE_SIZE / 2);
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

    public boolean isNextTo(Actor other) {
        int dx = Math.abs(getTileX() - other.getTileX());
        int dy = Math.abs(getTileY() - other.getTileY());
        return dx + dy == 1;
    }

    /** Both player and monster classes must override this method */
    public void die(Room room) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Die function not overridden.");
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public boolean hasNextAction() { return nextAction != null; }

    public void draw() {
        RogueGame.batch.draw(textureRegion, x, y + RogueGame.ROOM_Y);
    }

    @Override
    public String toString() {
        return name;
    }
}
