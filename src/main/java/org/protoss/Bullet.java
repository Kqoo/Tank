package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;

import java.awt.*;

@Data
public class Bullet {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

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
        g.fillOval(x, y, WIDTH, HEIGHT);
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
