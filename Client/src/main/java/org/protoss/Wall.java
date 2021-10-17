package org.protoss;

import java.awt.*;

public class Wall extends GameObject {

    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        rect = new Rectangle(x, y, w+20, h+20);
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
