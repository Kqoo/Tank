package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tank {
    private int x;
    private int y;
    private Dir dir;
    private int speed = 10;
    private boolean moving = false;
    private List<Bullet> bullets = new ArrayList<>();

    public Tank() {
    }

    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Tank(int x, int y, Dir dir, int speed) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
    }


    public void paint(Graphics g) {
        move();
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 50, 50);
        g.setColor(color);
    }

    private void move() {
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
    }

    public void fire() {

    }
}
