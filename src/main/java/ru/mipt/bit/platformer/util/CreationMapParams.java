package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CreationMapParams {
    private final String mapFilePath;
    private final TiledMapTileLayer groundLayer;

    private final Texture playerTexture;
    private final Texture obstacleTexture;


    public CreationMapParams(String mapFilePath,TiledMapTileLayer groundLayer,Texture playerTexture, Texture obstacleTexture) {
        this.mapFilePath = mapFilePath;
        this.groundLayer = groundLayer;
        this.playerTexture = playerTexture;
        this.obstacleTexture = obstacleTexture;
    }

    public String getMapFilePath() {
        return mapFilePath;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Texture getObstacleTexture() {
        return obstacleTexture;
    }
}
