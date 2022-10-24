package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandGenerator;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.entity.TileObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class BotControl implements IMoveControl, CommandGenerator {
    Level level;

    Collection<Tank> bots;

    public BotControl(Collection<Tank> bots,Level level) {
        this.bots = bots;
        this.level = level;
    }

    public Collection<Command> generateCommands(Tank bot, Level level) {
        ArrayList<Command> commands = new ArrayList<>();
        Random ran = new Random();
        int mv = ran.nextInt(4);
        commands.add(new MoveCommand(bot,MoveVector.values()[mv], TileObject.BOT, level));
        return commands;
    }

    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED){
        for (Tank tank : bots) {
            tank.setPlayerMovementProgress(continueProgress(tank.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
            if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
                // record that the player has reached his/her destination
                if (!tank.getDestinationCoordinates().equals(tank.getCurrentCoordinates()))
                    level.clearTile(tank.getCurrentCoordinates());
                tank.setCurrentCoordinates(tank.getDestinationCoordinates());
            }
        }
    }

    @Override
    public void handleCommands(){
        for (Tank bot: bots) {
            Collection<Command> commands = generateCommands(bot, level);
            for (Command cmd : commands) {
                cmd.execute();
            }
        }
    }

}
