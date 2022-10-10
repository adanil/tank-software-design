package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

import java.util.Collection;

public class RandomMapCreation implements ICreationMapStrategy{
    Player player;
    Collection<Obstacle> obstacles;


    @Override
    public void createMap(CreationMapParams params) {
        player = Player.createPlayerWithRandomPos(params.getWidth(),params.getHeight());
        obstacles = Obstacle.generateRandomObstacles(params.getWidth(),params.getHeight());
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Collection<Obstacle> getObstacles() {
        return obstacles;
    }
}
