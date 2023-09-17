package ru.tests;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Control;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;
import ru.mipt.bit.platformer.util.ControlButtons;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.util.Rotation;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static org.junit.jupiter.api.Assertions.*;

public class ControlTest {

    @Test
    public void testCheckCollision(){
        Control control = new Control(new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D));
        GridPoint2 coords = new GridPoint2(1,1);
        ArrayList<Obstacle> obstacles = new ArrayList<>();

        obstacles.add(new Obstacle(new GridPoint2(1,1)));
        boolean collision = control.checkCollision(coords,obstacles);
        assertTrue(collision);

        obstacles.add(new Obstacle(new GridPoint2(2,3)));
        collision = control.checkCollision(coords,obstacles);
        assertTrue(collision);

        collision = control.checkCollision(new GridPoint2(2,3),obstacles);
        assertTrue(collision);

        collision = control.checkCollision(new GridPoint2(4,7),obstacles);
        assertFalse(collision);

        obstacles.add(new Obstacle(new GridPoint2(4,7)));
        collision = control.checkCollision(new GridPoint2(4,7),obstacles);
        assertTrue(collision);
    }

    @Test
    public void testMove() {
        Player player = new Player(new GridPoint2(1, 1), new GridPoint2(1, 1), Rotation.UP);
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new GridPoint2(10,10)));

        Control control = new Control(new ControlButtons(UP, W, DOWN, S, LEFT, A, RIGHT, D));

        control.move(player, MoveVector.UP,obstacles);
        assertEquals(player.getDestinationCoordinates().x,1);
        assertEquals(player.getDestinationCoordinates().y,2);

        player.setPlayerMovementProgress(1.0f);
        control.move(player, MoveVector.RIGHT,obstacles);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,2);

        player.setPlayerMovementProgress(1.0f);
        control.move(player, MoveVector.RIGHT,obstacles);
        assertEquals(player.getDestinationCoordinates().x,3);
        assertEquals(player.getDestinationCoordinates().y,2);

        player.setPlayerMovementProgress(1.0f);
        control.move(player, MoveVector.LEFT,obstacles);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,2);

        player.setPlayerMovementProgress(1.0f);
        control.move(player, MoveVector.DOWN,obstacles);
        assertEquals(player.getDestinationCoordinates().x,2);
        assertEquals(player.getDestinationCoordinates().y,1);
    }
}
