package org.protoss;

import lombok.Data;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.product.BaseBullet;
import org.protoss.factory.product.BaseTank;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;

@Data
public class Bullet extends BaseBullet {
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();

    private int x;
    private int y;
    private int speed = Integer.parseInt(PropertyManager.get("bulletSpeed"));
    private Dir dir;
    private TankFrame tankFrame;
    private BaseTank tank;

//    private Group group;
//    private boolean living = true;
//    private Rectangle rect;

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir, BaseTank tank) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tank = tank;
        this.tankFrame = tank.getTankFrame();
        this.group = tank.getGroup();
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            tank.getBullets().remove(this);
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

    public void collideWidth(Tank tank) {
        if (group == tank.getGroup()) {
            return;
        }

        //相交
        if (rect.intersects(tank.getRect())) {
            tank.die();
            this.die();
        }

    }

    private void die() {
        living = false;
    }
}
