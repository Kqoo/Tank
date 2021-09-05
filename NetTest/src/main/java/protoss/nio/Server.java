package protoss.nio;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
        socketChannel.configureBlocking(false);//阻塞设为false

        log.info("服务器启动,监听：{}", socketChannel.getLocalAddress());

        //使用Selector管理客户端
        Selector selector = Selector.open();
        //设置这个selector专门处理accept事件
        //一个Selector可以管理多个socketChannel，只需将Selector注册到channel上就行
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();//阻塞方法，当有客户端连接时会返回
            Set<SelectionKey> selectionKeys = selector.selectedKeys();//取出事件的key
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handle(key);
            }

        }
    }

    private static void handle(SelectionKey key) {
        if (key.isAcceptable()) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                log.info("连接 ssc:{}", ssc.getLocalAddress());
                log.info("连接 sc:{}", sc);

                //为新加入的客户端注册事件
                sc.register(key.selector(), SelectionKey.OP_READ);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (key.isReadable()) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.clear();
                //读取
                int len;
                ByteOutputStream byteOutputStream = new ByteOutputStream();
                while (sc.read(byteBuffer) != 0) {
                    byteOutputStream.write(byteBuffer.array());
                }
                byte[] bytes = byteOutputStream.getBytes();
                String res = new String(bytes);
                ByteBuffer writeBuffer = ByteBuffer.wrap(("收到:" + res).getBytes());
                sc.write(writeBuffer);
                sc.write(ByteBuffer.wrap(("收到字节长度:" + bytes.length+"\n").getBytes()));
//                sc.register(key.selector(), SelectionKey.OP_WRITE);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sc.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (key.isWritable()) {
            try {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer writeBuffer = ByteBuffer.wrap("Hello World!".getBytes());
                sc.write(writeBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
