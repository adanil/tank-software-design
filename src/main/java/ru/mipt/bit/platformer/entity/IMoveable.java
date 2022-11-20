package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

public interface IMoveable {
    GridPoint2 getCurrentCoordinates();
    GridPoint2 getDestinationCoordinates();
}
