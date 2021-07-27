package org.protoss;

import java.awt.*;

public class Explode {
    public static final int WIDTH = ResourceManager.explodes[0].getWidth();
    public static final int HEIGHT = ResourceManager.explodes[0].getHeight();

    private int x;
    private int y;
    private TankFrame tankFrame;
    private boolean living = true;
    private int step;//第几张图

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceManager.explodes[step++], x, y, null);
        if (step >= ResourceManager.explodes.length) {
            step = 0;
            living = false;
        }
    }
}
