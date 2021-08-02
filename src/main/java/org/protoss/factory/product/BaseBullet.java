package org.protoss.factory.product;

import org.protoss.Tank;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import sun.java2d.loops.FillRect;

import java.awt.*;

public abstract class BaseBullet {
    protected Group group;
    protected Rectangle rect;
    protected boolean living = true;

    public abstract void paint(Graphics g);

    public void collideWidth(BaseTank tank) {
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
