package org.protoss;

import javax.net.ssl.SSLEngineResult;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args){
        TankFrame tankFrame = new TankFrame();

        new Thread(()->{
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
