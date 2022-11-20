package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.mapcreation.CreationMapParams;
import ru.mipt.bit.platformer.mapcreation.ICreationMapStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Level {
    private final int width;
    private final int height;

    private Tank player;
    private Collection<Obstacle> obstacles;
    private ArrayList<Tank> bots;

    private HashSet<Bullet> bullets;
    ArrayList<ArrayList<HashSet<TileObject>>> levelMap;

    ArrayList<IEventListener> subscribers;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        levelMap = new ArrayList<>();
        for (int h = 0;h < height;h++){
            ArrayList<HashSet<TileObject>> line = new ArrayList<>();
            for (int w = 0;w < width;w++){
                HashSet<TileObject> cellHS = new HashSet<>();
                line.add(cellHS);
            }
            levelMap.add(line);
        }

        bullets = new HashSet<>();
        subscribers = new ArrayList<>();
    }

    public void subscribe(IEventListener listener){
        subscribers.add(listener);
        listener.update();
    }

    public void removeSubscribe(IEventListener listener){
        subscribers.remove(listener);
    }

    private void notifySubscribers(){
        for (IEventListener sub : subscribers)
            sub.update();
    }

    public void addObjectOnMap(int x,int y, TileObject objectType){
        levelMap.get(y).get(x).add(objectType);
    }

    public void createLevel(ICreationMapStrategy creationMapStrategy, CreationMapParams params, int botsCount){
        creationMapStrategy.createMap(params);
        player = creationMapStrategy.getPlayer();
        obstacles = creationMapStrategy.getObstacles();

        addObjectOnMap(player.getCurrentCoordinates().x,player.getCurrentCoordinates().y,TileObject.PLAYER);
        for (Obstacle obstacle : obstacles){
            addObjectOnMap(obstacle.getCoordinates().x,obstacle.getCoordinates().y,TileObject.OBSTACLE);
        }
        bots = Tank.generateBotTanks(this, botsCount);
        for (Tank bot : bots){
            addObjectOnMap(bot.getCurrentCoordinates().x,bot.getCurrentCoordinates().y,TileObject.BOT);
        }
        notifySubscribers();
    }

    public boolean checkCollision(GridPoint2 destCoords){
        if (destCoords.x < 0 || destCoords.x >= width || destCoords.y < 0 || destCoords.y >= height)
            return true;
        HashSet<TileObject> tileObjects = this.getObjectByCoords(destCoords.x,destCoords.y);
        if (!tileObjects.isEmpty()){
            return true;
        }
        return false;
    }

    public HashSet<TileObject> checkCollisionBullet(GridPoint2 destCoords){
        if (destCoords.x < 0 || destCoords.x >= width || destCoords.y < 0 || destCoords.y >= height) {
            HashSet<TileObject> cellHS = new HashSet<>();
            cellHS.add(TileObject.OBSTACLE);
            return cellHS;
        }
        HashSet<TileObject> tileObjects = this.getObjectByCoords(destCoords.x,destCoords.y);
        return tileObjects;
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
        addObjectOnMap(bullet.getCurrentCoordinates().x,bullet.getCurrentCoordinates().y,TileObject.BULLET);
        notifySubscribers();
    }

    public void removeBullet(Bullet bullet){
        clearTile(bullet.getCurrentCoordinates());
        bullets.remove(bullet);
        notifySubscribers();
    }

    public void removeBot(Tank bot){
        clearTile(bot.getCurrentCoordinates());
        clearTile(bot.getDestinationCoordinates());
        bots.remove(bot);
        notifySubscribers();
    }

    public void moveObjects(IMoveable movableObject, TileObject objectType){
        levelMap.get(movableObject.getCurrentCoordinates().y).get(movableObject.getCurrentCoordinates().x).add(objectType);
        levelMap.get(movableObject.getDestinationCoordinates().y).get(movableObject.getDestinationCoordinates().x).add(objectType);
    }

    public void clearTile(GridPoint2 coords){
        HashSet<TileObject> cellHS = new HashSet<>();
        levelMap.get(coords.y).set(coords.x,cellHS);
    }

    public HashSet<TileObject> getObjectByCoords(int x,int y){
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

    public HashSet<Bullet> getBullets() {
        return bullets;
    }
}
