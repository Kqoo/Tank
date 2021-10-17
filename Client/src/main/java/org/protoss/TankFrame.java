package org.protoss;

import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Constant;
import org.protoss.constant.Dir;
import org.protoss.msg.TankFireMsg;
import org.protoss.msg.TankMoveMsg;
import org.protoss.strategy.DefaultFireStrategy;
import org.protoss.strategy.TripleFireStrategy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Slf4j
public class TankFrame extends Frame {

    private static TankFrame INSTANCE = new TankFrame();

    private Image offScreenImage;//双缓冲使用的缓冲图片
    private Tank mainTank;

    private TankFrame() {
        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        setResizable(false);
        setTitle("坦克大战");
        setVisible(true);
        mainTank = GameModel.getINSTANCE().getMainTank();
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
            offScreenImage = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        }
        Graphics offScreenG = offScreenImage.getGraphics();
        Color color = offScreenG.getColor();//保存颜色
        offScreenG.setColor(Color.BLACK);
        offScreenG.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        offScreenG.setColor(color);
        paint(offScreenG);//在内存中画，画完在将整张图片画到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        GameModel.getINSTANCE().paint(g);
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
                    Client.getINSTANCE().send(TankFireMsg.builder()
                                                         .id(mainTank.getId())
                                                         .x(mainTank.getX())
                                                         .y(mainTank.getY())
                                                         .dir(mainTank.getDir())
                                                         .group(mainTank.getGroup())
                                                         .build());
                    break;
                case KeyEvent.VK_F1:
                    if (mainTank.getFireStrategy() instanceof DefaultFireStrategy) {
                        mainTank.setFireStrategy(new TripleFireStrategy());
                    } else {
                        mainTank.setFireStrategy(new DefaultFireStrategy());
                    }
                    break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            if (bu) mainTank.setDir(Dir.UP);
            if (bd) mainTank.setDir(Dir.DOWN);
            if (bl) mainTank.setDir(Dir.LEFT);
            if (br) mainTank.setDir(Dir.RIGHT);
            if (!bu && !bd && !bl && !br) {
                mainTank.setMoving(false);
            } else {
                mainTank.setMoving(true);
            }
            Client.getINSTANCE().send(new TankMoveMsg(mainTank.getId(), mainTank.getX(),
                    mainTank.getY(), mainTank.getDir(), mainTank.isMoving()));

        }
    }

    public static TankFrame getINSTANCE() {
        return INSTANCE;
    }

    public Tank getMainTank() {
        return mainTank;
    }
}
