package org.protoss.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8888);

        new Thread(() ->
        {
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    OutputStream outputStream = socket.getOutputStream();
                    String s = scanner.next();
                    outputStream.write(s.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //读取
        try {
            InputStream in = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
