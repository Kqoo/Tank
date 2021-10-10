package org.protoss.strategy;

import org.protoss.Bullet;
import org.protoss.GameModel;
import org.protoss.Tank;
import org.protoss.utils.ResourceManager;

public class DefaultFireStrategy implements FireStrategy {
    private static final int WIDTH = ResourceManager.bulletD.getWidth();
    private static final int HEIGHT = ResourceManager.bulletD.getHeight();

    @Override
    public void fire(Tank tank) {
        int bx = tank.getX() + tank.getWidth() / 2 - WIDTH / 2;
        int by = tank.getY() + tank.getHeight() / 2 - HEIGHT / 2;

        GameModel.getINSTANCE().add(new Bullet(bx, by, tank.getDir(), tank.getGroup()));
    }
}
