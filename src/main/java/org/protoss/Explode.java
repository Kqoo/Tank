package org.protoss;

import org.protoss.utils.ResourceManager;

import java.awt.*;

public class Explode extends GameObject{
    public static final int WIDTH = ResourceManager.explodes[0].getWidth();
    public static final int HEIGHT = ResourceManager.explodes[0].getHeight();
    
    private boolean living = true;
    private int step;//第几张图

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
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
