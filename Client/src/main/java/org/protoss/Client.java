package org.protoss;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Constant;
import org.protoss.msg.*;

import java.util.Objects;

@Slf4j
public class Client {

    private Channel channel;

    private static final Client INSTANCE = new Client();

    private Client() {

    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture future = bootstrap.group(group)
                                            .channel(NioSocketChannel.class)
                                            .handler(new ClientChannelInitializer())
                                            .connect(Objects.requireNonNull(Constant.SERVER_HOST), Constant.SERVER_PORT)
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

    public void send(Msg msg) {
        log.info("发送消息:{}", msg);
        channel.writeAndFlush(msg);
    }

    public static Client getINSTANCE() {
        return INSTANCE;
    }

    private static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
              .addLast(new MsgDecoder())
              .addLast(new MsgEncoder())
              .addLast(new ClientHandler());

        }
    }

    private static class ClientHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new JoinMsg(TankFrame.getINSTANCE().getMainTank()));
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, Msg msg) throws Exception {
            log.info("接收到消息:{}", msg);
            msg.handle(ctx);
        }
    }
}
