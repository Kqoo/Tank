package org.protoss;

import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Dir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@Slf4j
public class TankFrame extends JFrame {

    private Tank mainTank = new Tank(200, 200, Dir.DOWN);

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
        mainTank.paint(g);

    }

    private class KeyListener extends KeyAdapter {
        private boolean bu;
        private boolean bd;
        private boolean bl;
        private boolean br;

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            log.info("pressed:{}", e.getKeyCode());
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
            if (bu) mainTank.setDir(Dir.UP);
            if (bd) mainTank.setDir(Dir.DOWN);
            if (bl) mainTank.setDir(Dir.LEFT);
            if (br) mainTank.setDir(Dir.RIGHT);
        }
    }
}
