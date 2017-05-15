package com.grumpus.rogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.grumpus.rogue.RogueGame;

import java.util.LinkedHashMap;

/**
 * Represents a level with its interactive geometry.
 * Includes walls, doors, etc., but not actors.
 */
public class Stage {

    private final String TAG = this.getClass().getSimpleName();

    private LinkedHashMap<String, TextureRegion[][]> layers;
    private int width;
    private int height;

    public Stage(TiledMap map) {
        layers = new LinkedHashMap<String, TextureRegion[][]>();
        loadMap(map);
    }

    /**
     * Generates the stage's layers from a {@link TiledMap}.
     * @param map The {@link TiledMap} to load.
     */
    private void loadMap(TiledMap map) {
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
     * @return
     */
    public boolean isLayerAt(String layerName, int tx, int ty) {
        return getTextureRegion(layerName, tx, ty) != null;
    }

    /**
     * Iterate through all layers and draw them to {@link RogueGame#batch} in order.
     */
    public void draw() {
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
    }
}

