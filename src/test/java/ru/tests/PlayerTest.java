package ru.tests;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Player;

import static org.junit.jupiter.api.Assertions.fail;

public class PlayerTest {

    @Test
    public void testCreateRandomPlayer(){
        int height = 15;
        int width = 15;
        Player player = Player.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }


        height = 30;
        width = 15;
        player = Player.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }

        height = 3;
        width = 5;
        player = Player.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }

        height = 3;
        width = 2;
        player = Player.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }
    }
}
