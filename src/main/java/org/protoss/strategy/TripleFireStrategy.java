package org.protoss.strategy;

import lombok.extern.slf4j.Slf4j;
import org.protoss.Bullet;
import org.protoss.GameModel;
import org.protoss.Tank;
import org.protoss.utils.ResourceManager;
import org.protoss.utils.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public class TripleFireStrategy implements FireStrategy {
    private static final int WIDTH = ResourceManager.bulletD.getWidth();
    private static final int HEIGHT = ResourceManager.bulletD.getHeight();
    @Override
    public void fire(Tank tank) {
        ExecutorService singleThreadPool = ThreadUtil.getSingleThreadPool();

        Future<?> submit = singleThreadPool.submit(() -> {
            for (int i = 0; i < 3; i++) {
                int bx = tank.getX() + tank.getWidth() / 2 - WIDTH / 2;
                int by = tank.getY() + tank.getHeight() / 2 - HEIGHT / 2;
                Bullet bullet = new Bullet(bx, by, tank.getDir(), tank.getGroup());
                GameModel.getINSTANCE().add(bullet);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
