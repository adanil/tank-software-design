package ru.mipt.bit.platformer.mapcreation;

import ru.mipt.bit.platformer.entity.Obstacle;
import ru.mipt.bit.platformer.entity.Tank;

import java.util.Collection;

public interface ICreationMapStrategy {

    void createMap(CreationMapParams params);
    Tank getPlayer();
    Collection<Obstacle> getObstacles();

}
