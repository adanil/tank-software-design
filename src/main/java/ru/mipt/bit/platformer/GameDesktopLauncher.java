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
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.*;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private IMoveRectangle tileMovement;

    Collection<Obstacle> trees;

    Player player;

    IMoveControl controller;

    ICreationMapStrategy creationMapStrategy;



    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        creationMapStrategy = new ReadMapCreation();
        createMap(groundLayer,creationMapStrategy);

        controller = new Control(new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D));

        setObstaclesAtTileCenter(groundLayer,trees);
    }

    private void createMap(TiledMapTileLayer groundLayer,ICreationMapStrategy creationMapStrategy) {
        CreationMapParams mapParams = new CreationMapParams("src/main/resources/levels/level1", groundLayer,new Texture("images/tank_blue.png"),new Texture("images/greenTree.png"));
        creationMapStrategy.createMap(mapParams);
        player = creationMapStrategy.getPlayer();
        trees = creationMapStrategy.getObstacles();
    }

    @Override
    public void render() {
        clearWindow();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        controller.movePlayer(player, trees);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.getGraphics(), player.getCurrentCoordinates(), player.getDestinationCoordinates(), player.getPlayerMovementProgress());

        player.calculateMovementProgress(deltaTime,MOVEMENT_SPEED);

        renderObjects();
    }

    private void renderObjects() {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRotation().value);

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
        player.getGraphics().getTexture().dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
