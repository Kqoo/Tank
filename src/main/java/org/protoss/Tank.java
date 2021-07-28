package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Tank {
    public static final int WIDTH = ResourceManager.myTankD.getWidth();
    public static final int HEIGHT = ResourceManager.myTankD.getHeight();

    private int x;
    private int y;
    private Dir dir;
    private int speed = 10;
    private boolean moving = false;
    private boolean living = true;
    private TankFrame tankFrame;
    private Group group = Group.enemy;
    private static Random random = new Random();

    private List<Bullet> bullets = new ArrayList<>();

    public Tank() {
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }


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
    }

    /**
     * 边界检测
     */
    private void boundCheck() {
        if (x < 0) {
            x = 0;
        } else if (x > TankFrame.GAME_WIDTH - WIDTH) {
            x = TankFrame.GAME_WIDTH -WIDTH;
        }
        if (y < 30) {
            y = 30;
        } else if (y > TankFrame.GAME_HEIGHT -HEIGHT) {
            y = TankFrame.GAME_HEIGHT -HEIGHT;
        }
    }

    private void randomDir() {
        dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bx = x + WIDTH / 2 - Bullet.WIDTH / 2;
        int by = y + HEIGHT / 2 - Bullet.HEIGHT / 2;

        bullets.add(new Bullet(bx, by, dir, this));
    }

    public void die() {
        int ex = x + WIDTH / 2 - Explode.WIDTH / 2;
        int ey = y + HEIGHT / 2 - Explode.HEIGHT / 2;
        living = false;
        //爆炸
        tankFrame.getExplodes().add(new Explode(ex, ey, tankFrame));
    }
}
