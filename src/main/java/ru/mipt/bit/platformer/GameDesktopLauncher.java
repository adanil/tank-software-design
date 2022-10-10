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
import ru.mipt.bit.platformer.util.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private IMoveRectangle tileMovement;

    Obstacle tree;

    Player player;

    IMoveControl controller;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        player = Player.createPlayerWithRandomPos(new Graphics(new Texture("images/tank_blue.png")),groundLayer.getWidth(),groundLayer.getHeight());

        tree = new Obstacle(new GridPoint2(1, 3),new Graphics(new Texture("images/greenTree.png")));

        controller = new Control(new ControlButtons(UP,W,DOWN,S,LEFT,A,RIGHT,D));

        moveRectangleAtTileCenter(groundLayer, tree.getGraphics(), tree.getCoordinates());
    }

    @Override
    public void render() {
        clearWindow();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        controller.movePlayer(player, tree);

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
        drawTextureRegionUnscaled(batch, tree.getGraphics(), 0f);

        // submit all drawing requests
        batch.end();
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
        tree.getGraphics().getTexture().dispose();
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
