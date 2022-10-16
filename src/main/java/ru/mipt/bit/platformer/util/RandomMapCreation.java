package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;

import java.util.Collection;

public class RandomMapCreation implements ICreationMapStrategy{
    Tank player;
    Collection<Obstacle> obstacles;


    @Override
    public void createMap(CreationMapParams params) {
        player = Tank.createPlayerWithRandomPos(params.getWidth(),params.getHeight());
        obstacles = Obstacle.generateRandomObstacles(params.getWidth(),params.getHeight());
    }

    @Override
    public Tank getPlayer() {
        return player;
    }

    @Override
    public Collection<Obstacle> getObstacles() {
        return obstacles;
    }
}
