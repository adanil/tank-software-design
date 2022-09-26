package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.util.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Control implements IMoveControl {
    ControlButtons controlButtons;

    public Control(ControlButtons controlButtons) {
        this.controlButtons = controlButtons;
    }

    @Override
    public void movePlayer(Player player, Obstacle obstacle){
        if (isButtonPressed(controlButtons.getUpButton1(), controlButtons.getUpButton2())) {
            moveUp(player,obstacle);
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            moveLeft(player,obstacle);
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            moveDown(player,obstacle);
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            moveRight(player,obstacle);
        }
    }

    @Override
    public void moveUp(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            // check potential player destination for collision with obstacles
            if (!obstacle.getCoordinates().equals(incrementedY(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesIncrementY();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.UP);
        }
    }

    @Override
    public void moveLeft(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedX(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesDecrementX();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.LEFT);
        }
    }

    @Override
    public void moveDown(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedY(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesDecrementY();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.DOWN);
        }
    }

    @Override
    public void moveRight(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(incrementedX(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesIncrementX();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.RIGHT);
        }
    }
}
