package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.Obstacle;
import ru.mipt.bit.platformer.Player;

import java.util.Collection;

public interface IMoveControl {
    void movePlayer(Player player, Collection<Obstacle> obstacles);

    default boolean isButtonPressed(int button1, int button2) {
        return Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2);
    }

    void move(Player player, MoveVector moveVector, Collection<Obstacle> obstacles);
}
