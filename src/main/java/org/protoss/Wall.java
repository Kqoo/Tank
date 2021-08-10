package org.protoss;

import java.awt.*;

public class Wall extends GameObject {

    private Rectangle rect;
    private int width;
    private int height;


    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        rect = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.gray);
        g.fillRect(x,y,width,height);
        g.setColor(color);
    }

    public Rectangle getRect() {
        return rect;
    }
}
