package ru.tests;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.util.CreationMapParams;
import ru.mipt.bit.platformer.util.ICreationMapStrategy;
import ru.mipt.bit.platformer.util.RandomMapCreation;
import ru.mipt.bit.platformer.util.ReadMapCreation;
import static org.junit.jupiter.api.Assertions.*;

public class CreationMapTest {

    @Test
    public void readMapCreation(){
        int width = 10;
        int height = 8;
        CreationMapParams params = new CreationMapParams("src/main/resources/levels/level1",width,height);

        ICreationMapStrategy readMapCreationAlg  = new ReadMapCreation();
        readMapCreationAlg.createMap(params);

        assertEquals(readMapCreationAlg.getObstacles().size(),15);

        assertEquals(readMapCreationAlg.getPlayer().getCurrentCoordinates().x,5);
        assertEquals(readMapCreationAlg.getPlayer().getCurrentCoordinates().y,3);
    }

    @Test
    public void randomMapCreation(){
        int width = 10;
        int height = 8;
        CreationMapParams params = new CreationMapParams("src/main/resources/levels/level1",width,height);

        ICreationMapStrategy randomMapCreationAlg  = new RandomMapCreation();
        randomMapCreationAlg.createMap(params);
        Tank player = randomMapCreationAlg.getPlayer();

        if (!(player.getCurrentCoordinates().x < width && player.getCurrentCoordinates().x > 0 && player.getCurrentCoordinates().y < height && player.getCurrentCoordinates().y > 0)){
            fail("Width: " + width + " Height: " + height + " X: " + player.getCurrentCoordinates().x + " Y: " + player.getCurrentCoordinates().y);
        }

        assertTrue(randomMapCreationAlg.getObstacles().size() > 0);
    }
}
