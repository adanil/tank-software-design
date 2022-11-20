package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.commands.*;
import ru.mipt.bit.platformer.entity.Bullet;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
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
        int mv = ran.nextInt(5);
        if (mv < 4) {
            if (bot.validateCommand(CommandType.MOVE))
                commands.add(new MoveCommand(bot, MoveVector.values()[mv], TileObject.BOT, level));
        }
        else {
            if (bot.validateCommand(CommandType.SHOOT))
                commands.add(new Shoot(new Bullet(bot), level,bot));
        }
        return commands;
    }

    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED){
        for (Tank tank : bots) {
            tank.calculateMovement(deltaTime,MOVEMENT_SPEED,level);
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
