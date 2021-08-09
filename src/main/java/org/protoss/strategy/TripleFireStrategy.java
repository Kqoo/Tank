package org.protoss.strategy;

import lombok.extern.slf4j.Slf4j;
import org.protoss.Bullet;
import org.protoss.Tank;
import org.protoss.utils.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public class TripleFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        ExecutorService singleThreadPool = ThreadUtil.getSingleThreadPool();

        Future<?> submit = singleThreadPool.submit(() -> {
            for (int i = 0; i < 3; i++) {
                int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
                int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
                Bullet bullet = new Bullet(bx, by, tank.getDir(), tank.getGroup(), tank.getGameModel());
                tank.getGameModel().add(bullet);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
