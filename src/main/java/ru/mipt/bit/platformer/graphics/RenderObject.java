package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics;

public class RenderObject {
    private Graphics graphics;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destCoordinates;
    private float movementProgress;
    private float rotation;

    public RenderObject(Graphics graphics, GridPoint2 currentCoordinates, GridPoint2 destCoordinates, float movementProgress, float rotation) {
        this.graphics = graphics;
        this.currentCoordinates = currentCoordinates;
        this.destCoordinates = destCoordinates;
        this.movementProgress = movementProgress;
        this.rotation = rotation;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public GridPoint2 getCurrentCoordinates() {
        return currentCoordinates;
    }

    public GridPoint2 getDestCoordinates() {
        return destCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }
}
