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
import ru.mipt.bit.platformer.util.*;

import java.util.ArrayList;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    public static final String playerTexturePath = "images/tank_blue.png";
    public static final String obstacleTexturePath = "images/greenTree.png";
    public static final String mapFilePath = "src/main/resources/levels/level1";

    private Batch batch;

    private TiledMap levelGraphic;
    private MapRenderer levelRenderer;
    private IMoveRectangle tileMovement;

    Collection<Obstacle> trees;

    Tank player;

    ArrayList<Tank> botTanks;

    Level level;

    IMoveControl controller;
    IMoveControl botController;

    ICreationMapStrategy creationMapStrategy;



    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        levelGraphic = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(levelGraphic, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelGraphic);

        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        creationMapStrategy = new ReadMapCreation();
        CreationMapParams mapParams = new CreationMapParams(mapFilePath,groundLayer.getWidth(),groundLayer.getHeight());
        level = new Level(groundLayer.getWidth(),groundLayer.getHeight());
        level.createLevel(creationMapStrategy,mapParams);

        player = level.getPlayer();
        trees = level.getObstacles();
        botTanks = level.getBots();

        controller = new PlayerControl(new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D),level);
        botController = new BotControl(level);

        setGraphics(playerTexturePath, obstacleTexturePath);
        setObstaclesAtTileCenter(groundLayer,trees);
    }

    private void setGraphics(String playerTexturePath, String obstacleTexturePath) {
        player.setGraphics(new Graphics(new Texture(playerTexturePath)));

        for (Tank bot : botTanks){
            bot.setGraphics(new Graphics(new Texture(playerTexturePath)));
        }

        for (Obstacle tree : trees){
            tree.setGraphics(new Graphics(new Texture(obstacleTexturePath)));
        }
    }

    @Override
    public void render() {
        clearWindow();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        controller.moveTank(player, level);
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.getGraphics(), player.getCurrentCoordinates(), player.getDestinationCoordinates(), player.getPlayerMovementProgress());
        controller.calculateMovementProgress(player,deltaTime,MOVEMENT_SPEED);

        for (Tank bot : botTanks){
            botController.moveTank(bot,level);
            tileMovement.moveRectangleBetweenTileCenters(bot.getGraphics(), bot.getCurrentCoordinates(), bot.getDestinationCoordinates(), bot.getPlayerMovementProgress());
            botController.calculateMovementProgress(bot,deltaTime,MOVEMENT_SPEED);
        }

        renderObjects();
    }

    private void renderObjects() {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRotation().value);

        for (Tank bot : botTanks){
            drawTextureRegionUnscaled(batch,bot.getGraphics(),bot.getRotation().value);
        }

        // render tree obstacle
        for (Obstacle tree : trees) {
            drawTextureRegionUnscaled(batch, tree.getGraphics(), 0f);
        }

        // submit all drawing requests
        batch.end();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (Obstacle tree : trees) {
            tree.getGraphics().getTexture().dispose();
        }

        for (Tank bot : botTanks){
            bot.getGraphics().getTexture().dispose();
        }
        player.getGraphics().getTexture().dispose();
        levelGraphic.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
