package org.protoss;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.strategy.FireStrategy;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

@Data
@Slf4j
public class Tank extends GameObject {
//    public static final int WIDTH = ResourceManager.myTankD.getWidth();
//    public static final int HEIGHT = ResourceManager.myTankD.getHeight();

    private int prevX;
    private int prevY;
    private Dir dir;
    private int speed = Integer.parseInt(PropertyManager.get("tankSpeed"));
    private boolean moving = false;
    private boolean living = true;
    private Group group;
    private static Random random = new Random();
    private Rectangle rect;
    private FireStrategy fireStrategy;
    private UUID uuid = UUID.randomUUID();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        width = ResourceManager.myTankD.getWidth();
        height = ResourceManager.myTankD.getHeight();
        this.dir = dir;
        this.group = group;
        rect = new Rectangle(x, y, width, height);
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


    public void paint(Graphics g) {
        if (!living) {
            GameModel.getINSTANCE().remove(this);
            return;
        }
        move();
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
    }

    private void move() {
        if (!living) {
            return;
        }
        //记录上一次位置
        prevX = x;
        prevY = y;
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
        } else if (x > TankFrame.GAME_WIDTH - width) {
            x = TankFrame.GAME_WIDTH - width;
        }
        if (y < 30) {
            y = 30;
        } else if (y > TankFrame.GAME_HEIGHT - height) {
            y = TankFrame.GAME_HEIGHT - height;
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
        Explode explode = new Explode();
        int ex = x + width / 2 - explode.width / 2;
        int ey = y + height / 2 - explode.height / 2;
        explode.setX(ex);
        explode.setY(ey);

        living = false;
        //爆炸
        GameModel.getINSTANCE().add(explode);
    }

}
