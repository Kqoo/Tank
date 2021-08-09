package org.protoss;

public class Main {

    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();

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
