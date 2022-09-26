package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Graphics {
    Texture texture;
    TextureRegion graphics;
    Rectangle rectangle;

    public Graphics() {
    }

    public Graphics(Texture texture) {
        this.texture = texture;
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public Graphics(Texture texture, TextureRegion graphics, Rectangle rectangle) {
        this.texture = texture;
        this.graphics = graphics;
        this.rectangle = rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void setGraphics(TextureRegion graphics) {
        this.graphics = graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}