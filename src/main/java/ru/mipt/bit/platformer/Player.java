package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.Rotation;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player {
    private Graphics graphics;

    private Rotation rotation;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float playerMovementProgress;
    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

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

    public Player(GridPoint2 destinationCoordinates, GridPoint2 currentCoordinates, Rotation rotation, Graphics graphics) {
        this.destinationCoordinates = destinationCoordinates;
        this.currentCoordinates = currentCoordinates;
        this.rotation = rotation;
        this.playerMovementProgress = 1f;
        this.graphics = graphics;
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
    
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED){
        setPlayerMovementProgress(continueProgress(getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            setCurrentCoordinates(getDestinationCoordinates());
        }
    }
}
