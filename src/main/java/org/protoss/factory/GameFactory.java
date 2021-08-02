package org.protoss.factory;

import org.protoss.Tank;
import org.protoss.TankFrame;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.product.BaseBullet;
import org.protoss.factory.product.BaseExplode;
import org.protoss.factory.product.BaseTank;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tankFrame);

    public abstract BaseBullet createBullet(int x, int y, Dir dir, BaseTank tank);

    public abstract BaseExplode createExplode(int ex, int ey, TankFrame tankFrame);
}
