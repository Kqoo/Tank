package org.protoss.decorator;

import org.protoss.GameObject;

import java.awt.*;

public abstract class GODecorator extends GameObject {
    protected GameObject gameObject;

    public GODecorator(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void paint(Graphics g) {
        gameObject.paint(g);
    }
}
