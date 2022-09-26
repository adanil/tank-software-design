package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

public interface IMoveControl {
    void movePlayer(Player player, Obstacle obstacle);

    default boolean isButtonPressed(int button1, int button2) {
        return Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2);
    }

    void moveUp(Player player, Obstacle obstacle);

    void moveLeft(Player player, Obstacle obstacle);

    void moveDown(Player player, Obstacle obstacle);

    void moveRight(Player player, Obstacle obstacle);
}
