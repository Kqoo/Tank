package org.protoss.factory.product;

import lombok.Data;
import org.protoss.Explode;
import org.protoss.TankFrame;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.GameFactory;
import org.protoss.strategy.FireStrategy;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public abstract class BaseTank {
    protected static final int WIDTH = ResourceManager.myTankD.getWidth();
    protected static final int HEIGHT = ResourceManager.myTankD.getHeight();

    protected int x;
    protected int y;
    protected Dir dir;
    protected int speed = Integer.parseInt(PropertyManager.get("tankSpeed"));
    protected boolean moving = false;
    protected boolean living = true;
    protected TankFrame tankFrame;
    protected Group group = Group.enemy;
    protected static Random random = new Random();
    protected Rectangle rect;
    protected FireStrategy fireStrategy;
    protected GameFactory gameFactory;
    protected List<BaseBullet> bullets = new ArrayList<>();


    public abstract void paint(Graphics g);

    public void die() {
        int ex = x + WIDTH / 2 - Explode.WIDTH / 2;
        int ey = y + HEIGHT / 2 - Explode.HEIGHT / 2;
        living = false;
        //爆炸
        tankFrame.getExplodes().add(gameFactory.createExplode(ex, ey, tankFrame));
    }

    public void fire() {
        fireStrategy.fire(this);
    }
}
