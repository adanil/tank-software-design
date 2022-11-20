package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.entity.*;
import ru.mipt.bit.platformer.util.IMoveRectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class GraphicalLevelDecorator implements IGraphics, IEventListener {
    Level logicalLevel;
    Batch batch;
    IMoveRectangle tileMovement;
    MapRenderer levelRenderer;
    TiledMap levelGraphic;
    Tank player;
    Collection<Obstacle> trees;
    ArrayList<Tank> botTanks;
    HashSet<Bullet> bullets;
    protected IGraphics wrappee;
//    Collection<RenderObject> renderObjects;

    public GraphicalLevelDecorator(IGraphics wrappee,Level logicalLevel, Batch batch, IMoveRectangle tileMovement, MapRenderer levelRenderer, TiledMap levelGraphic) {
        this.logicalLevel = logicalLevel;
        this.batch = batch;
        this.tileMovement = tileMovement;
        this.levelRenderer = levelRenderer;
        this.levelGraphic = levelGraphic;
        this.wrappee = wrappee;
//        this.renderObjects = new ArrayList<>();
    }

    @Override
    public void update() {
        this.player = logicalLevel.getPlayer();
        this.trees = logicalLevel.getObstacles();
        this.botTanks = logicalLevel.getBots();
        this.bullets = logicalLevel.getBullets();
        wrappee.update();
    }

    @Override
    public void setGraphics() {
        wrappee.setGraphics();
    }

    @Override
    public void renderObjects(Collection<RenderObject> renderObjects) {
        wrappee.renderObjects(renderObjects);
    }

    @Override
    public void dispose() {
        wrappee.dispose();
    }
}
