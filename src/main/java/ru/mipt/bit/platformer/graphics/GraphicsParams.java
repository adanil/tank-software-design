package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.util.TileMovement;

public class GraphicsParams {
    GraphicPointer graphicalLevel;
    Level level;
    Batch batch;
    TileMovement tileMovement;
    MapRenderer levelRenderer;
    TiledMap levelGraphic;
    String playerTexturePath;
    String obstacleTexturePath;

    boolean healthShow = false;

    public GraphicsParams(GraphicPointer graphicalLevel, Level level, Batch batch, TileMovement tileMovement, MapRenderer levelRenderer, TiledMap levelGraphic,String playerTexturePath,String obstacleTexturePath) {
        this.graphicalLevel = graphicalLevel;
        this.level = level;
        this.batch = batch;
        this.tileMovement = tileMovement;
        this.levelRenderer = levelRenderer;
        this.levelGraphic = levelGraphic;
        this.playerTexturePath = playerTexturePath;
        this.obstacleTexturePath = obstacleTexturePath;
    }

    public GraphicPointer getGraphicalLevel() {
        return graphicalLevel;
    }

    public void setGraphicalLevel(GraphicPointer graphicalLevel) {
        this.graphicalLevel = graphicalLevel;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public void setTileMovement(TileMovement tileMovement) {
        this.tileMovement = tileMovement;
    }

    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public void setLevelRenderer(MapRenderer levelRenderer) {
        this.levelRenderer = levelRenderer;
    }

    public TiledMap getLevelGraphic() {
        return levelGraphic;
    }

    public void setLevelGraphic(TiledMap levelGraphic) {
        this.levelGraphic = levelGraphic;
    }

    public String getPlayerTexturePath() {
        return playerTexturePath;
    }

    public String getObstacleTexturePath() {
        return obstacleTexturePath;
    }

    public boolean isHealthShow() {
        return healthShow;
    }

    public void setHealthShow(boolean healthShow) {
        this.healthShow = healthShow;
    }
}
