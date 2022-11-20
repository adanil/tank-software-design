package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.commands.*;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.graphics.GraphicsParams;
import ru.mipt.bit.platformer.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

/*
 * Port
*/
public class PlayerControl implements IMoveControl, CommandGenerator {
    ControlButtons controlButtons;
    Level level;

    Tank player;

    GraphicsParams graphicsParams;



    private int delay = 200;
    private HashMap<Integer,Long> lastButtonPressedTime;

    public PlayerControl(Tank player,ControlButtons controlButtons, Level level,GraphicsParams graphicsParams) {
        this.player = player;
        this.controlButtons = controlButtons;
        this.level = level;
        lastButtonPressedTime = new HashMap<>();
        this.graphicsParams = graphicsParams;

    }

    private boolean isButtonPressed(int button1, int button2) {
        if (Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2)) {
            long currTimestamp = new Date().getTime();
            if ((lastButtonPressedTime.containsKey(button1) || lastButtonPressedTime.containsKey(button2)) && currTimestamp - lastButtonPressedTime.get(button1) < delay) {
                return false;
            }
            lastButtonPressedTime.put(button1,currTimestamp);
            lastButtonPressedTime.put(button2,currTimestamp);
            return true;
        }
        return false;
    }

    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED){
        player.calculateMovement(deltaTime,MOVEMENT_SPEED,level);
    }

    @Override
    public void handleCommands(){
        Collection<Command> commands = generateCommands(player, level);
        for (Command cmd : commands){
            cmd.execute();
        }
    }

    @Override
    public Collection<Command> generateCommands(Tank player, Level level){
        ArrayList<Command> commands = new ArrayList<>();
        if (isButtonPressed(controlButtons.getUpButton1(), controlButtons.getUpButton2())) {
            if (player.validateCommand(CommandType.MOVE))
                commands.add(new MoveCommand(player,MoveVector.UP,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            if (player.validateCommand(CommandType.MOVE))
                commands.add(new MoveCommand(player,MoveVector.LEFT,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            if (player.validateCommand(CommandType.MOVE))
                commands.add(new MoveCommand(player,MoveVector.DOWN, TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            if (player.validateCommand(CommandType.MOVE))
                commands.add(new MoveCommand(player,MoveVector.RIGHT,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getShootButton(), controlButtons.getShootButton())){
            if (player.validateCommand(CommandType.SHOOT)) {
                commands.add(new Shoot(new Bullet(player), level, player));
            }
        }
        if (isButtonPressed(controlButtons.getShowHPButton(),controlButtons.getShowHPButton())){
            commands.add(new ShowHealthCommand(graphicsParams));
            graphicsParams.setHealthShow(!graphicsParams.isHealthShow());
        }
        return commands;
    }

}
