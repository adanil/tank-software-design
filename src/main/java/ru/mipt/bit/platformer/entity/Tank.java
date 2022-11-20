package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.commands.CommandType;
import ru.mipt.bit.platformer.tankstate.HardState;
import ru.mipt.bit.platformer.tankstate.LightState;
import ru.mipt.bit.platformer.tankstate.MediumState;
import ru.mipt.bit.platformer.tankstate.TankState;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.Rotation;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements IMoveable{
    private TankState state;
    private Graphics graphics;
    private Graphics hpBar;
    private Rotation rotation;
    private GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float playerMovementProgress;
    private final int startHealth = 10;
    private int health = startHealth;
    public Tank(GridPoint2 destinationCoordinates, GridPoint2 currentCoordinates, Rotation rotation) {
        this.destinationCoordinates = destinationCoordinates;
        this.currentCoordinates = currentCoordinates;
        this.rotation = rotation;
        this.playerMovementProgress = 1f;
        this.state = new LightState(this);
    }

    public void calculateMovement(float deltaTime, float MOVEMENT_SPEED, Level level){
        state.calculateMovementProgress(deltaTime,MOVEMENT_SPEED,level);
    }
    public boolean validateCommand(CommandType cmdType){
        return state.validateCommand(cmdType);
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

            if (!level.getObjectByCoords(x,y).isEmpty())
                continue;

            int rot = ran.nextInt(4);
            GridPoint2 coords = new GridPoint2(x,y);
            bots.add(new Tank(coords,coords,Rotation.values()[rot]));
            created++;
        }
        return bots;

    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public Graphics getHpBar() {
        return hpBar;
    }

    public void setHpBar(Graphics hpBar) {
        this.hpBar = hpBar;
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

    public int getHealth() {
        return health;
    }

    public void updateHealth(int health)
    {
        if (this.health >= 0.7 * startHealth && health < 0.7 * startHealth){
            this.state = new MediumState(this);
        }
        else if (this.health > 0.15 * startHealth && health < 0.15 * startHealth){
            this.state = new HardState(this);
        }

        this.health = health;

    }
}
