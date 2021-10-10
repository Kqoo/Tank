package org.protoss;

public class Main {

    public static void main(String[] args) {
        TankFrame tankFrame = TankFrame.getINSTANCE();

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

        //连接
        Client client = new Client();
        client.connect();
    }
}
