package org.protoss;

import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    private Image offScreenImage;//双缓冲使用的缓冲图片
    private final Tank mainTank = new Tank(200, 600, Dir.UP, Group.we, this);
    private List<Tank> enemies = new ArrayList<>();
    private List<Explode> explodes = new ArrayList<>();

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
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

    //update在paint之前调用
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics offScreenG = offScreenImage.getGraphics();
        Color color = offScreenG.getColor();//保存颜色
        offScreenG.setColor(Color.BLACK);
        offScreenG.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        offScreenG.setColor(color);
        paint(offScreenG);//在内存中画，画完在将整张图片画到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        mainTank.paint(g);
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量:" + mainTank.getBullets().size(), 10, 60);
        g.drawString("敌人数量:" + enemies.size(), 10, 80);
        g.drawString("爆炸数量:" + explodes.size(), 10, 100);
        g.setColor(color);
        List<Bullet> bullets = mainTank.getBullets();
        List<Bullet> enemyBullets = enemies.stream()
                                           .flatMap(e -> e.getBullets().stream())
                                           .collect(Collectors.toList());
        //敌方坦克
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).paint(g);
        }
        //爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //敌方坦克
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g);
        }
        //碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                bullets.get(i).collideWidth(enemies.get(j));
            }
        }

    }

    private class KeyListener extends KeyAdapter {
        private boolean bu;
        private boolean bd;
        private boolean bl;
        private boolean br;

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
                case KeyEvent.VK_SPACE:
                    mainTank.fire();
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            mainTank.setMoving(true);
            if (bu) mainTank.setDir(Dir.UP);
            if (bd) mainTank.setDir(Dir.DOWN);
            if (bl) mainTank.setDir(Dir.LEFT);
            if (br) mainTank.setDir(Dir.RIGHT);
            if (!bu && !bd && !bl && !br) {
                mainTank.setMoving(false);
            }
        }
    }

    public void setEnemies(List<Tank> enemies) {
        this.enemies = enemies;
    }

    public List<Tank> getEnemies() {
        return enemies;
    }

    public List<Explode> getExplodes() {
        return explodes;
    }

}
