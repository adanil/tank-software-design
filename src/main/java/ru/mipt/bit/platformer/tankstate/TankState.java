package ru.mipt.bit.platformer.tankstate;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandType;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;

import java.util.Collection;

public abstract class TankState {
    protected Tank tank;
    public abstract void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED, Level level);
    public abstract boolean validateCommand(CommandType cmdType);

    public TankState(Tank tank) {
        this.tank = tank;
    }
}
