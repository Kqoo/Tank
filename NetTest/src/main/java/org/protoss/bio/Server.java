package org.protoss.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        server.bind(new InetSocketAddress("127.0.0.1", 8888));
        while (true) {
            Socket socket = server.accept();
            poolExecutor.submit(() -> {
                try {
                    handle(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void handle(Socket socket) throws Exception {
        while (true) {
            InputStream in = socket.getInputStream();
            //读取
            byte[] bytes = new byte[1024];
            in.read(bytes);
            System.out.println(new String(bytes));

            OutputStream os = socket.getOutputStream();
            //发送
            os.write("received！".getBytes());
            os.flush();
            System.out.println("发送！");
        }
    }

    private void receive(Socket socket) throws Exception {
        InputStream in = socket.getInputStream();
        //读取
        String s;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }

    private void send(Socket socket) throws Exception {
        OutputStream os = socket.getOutputStream();
        //发送
        os.write("received！".getBytes());
        os.flush();
        System.out.println("发送！");
    }
}
