package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public interface IMoveControl {
    void moveTank(Tank player, Level level);

    void calculateMovementProgress(Tank tank,float deltaTime, float MOVEMENT_SPEED);

    void move(Tank tank, MoveVector moveVector, TileObject objectType);

    boolean checkCollision(GridPoint2 playerDestCoords);
}
