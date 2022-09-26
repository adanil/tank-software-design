package ru.mipt.bit.platformer.util;

public enum Rotation {
    UP(90.0f),
    DOWN(-90.0f),
    LEFT(-180.0f),
    RIGHT(0.0f);

    public final float value;

    Rotation(float value) {
        this.value = value;
    }
}
