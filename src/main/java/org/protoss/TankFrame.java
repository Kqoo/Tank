package org.protoss;

import org.protoss.constant.Dir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TankFrame extends JFrame {

    private int x;
    private int y;
    private int xSpeed = 10;
    private int ySpeed = 10;
    private boolean bu;
    private boolean bd;
    private boolean bl;
    private boolean br;
    private Dir dir = null;

    public TankFrame() {
        setSize(800, 600);
        setResizable(false);
        setTitle("坦克大战");
        setVisible(true);

        //按键监听
        addKeyListener(new KeyListener());
        //关闭监听
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (dir != null) {
            switch (dir) {
                case UP:
                    y -= ySpeed;
                    break;
                case DOWN:
                    y += ySpeed;
                    break;
                case LEFT:
                    x -= xSpeed;
                    break;
                case RIGHT:
                    x += xSpeed;
                    break;
            }
        }
        g.fillRect(x, y, 50, 50);
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (bu) dir = Dir.UP;
            if (bd) dir = Dir.DOWN;
            if (bl) dir = Dir.LEFT;
            if (br) dir = Dir.RIGHT;
        }
    }
}
