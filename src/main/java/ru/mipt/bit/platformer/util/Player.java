package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Rotation rotation;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float playerMovementProgress;
    private Texture blueTankTexture;
    private TextureRegion graphics;
    private Rectangle rectangle;

    public void currentCoordinatesIncrementX(){
        currentCoordinates.x++;
    }
    public void currentCoordinatesIncrementY(){
        currentCoordinates.y++;
    }

    public void currentCoordinatesDecrementX(){
        currentCoordinates.x--;
    }
    public void currentCoordinatesDecrementY(){
        currentCoordinates.y--;
    }

    public void destinationCoordinatesIncrementX(){
        destinationCoordinates.x++;
    }
    public void destinationCoordinatesIncrementY(){
        destinationCoordinates.y++;
    }

    public void destinationCoordinatesDecrementX(){
        destinationCoordinates.x--;
    }
    public void destinationCoordinatesDecrementY(){
        destinationCoordinates.y--;
    }

    public Player() {
        destinationCoordinates = new GridPoint2(1,1);
        currentCoordinates = new GridPoint2(1,1);
        rotation = Rotation.RIGHT;
        playerMovementProgress = 1f;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public GridPoint2 getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(GridPoint2 currentCoordinates) {
        this.currentCoordinates.set(currentCoordinates);
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public void setPlayerMovementProgress(float playerMovementProgress) {
        this.playerMovementProgress = playerMovementProgress;
    }

    public Texture getBlueTankTexture() {
        return blueTankTexture;
    }

    public void setBlueTankTexture(Texture blueTankTexture) {
        this.blueTankTexture = blueTankTexture;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void setGraphics(TextureRegion graphics) {
        this.graphics = graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
