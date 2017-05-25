package com.grumpus.rogue.dungeon;

/** Randomly-generated area made up of 20 rooms */
public class Dungeon {

    private String habitat;
    public Room currentRoom;
    private int[][]rooms;

    public Dungeon(String habitat) {
        this.habitat = habitat;
        this.rooms = new int[10][10];
    }

    private void generate() {

    }

}
