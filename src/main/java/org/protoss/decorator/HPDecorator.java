package org.protoss.decorator;

import org.protoss.GameObject;

import java.awt.*;

public class HPDecorator extends GODecorator {

    public HPDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color color = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(gameObject.getX(), gameObject.getY() - 20, gameObject.getWidth(), 10);
        g.setColor(color);
    }
}
