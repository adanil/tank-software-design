package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.CreationMapParams;
import ru.mipt.bit.platformer.util.ICreationMapStrategy;
import ru.mipt.bit.platformer.util.TileObject;

import java.util.ArrayList;
import java.util.Collection;

public class Level {
    private final int width;
    private final int height;

    private Tank player;
    private Collection<Obstacle> obstacles;
    private ArrayList<Tank> bots;


    ArrayList<ArrayList<TileObject>> levelMap;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        levelMap = new ArrayList<>();
        for (int h = 0;h < height;h++){
            ArrayList<TileObject> line = new ArrayList<>();
            for (int w = 0;w < width;w++){
                line.add(TileObject.FREE);
            }
            levelMap.add(line);
        }
    }

    public void addObjectOnMap(int x,int y, TileObject objectType){
        levelMap.get(y).set(x,objectType);
    }

    public void createLevel(ICreationMapStrategy creationMapStrategy, CreationMapParams params){
        creationMapStrategy.createMap(params);
        player = creationMapStrategy.getPlayer();
        obstacles = creationMapStrategy.getObstacles();



        addObjectOnMap(player.getCurrentCoordinates().x,player.getCurrentCoordinates().y,TileObject.PLAYER);
        for (Obstacle obstacle : obstacles){
            addObjectOnMap(obstacle.getCoordinates().x,obstacle.getCoordinates().y,TileObject.OBSTACLE);
        }
        bots = Tank.generateBotTanks(this,5);
        for (Tank bot : bots){
            addObjectOnMap(bot.getCurrentCoordinates().x,bot.getCurrentCoordinates().y,TileObject.BOT);
        }
    }

    public void moveObjects(Tank movableObject, TileObject objectType){
        levelMap.get(movableObject.getCurrentCoordinates().y).set(movableObject.getCurrentCoordinates().x,objectType);
        levelMap.get(movableObject.getDestinationCoordinates().y).set(movableObject.getDestinationCoordinates().x,objectType);
    }

    public void clearTile(GridPoint2 coords){
        levelMap.get(coords.y).set(coords.x,TileObject.FREE);
    }

    public TileObject getObjectByCoords(int x,int y){
        return levelMap.get(y).get(x);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tank getPlayer() {
        return player;
    }

    public Collection<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Tank> getBots() {
        return bots;
    }
}
