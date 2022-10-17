package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerControl implements IMoveControl {
    ControlButtons controlButtons;
    Level level;

    public PlayerControl(ControlButtons controlButtons, Level level) {
        this.controlButtons = controlButtons;
        this.level = level;
    }

    private boolean isButtonPressed(int button1, int button2) {
        return Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2);
    }

    @Override
    public void calculateMovementProgress(Tank tank,float deltaTime, float MOVEMENT_SPEED){
        tank.setPlayerMovementProgress(continueProgress(tank.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            if (!tank.getDestinationCoordinates().equals(tank.getCurrentCoordinates()))
                level.clearTile(tank.getCurrentCoordinates());
            tank.setCurrentCoordinates(tank.getDestinationCoordinates());
        }
    }

    @Override
    public void move(Tank tank, MoveVector moveVector, TileObject objectType){
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            GridPoint2 playerDestCoords = tank.getDestinationCoordinates().cpy().add(moveVector.vector);
            // check potential player destination for collision with obstacles
            if (!checkCollision(playerDestCoords)) {
                tank.setDestinationCoordinates(playerDestCoords);
                tank.setPlayerMovementProgress(0f);
                level.moveObjects(tank,objectType);
            }
            tank.setRotation(moveVector.rotation);
        }
    }
    @Override
    public boolean checkCollision(GridPoint2 playerDestCoords){
        if (playerDestCoords.x < 0 || playerDestCoords.x >= level.getWidth() || playerDestCoords.y < 0 || playerDestCoords.y >= level.getHeight())
            return true;
        if (level.getObjectByCoords(playerDestCoords.x,playerDestCoords.y) != TileObject.FREE){
            return true;
        }
        return false;
    }

    @Override
    public void moveTank(Tank player, Level level){
        if (isButtonPressed(controlButtons.getUpButton1(), controlButtons.getUpButton2())) {
            move(player,MoveVector.UP,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            move(player,MoveVector.LEFT,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            move(player,MoveVector.DOWN,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            move(player,MoveVector.RIGHT,TileObject.PLAYER);
        }
    }



}
