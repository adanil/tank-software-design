package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.util.Graphics;
import ru.mipt.bit.platformer.util.IMoveRectangle;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ShowHealthDecorator extends GraphicalLevelDecorator{


    public ShowHealthDecorator(IGraphics wrappee, Level logicalLevel, Batch batch, IMoveRectangle tileMovement, MapRenderer levelRenderer, TiledMap levelGraphic) {
        super(wrappee, logicalLevel, batch, tileMovement, levelRenderer, levelGraphic);
    }

    @Override
    public void setGraphics() {
        int playerHealth = player.getHealth();
        player.setHpBar(new Graphics(new Texture("images/hp/hpbar" + String.valueOf(playerHealth) + ".png")));
        for (Tank bot : botTanks){
            int botHealth = bot.getHealth();
            bot.setHpBar(new Graphics(new Texture("images/hp/hpbar" + String.valueOf(botHealth) + ".png")));
        }
        super.setGraphics();
    }

    @Override
    public void renderObjects(Collection<RenderObject> renderObjects) {
        setGraphics();
        renderObjects.add(new RenderObject(player.getHpBar(),player.getCurrentCoordinates(),player.getDestinationCoordinates(), player.getPlayerMovementProgress(),0f));
        for (Tank bot: botTanks){
            renderObjects.add(new RenderObject(bot.getHpBar(),bot.getCurrentCoordinates(),bot.getDestinationCoordinates(), bot.getPlayerMovementProgress(),0f));
        }
        super.renderObjects(renderObjects);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
