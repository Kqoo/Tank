package org.protoss;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.DefaultFactory;
import org.protoss.factory.GameFactory;
import org.protoss.factory.product.BaseBullet;
import org.protoss.factory.product.BaseExplode;
import org.protoss.factory.product.BaseTank;
import org.protoss.strategy.DefaultFireStrategy;
import org.protoss.strategy.FireStrategy;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Tank extends BaseTank {
    public static final int WIDTH = ResourceManager.myTankD.getWidth();
    public static final int HEIGHT = ResourceManager.myTankD.getHeight();

//    private int x;
//    private int y;
//    private Dir dir;
//    private int speed = Integer.parseInt(PropertyManager.get("tankSpeed"));
//    private boolean moving = false;
//    private boolean living = true;
//    private TankFrame tankFrame;
//    private Group group = Group.enemy;
//    private static Random random = new Random();
//    private Rectangle rect;
//    private FireStrategy fireStrategy;
//    private GameFactory gameFactory;
//
//    private List<BaseBullet> bullets = new ArrayList<>();

    public Tank() {
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
        rect = new Rectangle(x, y, Tank.WIDTH, Tank.HEIGHT);
        gameFactory = new DefaultFactory();
        try {
            if (group == Group.we) {
                fireStrategy = (FireStrategy) Class.forName(PropertyManager.get("weFireStrategy")).newInstance();
            } else {
                fireStrategy = (FireStrategy) Class.forName(PropertyManager.get("enemyFireStrategy")).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("开火策略异常：", e);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            tankFrame.getEnemies().remove(this);
            return;
        }
        move();
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        Image tankImg = null;
        if (group == Group.we) {
            switch (dir) {
                case UP:
                    tankImg = ResourceManager.myTankU;
                    break;
                case DOWN:
                    tankImg = ResourceManager.myTankD;
                    break;
                case LEFT:
                    tankImg = ResourceManager.myTankL;
                    break;
                case RIGHT:
                    tankImg = ResourceManager.myTankR;
                    break;
            }
        } else {
            switch (dir) {
                case UP:
                    tankImg = ResourceManager.enemyTankU;
                    break;
                case DOWN:
                    tankImg = ResourceManager.enemyTankD;
                    break;
                case LEFT:
                    tankImg = ResourceManager.enemyTankL;
                    break;
                case RIGHT:
                    tankImg = ResourceManager.enemyTankR;
                    break;
            }
        }
        g.drawImage(tankImg, x, y, null);
        g.setColor(color);
    }

    private void move() {
        if (!living) {
            return;
        }
        if (moving) {
            switch (dir) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
        }
        if (group == Group.enemy && Math.random() > 0.88) {
            fire();
        }
        if (group == Group.enemy && Math.random() > 0.88) {
            randomDir();
        }
        boundCheck();
        //边侧检测后更新rect坐标
        rect.x = x;
        rect.y = y;
    }

    /**
     * 边界检测
     */
    private void boundCheck() {
        if (x < 0) {
            x = 0;
        } else if (x > TankFrame.GAME_WIDTH - WIDTH) {
            x = TankFrame.GAME_WIDTH - WIDTH;
        }
        if (y < 30) {
            y = 30;
        } else if (y > TankFrame.GAME_HEIGHT - HEIGHT) {
            y = TankFrame.GAME_HEIGHT - HEIGHT;
        }
    }

    private void randomDir() {
        dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fireStrategy.fire(this);

//        int bx = x + WIDTH / 2 - Bullet.WIDTH / 2;
//        int by = y + HEIGHT / 2 - Bullet.HEIGHT / 2;
//
//        bullets.add(new Bullet(bx, by, dir, this));
    }

    public void die() {
        int ex = x + WIDTH / 2 - Explode.WIDTH / 2;
        int ey = y + HEIGHT / 2 - Explode.HEIGHT / 2;
        living = false;
        //爆炸
        tankFrame.getExplodes().add(gameFactory.createExplode(ex, ey, tankFrame));
    }

}
