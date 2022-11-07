package ru.mipt.bit.platformer.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.Bullet;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.TileObject;
import ru.mipt.bit.platformer.util.MoveVector;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Shoot implements Command {
    Bullet bullet;
    TileObject objectType;
    Level level;

    public Shoot(Bullet bullet, TileObject objectType, Level level) {
        this.level = level;
        this.bullet = bullet;
        this.objectType = objectType;
    }

    @Override
    public void execute() {
        GridPoint2 bulletDestCoords = bullet.getDestinationCoordinates().cpy().add(bullet.getMoveVector().vector);
        // check potential player destination for collision with obstacles
        if (!level.checkCollision(bulletDestCoords)) {
            bullet.setDestinationCoordinates(bulletDestCoords);
            bullet.setBulletMovementProgress(0f);
            level.addBullet(bullet);
            level.moveObjects(bullet,objectType);
        }
    }
}
