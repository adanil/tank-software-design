package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.MoveVector;
import ru.mipt.bit.platformer.util.Rotation;

public class Bullet implements IMoveable{
    public static final String bulletTexturePath = "images/bullet.png";

    private Graphics graphics;

    private Rotation rotation;

    private MoveVector moveVector;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float bulletMovementProgress;

    private Tank source;

    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Bullet(GridPoint2 destinationCoordinates, GridPoint2 currentCoordinates, Rotation rotation) {
        this.destinationCoordinates = destinationCoordinates;
        this.currentCoordinates = currentCoordinates;
        this.rotation = rotation;
        this.bulletMovementProgress = 0f;
    }
    public Bullet(Tank tank){
        this.source = tank;
        this.rotation = tank.getRotation();
        this.bulletMovementProgress = 0f;
        this.graphics = new Graphics(new Texture(bulletTexturePath));

        switch (this.rotation){
            case RIGHT:{
                this.moveVector = MoveVector.RIGHT;
                break;
            }
            case LEFT:{
                this.moveVector = MoveVector.LEFT;
                break;
            }
            case UP:{
                this.moveVector = MoveVector.UP;
                break;
            }
            case DOWN:{
                this.moveVector = MoveVector.DOWN;
                break;
            }
        }
        this.currentCoordinates = tank.getCurrentCoordinates().cpy().add(moveVector.vector);
        this.destinationCoordinates = this.currentCoordinates.cpy();

    }

    public void calcDestCoords() {
        this.destinationCoordinates = this.currentCoordinates.cpy().add(this.moveVector.vector);
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

    public float getBulletMovementProgress() {
        return bulletMovementProgress;
    }

    public void setBulletMovementProgress(float bulletMovementProgress) {
        this.bulletMovementProgress = bulletMovementProgress;
    }
    public MoveVector getMoveVector() {
        return moveVector;
    }
    public Tank getSource() {
        return source;
    }
}
