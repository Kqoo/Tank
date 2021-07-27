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
    private TankFrame tankFrame;
    private Image tankImg = null;
    private List<Bullet> bullets = new ArrayList<>();

    public Tank() {
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
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
        bullets.add(new Bullet(x, y, dir, this));
    }
}
