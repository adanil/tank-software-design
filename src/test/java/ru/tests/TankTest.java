package ru.tests;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.entity.Tank;

import static org.junit.jupiter.api.Assertions.fail;

public class TankTest {

    @Test
    public void testCreateRandomPlayer(){
        int height = 15;
        int width = 15;
        Tank player = Tank.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }


        height = 30;
        width = 15;
        player = Tank.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }

        height = 3;
        width = 5;
        player = Tank.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }

        height = 3;
        width = 2;
        player = Tank.createPlayerWithRandomPos(width,height);
        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }
    }
}
