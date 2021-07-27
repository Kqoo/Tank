package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;

import java.awt.*;

@Data
public class Bullet {
    private int x;
    private int y;
    private int speed = 10;
    private Dir dir;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
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
    }
}
