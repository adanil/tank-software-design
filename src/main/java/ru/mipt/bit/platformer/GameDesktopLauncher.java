package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.control.BotControl;
import ru.mipt.bit.platformer.control.BulletControl;
import ru.mipt.bit.platformer.control.ControlButtons;
import ru.mipt.bit.platformer.control.PlayerControl;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.mapcreation.CreationMapParams;
import ru.mipt.bit.platformer.mapcreation.ICreationMapStrategy;
import ru.mipt.bit.platformer.mapcreation.ReadMapCreation;
import ru.mipt.bit.platformer.util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    public static final String playerTexturePath = "images/tank_blue.png";
    public static final String obstacleTexturePath = "images/greenTree.png";
    public static final String mapFilePath = "src/main/resources/levels/level1";


    Level level;

    GraphicalLevel graphicalLevel;

    PlayerControl playerControl;
    BotControl botController;

    BulletControl bulletControl;

    ICreationMapStrategy creationMapStrategy;
    AIAdapter ai;


    @Override
    public void create() {
        Batch batch = new SpriteBatch();

        // load level tiles
        TiledMap levelGraphic = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(levelGraphic, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelGraphic);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        //Create logical level
        creationMapStrategy = new ReadMapCreation();
        CreationMapParams mapParams = new CreationMapParams(mapFilePath,groundLayer.getWidth(),groundLayer.getHeight());
        level = new Level(groundLayer.getWidth(),groundLayer.getHeight());
        level.createLevel(creationMapStrategy,mapParams, 5);

        //Create graphical level
        graphicalLevel = new GraphicalLevel(level,batch,tileMovement,levelRenderer,levelGraphic);
        level.subscribe(graphicalLevel);

        Tank player = level.getPlayer();
        Collection<Obstacle> trees = level.getObstacles();
        Collection<Tank> botTanks = level.getBots();

        playerControl = new PlayerControl(player,new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D,SPACE),level);
        botController = new BotControl(botTanks,level);
        bulletControl = new BulletControl(level);

        ai = new AIAdapter(botTanks,player,trees,level);

        graphicalLevel.setGraphics(playerTexturePath, obstacleTexturePath);
        setObstaclesAtTileCenter(groundLayer,trees);
    }



    @Override
    public void render() {
        clearWindow();

        executeCommands();

        live();

        graphicalLevel.renderObjects();
    }

    private void executeCommands() {
        Collection<Command> commands = ai.generateCommands();
        for (Command cmd : commands){
            cmd.execute();
        }

        playerControl.handleCommands();
    }

    public void live(){
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        playerControl.calculateMovementProgress(deltaTime,MOVEMENT_SPEED);

        botController.calculateMovementProgress(deltaTime,MOVEMENT_SPEED);

        bulletControl.calculateMovementProgress(deltaTime,MOVEMENT_SPEED);

    }



    private void setObstaclesAtTileCenter(TiledMapTileLayer groundLayer,Collection<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            moveRectangleAtTileCenter(groundLayer, obstacle.getGraphics(), obstacle.getCoordinates());
        }
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        graphicalLevel.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
