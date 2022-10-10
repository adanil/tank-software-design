package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Graphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Obstacle {
    private GridPoint2 coordinates;

    private Graphics graphics;

    public Obstacle(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public Obstacle(GridPoint2 coordinates, Graphics graphics) {
        this.coordinates = coordinates;
        this.graphics = graphics;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }


    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    static public ArrayList<Obstacle> generateRandomObstacles(int levelWidth, int levelHeight){
        Random ran = new Random();
        int numberOfObstacles = ran.nextInt(levelWidth*levelHeight/4) + 1;

        HashSet<GridPoint2> obstacleCoords = new HashSet<>();
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        int count = 0;
        while(count < numberOfObstacles){
            int x = ran.nextInt(levelWidth - 1) + 1;
            int y = ran.nextInt(levelHeight - 1) + 1;
            GridPoint2 coords = new GridPoint2(x,y);
            if (!obstacleCoords.contains(coords)) {
                obstacleCoords.add(coords);
                obstacles.add(new Obstacle(coords));
                count++;
            }
        }
        return obstacles;

    }

}
