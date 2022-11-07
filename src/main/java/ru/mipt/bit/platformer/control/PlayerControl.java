package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandGenerator;
import ru.mipt.bit.platformer.commands.Shoot;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.util.*;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerControl implements IMoveControl, CommandGenerator {
    ControlButtons controlButtons;
    Level level;

    Tank player;

    public PlayerControl(Tank player,ControlButtons controlButtons, Level level) {
        this.player = player;
        this.controlButtons = controlButtons;
        this.level = level;
    }

    private boolean isButtonPressed(int button1, int button2) {
        return Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2);
    }

    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED){
        player.setPlayerMovementProgress(continueProgress(player.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            if (!player.getDestinationCoordinates().equals(player.getCurrentCoordinates()))
                level.clearTile(player.getCurrentCoordinates());
            player.setCurrentCoordinates(player.getDestinationCoordinates());
        }
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
            commands.add(new MoveCommand(player,MoveVector.UP,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            commands.add(new MoveCommand(player,MoveVector.LEFT,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            commands.add(new MoveCommand(player,MoveVector.DOWN, TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            commands.add(new MoveCommand(player,MoveVector.RIGHT,TileObject.PLAYER,level));
        }
        if (isButtonPressed(controlButtons.getShootButton(), controlButtons.getShootButton())){
            commands.add(new Shoot(new Bullet(player),TileObject.BULLET,level));
        }
        return commands;
    }

}
