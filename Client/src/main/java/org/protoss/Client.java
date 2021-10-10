package org.protoss;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.protoss.msg.JoinMsg;
import org.protoss.msg.JoinMsgDecoder;
import org.protoss.msg.JoinMsgEncoder;

@Slf4j
public class Client {

    public static void main(String[] args) {
        new Client().connect();
    }

    private Channel channel;

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture future = bootstrap.group(group)
                                            .channel(NioSocketChannel.class)
                                            .handler(new ClientChannelInitializer())
                                            .connect()
                                            .sync();
            future.addListener((ChannelFutureListener) f -> {
                if (!f.isSuccess()) {
                    log.error("连接失败!");
                } else {
                    log.info("连接成功!");
                    channel = f.channel();
                }
            });
            future.channel()
                  .closeFuture()
                  .sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg){
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(byteBuf);
    }

    private static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
              .addLast(new JoinMsgDecoder())
              .addLast(new JoinMsgEncoder())
              .addLast(new ClientHandler());

        }
    }

    private static class ClientHandler extends SimpleChannelInboundHandler<JoinMsg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new JoinMsg(TankFrame.getINSTANCE().getMainTank()));
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, JoinMsg msg) throws Exception {
            log.info("新坦克加入:{}", msg);
        }
    }
}
