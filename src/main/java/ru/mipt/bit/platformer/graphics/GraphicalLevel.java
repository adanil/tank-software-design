package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.IMoveRectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

/*
* Adapter
*/
public class GraphicalLevel implements IGraphics{
    Level logicalLevel;
    Batch batch;
    IMoveRectangle tileMovement;
    MapRenderer levelRenderer;
    TiledMap levelGraphic;
    Tank player;
    Collection<Obstacle> trees;
    ArrayList<Tank> botTanks;
    HashSet<Bullet> bullets;
    String playerTexturePath;
    String obstacleTexturePath;

//    Collection<RenderObject> renderObjects;

    public GraphicalLevel(Level logicalLevel, Batch batch, IMoveRectangle tileMovement, MapRenderer levelRenderer, TiledMap levelGraphic, String playerTexturePath,String obstacleTexturePath) {
        this.logicalLevel = logicalLevel;
        this.batch = batch;
        this.tileMovement = tileMovement;
        this.levelRenderer = levelRenderer;
        this.levelGraphic = levelGraphic;
        this.playerTexturePath = playerTexturePath;
        this.obstacleTexturePath = obstacleTexturePath;
//        renderObjects = new ArrayList<>();
    }

    @Override
    public void update() {
        this.player = logicalLevel.getPlayer();
        this.trees = logicalLevel.getObstacles();
        this.botTanks = logicalLevel.getBots();
        this.bullets = logicalLevel.getBullets();

        setGraphics();

    }

    public void setGraphics() {
        player.setGraphics(new Graphics(new Texture(playerTexturePath)));

        for (Tank bot : botTanks){
            bot.setGraphics(new Graphics(new Texture(playerTexturePath)));
        }

        for (Obstacle tree : trees){
            tree.setGraphics(new Graphics(new Texture(obstacleTexturePath)));
        }
    }

    public void renderObjects(Collection<RenderObject> renderObjects) {
        renderObjects.add(new RenderObject(player.getGraphics(),player.getCurrentCoordinates(),player.getDestinationCoordinates(),player.getPlayerMovementProgress(),player.getRotation().value));
        for (Obstacle tree : trees){
            renderObjects.add(new RenderObject(tree.getGraphics(),tree.getCoordinates(),tree.getCoordinates(),0,0f));
        }
        for (Tank bot : botTanks){
            renderObjects.add(new RenderObject(bot.getGraphics(),bot.getCurrentCoordinates(),bot.getDestinationCoordinates(),bot.getPlayerMovementProgress(),bot.getRotation().value));
        }
        for (Bullet bullet : bullets){
            renderObjects.add(new RenderObject(bullet.getGraphics(),bullet.getCurrentCoordinates(),bullet.getDestinationCoordinates(),bullet.getBulletMovementProgress(),bullet.getRotation().value));
        }


        for (RenderObject obj : renderObjects){
            tileMovement.moveRectangleBetweenTileCenters(obj.getGraphics(),obj.getCurrentCoordinates(),obj.getDestCoordinates(),obj.getMovementProgress());
        }

        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        for (RenderObject obj : renderObjects){
            drawTextureRegionUnscaled(batch,obj.getGraphics(),obj.getRotation());
        }
        // submit all drawing requests
        batch.end();
        renderObjects.clear();

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
