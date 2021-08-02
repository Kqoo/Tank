package org.protoss;

import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.factory.product.BaseTank;
import org.protoss.utils.PropertyManager;

import javax.net.ssl.SSLEngineResult;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        int initEnemyCount = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("initEnemyCount")));

        TankFrame tankFrame = new TankFrame();
        //敌方坦克
        for (int i = 0; i < initEnemyCount; i++) {
//            Tank tank = new Tank(100 * (i + 1), 200, Dir.DOWN, Group.enemy, tankFrame);
            BaseTank tank = TankFrame.gameFactory.createTank(100 * (i + 1), 200, Dir.DOWN, Group.enemy, tankFrame);
            tank.setMoving(true);
            tankFrame.getEnemies().add(tank);
        }


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                    tankFrame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
