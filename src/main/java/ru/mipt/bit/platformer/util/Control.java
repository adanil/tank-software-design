package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Control {

    public static void move(Player player,Obstacle obstacle){
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            moveUp(player,obstacle);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            moveLeft(player,obstacle);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            moveDown(player,obstacle);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            moveRight(player,obstacle);
        }
    }
    public static void moveUp(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            // check potential player destination for collision with obstacles
            if (!obstacle.getCoordinates().equals(incrementedY(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesIncrementY();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.UP);
        }
    }

    public static void moveLeft(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedX(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesDecrementX();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.LEFT);
        }
    }

    public static void moveDown(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(decrementedY(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesDecrementY();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.DOWN);
        }
    }

    public static void moveRight(Player player, Obstacle obstacle){
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            if (!obstacle.getCoordinates().equals(incrementedX(player.getCurrentCoordinates()))) {
                player.destinationCoordinatesIncrementX();
                player.setPlayerMovementProgress(0f);
            }
            player.setRotation(Rotation.RIGHT);
        }
    }
}
