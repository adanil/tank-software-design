package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CreationMapParams {
    private final String mapFilePath;
    private final int width;
    private final int height;




    public CreationMapParams(String mapFilePath,int width,int height) {
        this.mapFilePath = mapFilePath;
        this.width = width;
        this.height = height;

    }

    public String getMapFilePath() {
        return mapFilePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
