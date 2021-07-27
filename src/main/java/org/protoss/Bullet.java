package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;

import java.awt.*;

@Data
public class Bullet {
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();

    private int x;
    private int y;
    private int speed = 10;
    private Dir dir;
    private TankFrame tankFrame;
    private Tank tank;
    private boolean live = true;

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir, Tank tank) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tank = tank;
        this.tankFrame = tank.getTankFrame();
    }

    public void paint(Graphics g) {
        if (!live) {
            tank.getBullets().remove(this);
        }
        Color color = g.getColor();
        g.setColor(Color.RED);
        Image bulletImg = null;
        switch (dir) {
            case UP:
                bulletImg = ResourceManager.bulletU;
                break;
            case DOWN:
                bulletImg = ResourceManager.bulletD;
                break;
            case LEFT:
                bulletImg = ResourceManager.bulletL;
                break;
            case RIGHT:
                bulletImg = ResourceManager.bulletR;
                break;
        }
        g.drawImage(bulletImg, x, y, null);
        g.setColor(color);
        move();
    }

    private void move() {
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
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }
}
