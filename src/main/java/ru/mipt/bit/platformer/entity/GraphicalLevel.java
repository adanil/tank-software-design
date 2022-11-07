package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.IMoveRectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicalLevel implements IEventListener{
    Level logicalLevel;
    Batch batch;
    IMoveRectangle tileMovement;
    MapRenderer levelRenderer;
    TiledMap levelGraphic;
    Tank player;
    Collection<Obstacle> trees;
    ArrayList<Tank> botTanks;
    HashSet<Bullet> bullets;

    public GraphicalLevel(Level logicalLevel, Batch batch, IMoveRectangle tileMovement, MapRenderer levelRenderer, TiledMap levelGraphic) {
        this.logicalLevel = logicalLevel;
        this.batch = batch;
        this.tileMovement = tileMovement;
        this.levelRenderer = levelRenderer;
        this.levelGraphic = levelGraphic;
    }

    @Override
    public void update() {
        this.player = logicalLevel.getPlayer();
        this.trees = logicalLevel.getObstacles();
        this.botTanks = logicalLevel.getBots();
        this.bullets = logicalLevel.getBullets();
    }

    public void setGraphics(String playerTexturePath, String obstacleTexturePath) {
        player.setGraphics(new Graphics(new Texture(playerTexturePath)));

        for (Tank bot : botTanks){
            bot.setGraphics(new Graphics(new Texture(playerTexturePath)));
        }

        for (Obstacle tree : trees){
            tree.setGraphics(new Graphics(new Texture(obstacleTexturePath)));
        }
    }

    public void renderObjects() {
        for (Tank bot : botTanks){
            tileMovement.moveRectangleBetweenTileCenters(bot.getGraphics(), bot.getCurrentCoordinates(), bot.getDestinationCoordinates(), bot.getPlayerMovementProgress());
        }
        for (Bullet bullet : bullets){
            tileMovement.moveRectangleBetweenTileCenters(bullet.getGraphics(),bullet.getCurrentCoordinates(),bullet.getDestinationCoordinates(), bullet.getBulletMovementProgress());
        }
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.getGraphics(), player.getCurrentCoordinates(), player.getDestinationCoordinates(), player.getPlayerMovementProgress());

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

        //render bullets

        for (Bullet bullet : bullets){
            drawTextureRegionUnscaled(batch,bullet.getGraphics(),bullet.getRotation().value);
        }

        // submit all drawing requests
        batch.end();
    }

    public void dispose(){
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
}
