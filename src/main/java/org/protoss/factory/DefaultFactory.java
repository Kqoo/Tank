package org.protoss.factory;

import org.protoss.Bullet;
import org.protoss.Explode;
import org.protoss.Tank;
import org.protoss.TankFrame;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.product.BaseBullet;
import org.protoss.factory.product.BaseExplode;
import org.protoss.factory.product.BaseTank;

public class DefaultFactory extends GameFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        return new Tank(x, y, Dir.UP, group, tankFrame);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, BaseTank tank) {
        return new Bullet(x, y, dir, tank);
    }

    @Override
    public BaseExplode createExplode(int ex, int ey, TankFrame tankFrame) {
        return new Explode(ex, ey, tankFrame);
    }
}
