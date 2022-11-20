package ru.mipt.bit.platformer.tankstate;

import ru.mipt.bit.platformer.commands.CommandType;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
/*
 * Adapter
 */
public class HardState extends TankState{
    public HardState(Tank tank) {
        super(tank);
    }

    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED, Level level) {
        MOVEMENT_SPEED *= 3;
        tank.setPlayerMovementProgress(continueProgress(tank.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            if (!tank.getDestinationCoordinates().equals(tank.getCurrentCoordinates()))
                level.clearTile(tank.getCurrentCoordinates());
            tank.setCurrentCoordinates(tank.getDestinationCoordinates());
        }
    }

    @Override
    public boolean validateCommand(CommandType cmdType) {
        if (cmdType == CommandType.SHOOT)
            return false;
        return true;
    }
}
