package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class PlayerControl implements IMoveControl {
    ControlButtons controlButtons;

    public PlayerControl(ControlButtons controlButtons) {
        this.controlButtons = controlButtons;
    }

    private boolean isButtonPressed(int button1, int button2) {
        return Gdx.input.isKeyPressed(button1) || Gdx.input.isKeyPressed(button2);
    }

    @Override
    public void moveTank(Tank player, Level level){
        if (isButtonPressed(controlButtons.getUpButton1(), controlButtons.getUpButton2())) {
            move(player,MoveVector.UP, level,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getLeftButton1(), controlButtons.getLeftButton2())) {
            move(player,MoveVector.LEFT, level,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getDownButton1(), controlButtons.getDownButton2())) {
            move(player,MoveVector.DOWN, level,TileObject.PLAYER);
        }
        if (isButtonPressed(controlButtons.getRightButton1(), controlButtons.getRightButton2())) {
            move(player,MoveVector.RIGHT, level,TileObject.PLAYER);
        }
    }



}
