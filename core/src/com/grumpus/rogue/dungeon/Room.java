package com.grumpus.rogue.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.actor.Monster;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.data.MonsterData;
import com.grumpus.rogue.data.DataLoader;
import com.grumpus.rogue.effect.Effect;
import com.grumpus.rogue.util.TileGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Represents a level with its interactive geometry.
 * Includes walls, doors, etc., but not monsters.
 */
public class Room {

    private final String TAG = this.getClass().getSimpleName();

    private Player player;

    private int width;
    private int height;
    private LinkedHashMap<String, TextureRegion[][]> layers;
    private ArrayList<Monster> monsters;

    private ArrayList<Effect> effects;

    public Room(String filename, Player player) {
        layers = new LinkedHashMap<>();
        monsters = new ArrayList<>();
        effects = new ArrayList<>();
        this.player = player;

        // load map from filename
        TiledMap map = new TmxMapLoader().load(filename);
        loadTiles(map);
        loadMonsters(map, player);
    }

    // TODO: phase this constructor out?
    public Room(TiledMap map, Player player) {
        layers = new LinkedHashMap<>();
        monsters = new ArrayList<>();
        effects = new ArrayList<>();
        this.player = player;
        loadTiles(map);
        loadMonsters(map, player);
    }

    /**
     * Generates the dungeon's level geometry from a {@link TiledMap}.
     * The level is loaded into {@link Room#layers}.
     * @param map The map to load from.
     */
    private void loadTiles(TiledMap map) {
        // get width and height (in tiles) from the map
        MapProperties props = map.getProperties();
        width = props.get("width", Integer.class);
        height = props.get("height", Integer.class);

        // add empty "doorway" layer, initialized by dungeon generation code, and
        // used to handle transitions between rooms
        layers.put("doorway", new TextureRegion[width][height]);

        // load map tile layers into dungeon
        for (MapLayer mapLayer : map.getLayers()) {
            try {
                TiledMapTileLayer tmxLayer = (TiledMapTileLayer)mapLayer;
                TextureRegion[][] layer = new TextureRegion[width][height];

                // go through each cell individually and set the textureRegion from TiledMap
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        TiledMapTileLayer.Cell cell = tmxLayer.getCell(x, y);
                        if (cell != null) {
                            layer[x][y] = cell.getTile().getTextureRegion();
                        }
                    }
                }

                // create the layer
                layers.put(tmxLayer.getName(), layer);
            } catch (ClassCastException e) {
                // ignore object layers, we only care about tile layers for dungeon geometry
                Gdx.app.debug(TAG, "Ignoring \"" + mapLayer.getName() + "\" layer because it's not a tile layer.");
            }
        }
    }

    /**
     * Generates the player and monsters from a {@link TiledMap}.
     * The monsters are loaded into {@link Room#monsters}.
     * @param map The map to load from.
     */
    private void loadMonsters(TiledMap map, Player player) {
        // load all monsters from map
        MapLayer monsterLayer = map.getLayers().get("monsters");
        if (monsterLayer != null) {
            // load each monster
            for (TiledMapTileMapObject tileObj : monsterLayer.getObjects().getByType(TiledMapTileMapObject.class)) {
                // load action data from key
                String name = tileObj.getName();
                MonsterData data = DataLoader.loadMonsterData(name);

                // create action from data, if not null
                if (data != null) {
                    int x = (int)tileObj.getX();
                    int y = (int)tileObj.getY();
                    TextureRegion tr = tileObj.getTextureRegion();
                    monsters.add(new Monster(player, name, tr, x, y, data));
                } else {
                    Gdx.app.log(this.getClass().getSimpleName(), "WARNING! There is no monster named " +
                            name + ", no monster was loaded.");
                }
            }
        }
    }

    public TextureRegion getTextureRegion(String layerName, int tx, int ty) {
        try {
            return layers.get(layerName)[tx][ty];
        } catch (NullPointerException e) {
            Gdx.app.log(TAG, "WARNING! There is no layer named \"" + layerName + "\"");
            return null;
        }
    }

    public void setTextureRegion(String layerName, int tx, int ty, TextureRegion textureRegion) {
        try {
            layers.get(layerName)[tx][ty] = textureRegion;
        } catch (NullPointerException e) {
            Gdx.app.log(TAG, "WARNING! There is no layer named: \"" + layerName + "\"");
        }
    }

    /**
     * Check if the specified layer has a non-empty tile at the given tile coordinates.
     * @param layerName The name of the layer, i.e. "solid", "door", etc.
     * @param tx The tile-x position to check.
     * @param ty The tile-y position to check.
     * @return True if the layer has a tile at the given position, false if otherwise.
     */
    public boolean isLayerAt(String layerName, int tx, int ty) {
        return getTextureRegion(layerName, tx, ty) != null;
    }

    /** Check if the player is at a given tile location */
    public boolean isPlayerAt(int tx, int ty) {
        return player.getTileX() == tx && player.getTileY() == ty;
    }

    public void addEffect(Effect e) {
        effects.add(e);
    }

    public void addMonster(Monster m) {
        monsters.add(m);
    }

    public void removeMonster(Monster m) {
        monsters.remove(m);
    }

    /** Get the monster at a specific tile location.  Null if no monster there. */
    public Monster getMonsterAt(int tx, int ty) {
        for (Monster monster : monsters) {
            if (monster.getTileX() == tx && monster.getTileY() == ty) {
                return monster;
            }
        }
        return null;
    }

    /** Removes a wall and adds a doorway at one of four positions. */
    public void carveDoorway(Doorway.Positions position, Room toRoom) {
        try {
            // get the tile coords of the new doorway
            Vector2 tilePos = Doorway.entryPoints.get(position);
            int tx = (int)tilePos.x;
            int ty = (int)tilePos.y;

            // get the solid, door, and doorway layers
            TextureRegion[][] solidLayer = layers.get("solid");
            TextureRegion[][] doorwayLayer = layers.get("doorway");

            // remove solid tile, create door and doorway tiles
            solidLayer[tx][ty] = null;
            doorwayLayer[tx][ty] = new Doorway(TileGraphics.DOORWAY, position, toRoom);
        } catch (NullPointerException e) {
            Gdx.app.log(TAG, "WARNING! This room is missing a doorway or solid layer!");
        } catch (ArrayIndexOutOfBoundsException e) {
            Gdx.app.log(TAG, "WARNING! Can't carve a door out of bounds.");
        }
    }

    /**
     * Check if a tile is blocked by level geometry (doors, solids, etc.) or by
     * actors (player or monsters).  NOTE: Opened doorways are treated as solid
     * so that enemies won't walk through them.  Room transitions are a special
     * case only relevant to player.
     * @return True if blocked, false if clear.
     */
    public boolean isBlockedForPlayer(int tx, int ty) {
        return isLayerAt("door", tx, ty)
                || isLayerAt("solid", tx, ty)
                || isPlayerAt(tx, ty)
                || getMonsterAt(tx, ty) != null;
    }

    /**
     * Check if a tile is blocked by level geometry (doors, solids, etc.) or by
     * actors (player or monsters).  NOTE: Opened doorways are treated as solid
     * so that enemies won't walk through them.  Room transitions are a special
     * case only relevant to player.
     * @return True if blocked or if there's a doorway there, false if clear.
     */
    public boolean isBlockedForMonster(int tx, int ty) {
        return isBlockedForPlayer(tx, ty) || isLayerAt("doorway", tx, ty);
    }

    /**
     * Check if a tile position is outside the room's tile grid.  I'm pretty
     * sure this is exclusively being used by room transition code, which is
     * pretty hacky... but it should be fine for now.
     */
    public boolean isOutOfBounds(int tx, int ty) {
        return tx < 0 || tx >= width || ty < 0 || ty >= height;
    }

    public void updateMonsters() {
        // perform actions for all monsters
        for (Monster monster : monsters) {

            // update monster action based on AI input
            monster.processAI(this);

            // execute action if not null
            if (monster.hasNextAction()) {
                monster.getNextAction().execute();
            }
        }
    }

    /** Draw all level geometry */
    public void drawLevel() {
        for (TextureRegion[][] layer : layers.values()) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    TextureRegion tr = layer[x][y];
                    if (tr != null) {
                        RogueGame.batch.draw(layer[x][y],
                                x * RogueGame.TILE_SIZE,
                                y * RogueGame.TILE_SIZE + RogueGame.ROOM_Y);
                    }
                }
            }
        }
    }

    /** Draw all monsters */
    public void drawMonsters() {
        for (Actor actor : monsters) {
            actor.draw();
        }
    }

    /** Draw all effects */
    public void drawEffects(float delta) {
        // draw all effects
        Iterator<Effect> iter = effects.iterator();
        while (iter.hasNext()) {
            Effect e = iter.next();
            if (e.isFinished()) {
                iter.remove();
            } else {
                e.draw(delta);
            }
        }
    }
}

