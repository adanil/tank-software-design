package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Tank;

import java.util.Collection;

public interface ICreationMapStrategy {

    void createMap(CreationMapParams params);
    Tank getPlayer();
    Collection<Obstacle> getObstacles();

}
