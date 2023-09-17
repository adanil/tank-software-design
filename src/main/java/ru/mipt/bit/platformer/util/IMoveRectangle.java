package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface IMoveRectangle {
    Rectangle moveRectangleBetweenTileCenters(Graphics graphics, GridPoint2 fromTileCoordinates, GridPoint2 toTileCoordinates, float progress);
}
