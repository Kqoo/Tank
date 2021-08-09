package org.protoss.strategy;

import org.protoss.Bullet;
import org.protoss.Tank;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        tank.getBullets().add(new Bullet(bx, by, tank.getDir(), tank));
    }
}
