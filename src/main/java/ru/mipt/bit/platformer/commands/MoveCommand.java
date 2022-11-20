package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.entity.TileObject;

import static com.badlogic.gdx.math.MathUtils.isEqual;

/*
 * Adapter
 */
public class MoveCommand implements Command {
    Tank tank;
    MoveVector moveVector;
    TileObject objectType;
    Level level;

    public MoveCommand(Tank tank, MoveVector moveVector, TileObject objectType, Level level) {
        this.tank = tank;
        this.moveVector = moveVector;
        this.objectType = objectType;
        this.level = level;
    }

    @Override
    public void execute() {
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            GridPoint2 playerDestCoords = tank.getCurrentCoordinates().cpy().add(moveVector.vector);
            // check potential player destination for collision with obstacles
            if (!level.checkCollision(playerDestCoords)) {
                tank.setDestinationCoordinates(playerDestCoords);
                tank.setPlayerMovementProgress(0f);
                level.moveObjects(tank,objectType);
            }
            tank.setRotation(moveVector.rotation);
        }
    }
}
