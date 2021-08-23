package org.protoss;

import org.protoss.utils.ResourceManager;

import java.awt.*;

public class Explode extends GameObject {

    private boolean living = true;
    private int step;//第几张图

    public Explode() {
        width = ResourceManager.explodes[0].getWidth();
        height = ResourceManager.explodes[0].getHeight();
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        width = ResourceManager.explodes[0].getWidth();
        height = ResourceManager.explodes[0].getHeight();
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceManager.explodes[step++], x, y, null);
        if (step >= ResourceManager.explodes.length) {
            step = 0;
            living = false;
            GameModel.getINSTANCE().remove(this);
        }
    }
}
