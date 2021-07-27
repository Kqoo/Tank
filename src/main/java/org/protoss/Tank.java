package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tank {
    public static final int WIDTH = ResourceManager.tankD.getWidth();
    public static final int HEIGHT = ResourceManager.tankD.getHeight();

    private int x;
    private int y;
    private Dir dir;
    private int speed = 10;
    private boolean moving = false;
    private boolean living = true;
    private TankFrame tankFrame;
    private Group group = Group.enemy;

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
        switch (dir) {
            case UP:
                tankImg = ResourceManager.tankU;
                break;
            case DOWN:
                tankImg = ResourceManager.tankD;
                break;
            case LEFT:
                tankImg = ResourceManager.tankL;
                break;
            case RIGHT:
                tankImg = ResourceManager.tankR;
                break;
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
    }

    public void fire() {
        int bx = x + WIDTH / 2 - Bullet.WIDTH / 2;
        int by = y + HEIGHT / 2 - Bullet.HEIGHT / 2;

        bullets.add(new Bullet(bx, by, dir, this));
    }

    public void die() {
        living = false;
    }
}
