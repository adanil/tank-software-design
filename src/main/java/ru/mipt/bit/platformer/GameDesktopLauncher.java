package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.Obstacle;
import ru.mipt.bit.platformer.util.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.Control.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    Obstacle tree;

    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // set player initial position
        player = new Player();
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        player.setBlueTankTexture(new Texture("images/tank_blue.png"));
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        player.setGraphics(new TextureRegion(player.getBlueTankTexture()));
        player.setRectangle(createBoundingRectangle(player.getGraphics()));


        var greenTreeTexture = new Texture("images/greenTree.png");
        var treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        var treeObstacleCoordinates = new GridPoint2(1, 3);
        var treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        tree = new Obstacle(greenTreeTexture,treeObstacleGraphics,treeObstacleCoordinates,treeObstacleRectangle);

        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    @Override
    public void render() {
        clearWindow();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        move(player, tree);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.getRectangle(), player.getCurrentCoordinates(), player.getDestinationCoordinates(), player.getPlayerMovementProgress());

        player.setPlayerMovementProgress(continueProgress(player.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            player.setCurrentCoordinates(player.getDestinationCoordinates());
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.getGraphics(), player.getRectangle(), player.getRotation().value);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.getGraphics(), tree.getRectangle(), 0f);

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
        tree.getTexture().dispose();
        player.getBlueTankTexture().dispose();
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
