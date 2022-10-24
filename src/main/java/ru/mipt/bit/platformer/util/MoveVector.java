package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public enum MoveVector {
    UP(Rotation.UP, DefaultsVectors.UP),
    DOWN(Rotation.DOWN, DefaultsVectors.DOWN),
    LEFT(Rotation.LEFT, DefaultsVectors.LEFT),
    RIGHT(Rotation.RIGHT, DefaultsVectors.RIGHT);

    public final Rotation rotation;
    public final GridPoint2 vector;

    MoveVector(Rotation rotation, GridPoint2 vector) {
        this.rotation = rotation;
        this.vector = vector;
    }

    private static class DefaultsVectors
    {
        private static final GridPoint2 UP = new GridPoint2(0,1);
        private static final GridPoint2 DOWN = new GridPoint2(0,-1);
        private static final GridPoint2 LEFT = new GridPoint2(-1,0);
        private static final GridPoint2 RIGHT = new GridPoint2(1,0);
    }
}
