package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Control implements IMoveControl {
    ControlButtons controlButtons;

    public Control(ControlButtons controlButtons) {
        this.controlButtons = controlButtons;
    }

    @Override
    public void movePlayer(Player player, Collection<Obstacle> obstacles){
        if (isButtonPressed(controlButtons.getUpButton1(), controlButtons.getUpButton2())) {
            move(player,MoveVector.UP, obstacles);
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            move(player,MoveVector.LEFT, obstacles);
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            move(player,MoveVector.DOWN, obstacles);
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            move(player,MoveVector.RIGHT, obstacles);
        }
    }

    public boolean checkCollision(GridPoint2 playerDestCoords, Collection<Obstacle> obstacles){
        for (Obstacle obstacle : obstacles){
            if (obstacle.getCoordinates().equals(playerDestCoords))
                return true;
        }
        return false;
    }

    @Override
    public void move(Player player, MoveVector moveVector, Collection<Obstacle> obstacles){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            GridPoint2 playerDestCoords = player.getDestinationCoordinates().cpy().add(moveVector.vector);
            // check potential player destination for collision with obstacles
            if (!checkCollision(playerDestCoords,obstacles)) {
                player.setDestinationCoordinates(playerDestCoords);
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(moveVector.rotation);
        }
    }


}
