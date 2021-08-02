package org.protoss.strategy;

import org.protoss.Bullet;
import org.protoss.Tank;
import org.protoss.TankFrame;
import org.protoss.factory.product.BaseTank;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(BaseTank tank) {
        int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        //new Bullet(bx, by, tank.getDir(), tank)
        tank.getBullets().add(TankFrame.gameFactory.createBullet(bx, by, tank.getDir(), tank));
    }
}
