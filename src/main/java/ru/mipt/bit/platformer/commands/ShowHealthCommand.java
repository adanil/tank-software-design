package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.graphics.*;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

/*
 * Adapter
 */
public class ShowHealthCommand implements Command{
    GraphicPointer graphicalLevel;
    Level level;
    Batch batch;
    TileMovement tileMovement;
    MapRenderer levelRenderer;
    TiledMap levelGraphic;
    String playerTexturePath;
    String obstacleTexturePath;

    boolean healthShow;

    public ShowHealthCommand(GraphicsParams graphicsParams) {
        this.graphicalLevel = graphicsParams.getGraphicalLevel();
        this.level = graphicsParams.getLevel();
        this.batch = graphicsParams.getBatch();
        this.tileMovement = graphicsParams.getTileMovement();
        this.levelRenderer = graphicsParams.getLevelRenderer();
        this.levelGraphic = graphicsParams.getLevelGraphic();
        this.playerTexturePath = graphicsParams.getPlayerTexturePath();
        this.obstacleTexturePath = graphicsParams.getObstacleTexturePath();
        this.healthShow = graphicsParams.isHealthShow();
    }

    @Override
    public void execute() {
        if (healthShow){
            graphicalLevel.setSource(new GraphicalLevel(level,batch,tileMovement,levelRenderer,levelGraphic,playerTexturePath,obstacleTexturePath));
            level.subscribe(graphicalLevel.getSource());
        }
        else {
            graphicalLevel.setSource(new ShowHealthDecorator(graphicalLevel.getSource(), level, batch, tileMovement, levelRenderer, levelGraphic));
            level.subscribe(graphicalLevel.getSource());
        }
    }
}
