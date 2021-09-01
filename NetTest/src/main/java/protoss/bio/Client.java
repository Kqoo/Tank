package protoss.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream outputStream = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        new Thread(() -> {
            //读取
            byte[] bytes = new byte[1024];
            try {
                while (in.read(bytes) != -1) {
                    System.out.println(new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            //发送
            Scanner scanner = new Scanner(System.in);
            final String s = scanner.next();
            try {
                outputStream.write(s.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
