package ru.mipt.bit.platformer.mapcreation;

import ru.mipt.bit.platformer.entity.Obstacle;
import ru.mipt.bit.platformer.entity.Tank;

import java.util.Collection;

/*
* Adapter
*/
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
