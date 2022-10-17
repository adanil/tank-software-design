package ru.tests;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.PlayerControl;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.util.ControlButtons;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.util.Rotation;
import ru.mipt.bit.platformer.util.TileObject;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerControlTest {

    @Test
    public void testCheckCollision(){
        Level level = new Level(10,10);
        PlayerControl playerControl = new PlayerControl(new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D),level);
        GridPoint2 coords = new GridPoint2(1,1);
        ArrayList<Obstacle> obstacles = new ArrayList<>();

        GridPoint2 obstacleCoords = new GridPoint2(1,1);
        obstacles.add(new Obstacle(obstacleCoords));
        level.addObjectOnMap(obstacleCoords.x, obstacleCoords.y, TileObject.OBSTACLE);
        boolean collision = playerControl.checkCollision(coords);
        assertTrue(collision);

        obstacleCoords = new GridPoint2(2,3);
        obstacles.add(new Obstacle(obstacleCoords));
        level.addObjectOnMap(obstacleCoords.x, obstacleCoords.y, TileObject.OBSTACLE);
        collision = playerControl.checkCollision(coords);
        assertTrue(collision);

        collision = playerControl.checkCollision(new GridPoint2(2,3));
        assertTrue(collision);

        collision = playerControl.checkCollision(new GridPoint2(4,7));
        assertFalse(collision);

        obstacleCoords = new GridPoint2(4,7);
        obstacles.add(new Obstacle(obstacleCoords));
        level.addObjectOnMap(obstacleCoords.x, obstacleCoords.y, TileObject.OBSTACLE);
        collision = playerControl.checkCollision(new GridPoint2(4,7));
        assertTrue(collision);
    }

    @Test
    public void testMove() {
        Level level = new Level(30,30);
        Tank player = new Tank(new GridPoint2(1, 1), new GridPoint2(1, 1), Rotation.UP);
        ArrayList<Obstacle> obstacles = new ArrayList<>();

        GridPoint2 obstacleCoords = new GridPoint2(10,10);
        obstacles.add(new Obstacle(obstacleCoords));
        level.addObjectOnMap(obstacleCoords.x,obstacleCoords.y,TileObject.OBSTACLE);

        PlayerControl playerControl = new PlayerControl(new ControlButtons(UP, W, DOWN, S, LEFT, A, RIGHT, D),level);

        playerControl.move(player, MoveVector.UP,TileObject.PLAYER);
        assertEquals(player.getDestinationCoordinates().x,1);
        assertEquals(player.getDestinationCoordinates().y,2);

        playerControl.calculateMovementProgress(player,1,1.0f);
        playerControl.move(player, MoveVector.RIGHT,TileObject.PLAYER);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,2);

        playerControl.calculateMovementProgress(player,1,1.0f);
        playerControl.move(player, MoveVector.RIGHT,TileObject.PLAYER);
        assertEquals(player.getDestinationCoordinates().x,3);
        assertEquals(player.getDestinationCoordinates().y,2);

        playerControl.calculateMovementProgress(player,1,1.0f);
        playerControl.move(player, MoveVector.LEFT,TileObject.PLAYER);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,2);

        playerControl.calculateMovementProgress(player,1,1.0f);
        playerControl.move(player, MoveVector.DOWN,TileObject.PLAYER);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,1);
    }
}
