package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.Graphics;

public class Obstacle {
    private GridPoint2 coordinates;

    private Graphics graphics;

    public Obstacle(GridPoint2 coordinates,Graphics graphics) {
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

}
