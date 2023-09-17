package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

import java.util.Collection;

public interface ICreationMapStrategy {

    void createMap(CreationMapParams params);
    Player getPlayer();
    Collection<Obstacle> getObstacles();

}
