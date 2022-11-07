package ru.mipt.bit.platformer.control;

import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.entity.Bullet;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.TileObject;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class BulletControl implements IMoveControl{
    Level level;
    public BulletControl(Level level){
        this.level = level;
    }
    @Override
    public void calculateMovementProgress(float deltaTime, float MOVEMENT_SPEED) {
        HashSet<Bullet> bullets = (HashSet<Bullet>) level.getBullets();
        ArrayList<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.setBulletMovementProgress(continueProgress(bullet.getBulletMovementProgress(), deltaTime, MOVEMENT_SPEED));
            if (isEqual(bullet.getBulletMovementProgress(), 1f)) {
                if (!bullet.getDestinationCoordinates().equals(bullet.getCurrentCoordinates()))
                    level.clearTile(bullet.getCurrentCoordinates());
                bullet.setCurrentCoordinates(bullet.getDestinationCoordinates());
                bullet.calcDestCoords();
                bullet.setBulletMovementProgress(0);

                TileObject tileObject = level.getObjectByCoords(bullet.getCurrentCoordinates().x,bullet.getCurrentCoordinates().y);
                if (tileObject == TileObject.PLAYER){
                    Tank player = level.getPlayer();
                    player.setHealth(player.getHealth() - 1);
                }
                else if (tileObject == TileObject.BOT){
                    ArrayList<Tank> bots = level.getBots();
                    ArrayList<Tank> botsToRemove = new ArrayList<>();
                    for (Tank bot : bots){
                        if (bot.getCurrentCoordinates().equals(bullet.getDestinationCoordinates())) {
                            bot.setHealth(bot.getHealth() - 1);
                            if (bot.getHealth() == 0)
                                botsToRemove.add(bot);
                        }
                    }
                    for (Tank bot : botsToRemove){
                        level.removeBot(bot);
                    }
                }

                if (level.checkCollision(bullet.getDestinationCoordinates())) {
                    toRemove.add(bullet);
                }


            }

        }
        for (Bullet bullet : toRemove){
            level.removeBullet(bullet);
        }
    }

    @Override
    public void handleCommands() {

    }
}
