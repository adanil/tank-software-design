package ru.mipt.bit.platformer.control;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public interface IMoveControl {
    void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED);

    void handleCommands();
}
