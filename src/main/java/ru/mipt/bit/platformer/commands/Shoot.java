package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.Bullet;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.TileObject;
import ru.mipt.bit.platformer.util.MoveVector;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;

/*
 * Adapter
 */
public class Shoot implements Command {
    Bullet bullet;
    final TileObject objectType = TileObject.BULLET;
    Level level;
    Tank tank;

    public Shoot(Bullet bullet, Level level, Tank tank) {
        this.level = level;
        this.bullet = bullet;
        this.tank = tank;
    }

    @Override
    public void execute() {
        //TODO TWO BULLETS IN ONE CELL
        GridPoint2 bulletDestCoords = bullet.getDestinationCoordinates().cpy();
        // check potential player destination for collision with obstacles
        HashSet<TileObject> tileObjects = level.checkCollisionBullet(bulletDestCoords);
        if (tileObjects.isEmpty()) {
            bullet.setDestinationCoordinates(bulletDestCoords);
            bullet.setBulletMovementProgress(0f);
            level.addBullet(bullet);
            level.moveObjects(bullet,objectType);
        }

        if (tileObjects.contains(TileObject.PLAYER)){
            Tank player = level.getPlayer();
            if (this.tank != player) {
                player.updateHealth(player.getHealth() - 1);
                System.out.println(player.getHealth());
            }
            else{
                bullet.setDestinationCoordinates(bulletDestCoords);
                bullet.setBulletMovementProgress(0f);
                level.addBullet(bullet);
                level.moveObjects(bullet,objectType);
            }

        }
        else if (tileObjects.contains(TileObject.BOT)){
            ArrayList<Tank> bots = level.getBots();
            ArrayList<Tank> botsToRemove = new ArrayList<>();
            for (Tank bot : bots){
                if (bot.getCurrentCoordinates().equals(bulletDestCoords)) {
                    if (this.tank != bot) {
                        bot.updateHealth(bot.getHealth() - 1);
                        if (bot.getHealth() == 0)
                            botsToRemove.add(bot);
                    }
                    else{
                        bullet.setDestinationCoordinates(bulletDestCoords);
                        bullet.setBulletMovementProgress(0f);
                        level.addBullet(bullet);
                        level.moveObjects(bullet,objectType);
                    }
                }
            }
            for (Tank bot : botsToRemove){
                level.removeBot(bot);
            }
        }
    }
}
