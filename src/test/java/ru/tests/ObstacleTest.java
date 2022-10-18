package ru.tests;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Obstacle;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;



public class ObstacleTest {

    @Test
    public void testGenerateRandomObstacles(){
        int height = 15;
        int width = 15;
        Collection<Obstacle> obstacles = Obstacle.generateRandomObstacles(width,height);
        for (Obstacle obstacle : obstacles){
            if (!(obstacle.getCoordinates().x < width && obstacle.getCoordinates().x > 0 && obstacle.getCoordinates().y < height && obstacle.getCoordinates().y > 0)){
                fail("Width: " + width + " Height: " + height + " X: " + obstacle.getCoordinates().x + " Y: " + obstacle.getCoordinates().y);
            }
        }

        height = 30;
        width = 10;
        obstacles = Obstacle.generateRandomObstacles(width,height);
        for (Obstacle obstacle : obstacles){
            if (!(obstacle.getCoordinates().x < width && obstacle.getCoordinates().x > 0 && obstacle.getCoordinates().y < height && obstacle.getCoordinates().y > 0)){
                fail("Width: " + width + " Height: " + height + " X: " + obstacle.getCoordinates().x + " Y: " + obstacle.getCoordinates().y);
            }
        }

        height = 3;
        width = 3;
        obstacles = Obstacle.generateRandomObstacles(width,height);
        for (Obstacle obstacle : obstacles){
            if (!(obstacle.getCoordinates().x < width && obstacle.getCoordinates().x > 0 && obstacle.getCoordinates().y < height && obstacle.getCoordinates().y > 0)){
                fail("Width: " + width + " Height: " + height + " X: " + obstacle.getCoordinates().x + " Y: " + obstacle.getCoordinates().y);
            }
        }

        height = 2;
        width = 2;
        obstacles = Obstacle.generateRandomObstacles(width,height);
        for (Obstacle obstacle : obstacles){
            if (!(obstacle.getCoordinates().x < width && obstacle.getCoordinates().x > 0 && obstacle.getCoordinates().y < height && obstacle.getCoordinates().y > 0)){
                fail("Width: " + width + " Height: " + height + " X: " + obstacle.getCoordinates().x + " Y: " + obstacle.getCoordinates().y);
            }
        }

    }
}
