package org.protoss;

import org.protoss.constant.Dir;

import javax.net.ssl.SSLEngineResult;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();
        //敌方坦克
        for (int i = 0; i < 5; i++) {
            tankFrame.getEnemies().add(new Tank(100 * (i + 1), 200, Dir.DOWN, tankFrame));
        }


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    tankFrame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
