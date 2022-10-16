package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.Rotation;
import ru.mipt.bit.platformer.util.TileObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {
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

    public Tank(GridPoint2 destinationCoordinates, GridPoint2 currentCoordinates, Rotation rotation) {
        this.destinationCoordinates = destinationCoordinates;
        this.currentCoordinates = currentCoordinates;
        this.rotation = rotation;
        this.playerMovementProgress = 1f;
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
    
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED,Level level){
        setPlayerMovementProgress(continueProgress(getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            if (!destinationCoordinates.equals(currentCoordinates))
                level.clearTile(currentCoordinates);
            setCurrentCoordinates(getDestinationCoordinates());
        }
    }

    static public Tank createPlayerWithRandomPos(int levelWidth, int levelHeight){
        Random ran = new Random();
        int x = ran.nextInt(levelWidth - 1) + 1;
        int y = ran.nextInt(levelHeight - 1) + 1;
        GridPoint2 coords = new GridPoint2(x,y);
        return new Tank(coords,coords,Rotation.RIGHT);
    }

    static public ArrayList<Tank> generateBotTanks(Level level,int botsCount){
        ArrayList<Tank> bots = new ArrayList<>();
        int created = 0;
        while (created < botsCount){
            Random ran = new Random();
            int x = ran.nextInt(level.getWidth() - 1) + 1;
            int y = ran.nextInt(level.getHeight() - 1) + 1;

            if (level.getObjectByCoords(x,y) != TileObject.FREE)
                continue;

            int rot = ran.nextInt(4);
            GridPoint2 coords = new GridPoint2(x,y);
            bots.add(new Tank(coords,coords,Rotation.values()[rot]));
            created++;
        }
        return bots;

    }
}