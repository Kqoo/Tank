package org.protoss.strategy;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import jdk.nashorn.internal.ir.CallNode;
import lombok.extern.slf4j.Slf4j;
import org.protoss.Bullet;
import org.protoss.Tank;
import org.protoss.factory.DefaultFactory;
import org.protoss.factory.GameFactory;
import org.protoss.factory.product.BaseBullet;
import org.protoss.factory.product.BaseTank;
import org.protoss.utils.ThreadUtil;

import java.util.concurrent.*;

@Slf4j
public class TripleFireStrategy implements FireStrategy {

    private GameFactory gameFactory = new DefaultFactory();

    @Override
    public void fire(BaseTank tank) {
        ExecutorService singleThreadPool = ThreadUtil.getSingleThreadPool();

        Future<?> submit = singleThreadPool.submit(() -> {
            for (int i = 0; i < 3; i++) {
                int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
                int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
//                Bullet bullet = new Bullet(bx, by, tank.getDir(), tank);
                BaseBullet bullet = gameFactory.createBullet(bx, by, tank.getDir(), tank);
                tank.getBullets().add(bullet);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
