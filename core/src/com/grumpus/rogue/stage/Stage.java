package com.grumpus.rogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.grumpus.rogue.RogueGame;
import com.grumpus.rogue.actor.Actor;
import com.grumpus.rogue.actor.Monster;
import com.grumpus.rogue.actor.Player;
import com.grumpus.rogue.data.MonsterData;
import com.grumpus.rogue.data.DataLoader;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents a level with its interactive geometry.
 * Includes walls, doors, etc., but not monsters.
 */
public class Stage {

    private final String TAG = this.getClass().getSimpleName();

    private int width;
    private int height;
    private LinkedHashMap<String, TextureRegion[][]> layers;
    private ArrayList<Monster> monsters;

    public Stage(TiledMap map, Player player) {
        layers = new LinkedHashMap<>();
        monsters = new ArrayList<>();
        loadTiles(map);
        loadMonsters(map, player);
    }

    /**
     * Generates the stage's level geometry from a {@link TiledMap}.
     * The level is loaded into {@link Stage#layers}.
     * @param map The map to load from.
     */
    private void loadTiles(TiledMap map) {
        // get width and height (in tiles) from the map
        MapProperties props = map.getProperties();
        width = props.get("width", Integer.class);
        height = props.get("height", Integer.class);

        // load map tile layers into stage
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
                // ignore object layers, we only care about tile layers for stage geometry
                Gdx.app.debug(TAG, "Ignoring \"" + mapLayer.getName() + "\" layer because it's not a tile layer.");
            }
        }
    }

    /**
     * Generates the player and monsters from a {@link TiledMap}.
     * The monsters are loaded into {@link Stage#monsters}.
     * @param map The map to load from.
     */
    private void loadMonsters(TiledMap map, Player player) {
        // load all monsters from map
        MapLayer monsterLayer = map.getLayers().get("monsters");
        if (monsterLayer != null) {
            // load each monster
            for (TiledMapTileMapObject tileObj : monsterLayer.getObjects().getByType(TiledMapTileMapObject.class)) {
                // load action data from key
                String key = tileObj.getName();
                MonsterData data = DataLoader.loadMonsterData(key);

                // create action from data, if not null
                if (data != null) {
                    int x = (int)tileObj.getX();
                    int y = (int)tileObj.getY();
                    TextureRegion tr = tileObj.getTextureRegion();
                    monsters.add(new Monster(player, tr, x, y, data));
                } else {
                    Gdx.app.log(this.getClass().getSimpleName(), "WARNING! There is no monster named " +
                            key + ", no monster was loaded.");
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

    /**
     * Check if a tile is blocked by level geometry (doors, solids, etc.)
     * @return True if blocked, false if clear.
     */
    public boolean isBlocked(int tx, int ty) {
        return isLayerAt("door", tx, ty) || isLayerAt("solid", tx, ty);
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

    /**
     * Iterate through all layers and monsters and draw them to {@link RogueGame#batch} in order.
     */
    public void draw() {
        // drawing layers
        for (TextureRegion[][] layer : layers.values()) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    TextureRegion tr = layer[x][y];
                    if (tr != null) {
                        RogueGame.batch.draw(layer[x][y],
                                x * RogueGame.TILE_SIZE,
                                y * RogueGame.TILE_SIZE);
                    }
                }
            }
        }

        // drawing all other monsters
        for (Actor actor : monsters) {
            actor.draw();
        }
    }
}

