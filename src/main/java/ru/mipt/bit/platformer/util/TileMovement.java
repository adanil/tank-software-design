package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement implements IMoveRectangle {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    @Override
    public Rectangle moveRectangleBetweenTileCenters(Graphics graphics, GridPoint2 fromTileCoordinates, GridPoint2 toTileCoordinates, float progress) {
        moveRectangleAtTileCenter(tileLayer, graphics, fromTileCoordinates);
        Rectangle rectangle = graphics.getRectangle();
        float fromTileBottomLeftX = rectangle.x;
        float fromTileBottomLeftY = rectangle.y;

        moveRectangleAtTileCenter(tileLayer, graphics, toTileCoordinates);
        float toTileBottomLeftX = rectangle.x;
        float toTileBottomLeftY = rectangle.y;

        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, progress);
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, progress);

        return rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
