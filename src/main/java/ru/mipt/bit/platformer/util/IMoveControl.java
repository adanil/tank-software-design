package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public interface IMoveControl {
    void moveTank(Tank player, Level level);

    default void move(Tank tank, MoveVector moveVector, Level level, TileObject objectType){
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            GridPoint2 playerDestCoords = tank.getDestinationCoordinates().cpy().add(moveVector.vector);
            // check potential player destination for collision with obstacles
            if (!checkCollision(playerDestCoords,level)) {
                tank.setDestinationCoordinates(playerDestCoords);
                tank.setPlayerMovementProgress(0f);
                level.moveObjects(tank,objectType);
            }
            tank.setRotation(moveVector.rotation);
        }
    }

    default boolean checkCollision(GridPoint2 playerDestCoords, Level level){
        if (playerDestCoords.x < 0 || playerDestCoords.x >= level.getWidth() || playerDestCoords.y < 0 || playerDestCoords.y >= level.getHeight())
            return true;
        if (level.getObjectByCoords(playerDestCoords.x,playerDestCoords.y) != TileObject.FREE){
            return true;
        }
        return false;
    }
}
