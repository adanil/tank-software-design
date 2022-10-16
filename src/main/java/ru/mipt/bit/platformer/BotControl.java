package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.IMoveControl;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.util.TileObject;

import java.util.Collection;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class BotControl implements IMoveControl {

    @Override
    public void moveTank(Tank player, Level level) {
        Random ran = new Random();
        int mv = ran.nextInt(4);
        move(player,MoveVector.values()[mv],level, TileObject.BOT);
    }

}
