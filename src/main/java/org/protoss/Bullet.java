package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;

@Data
public class Bullet extends GameObject{
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();

    private int speed = Integer.parseInt(PropertyManager.get("bulletSpeed"));
    private Dir dir;
    private GameModel gameModel;
    private Tank tank;
    private Group group;
    private boolean living = true;
    private Rectangle rect;

    public Bullet(int x, int y, Dir dir, Group group, GameModel gameModel) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.gameModel = gameModel;
        this.group = group;
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void paint(Graphics g) {
        if (!living) {
           gameModel.remove(this);
        }
        Color color = g.getColor();
        g.setColor(Color.RED);
        Image bulletImg = null;
        switch (dir) {
            case UP:
                y -= 10;
                bulletImg = ResourceManager.bulletU;
                break;
            case DOWN:
                y += 10;
                bulletImg = ResourceManager.bulletD;
                break;
            case LEFT:
                x -= 10;
                bulletImg = ResourceManager.bulletL;
                break;
            case RIGHT:
                x += 10;
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
            living = false;
        }

        rect.x = x;
        rect.y = y;
    }

    public void die() {
        living = false;
    }
}
