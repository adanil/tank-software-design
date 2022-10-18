package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.IMoveControl;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.util.TileObject;

import java.util.Collection;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class BotControl implements IMoveControl {
    Level level;

    public BotControl(Level level) {
        this.level = level;
    }

    @Override
    public void moveTank(Tank player, Level level) {
        Random ran = new Random();
        int mv = ran.nextInt(4);
        move(player,MoveVector.values()[mv], TileObject.BOT);
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

}
