package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Input;

import static com.badlogic.gdx.Input.Keys.*;

public class ControlButtons {
    private final int upButton1;
    private final int upButton2;

    private final int downButton1;
    private final int downButton2;

    private final int leftButton1;
    private final int leftButton2;

    private final int rightButton1;
    private final int rightButton2;

    public ControlButtons(int upButton1, int upButton2, int downButton1, int downButton2, int leftButton1, int leftButton2, int rightButton1, int rightButton2) {
        this.upButton1 = upButton1;
        this.upButton2 = upButton2;
        this.downButton1 = downButton1;
        this.downButton2 = downButton2;
        this.leftButton1 = leftButton1;
        this.leftButton2 = leftButton2;
        this.rightButton1 = rightButton1;
        this.rightButton2 = rightButton2;
    }

    public int getUpButton1() {
        return upButton1;
    }

    public int getUpButton2() {
        return upButton2;
    }

    public int getDownButton1() {
        return downButton1;
    }

    public int getDownButton2() {
        return downButton2;
    }

    public int getLeftButton1() {
        return leftButton1;
    }

    public int getLeftButton2() {
        return leftButton2;
    }

    public int getRightButton1() {
        return rightButton1;
    }

    public int getRightButton2() {
        return rightButton2;
    }
}
