package org.protoss;//package org.protoss.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Group;
import org.protoss.msg.JoinMsg;
import org.protoss.msg.MsgDecoder;
import org.protoss.msg.MsgEncoder;

@Slf4j
public class Server {

    private static final Server server = new Server();

    //线程池
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        server.start();
    }

    private void start() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(2);//2个线程 处理accept事件
        EventLoopGroup workGroup = new NioEventLoopGroup(8);//处理连接后的事件

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture future = serverBootstrap
                    .group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    private class ServerChildHandler extends ChannelHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            channelGroup.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("channelRead 读取消息:{}", msg);
            try {
                if (msg instanceof JoinMsg) {
                    JoinMsg joinMsg = (JoinMsg) msg;
                    //设置group为 敌人
                    joinMsg.setGroup(Group.enemy);
                    log.info("tankJoinMsg:{}",joinMsg);
                    log.info("新玩家连接UUID:{},当前玩家数量:{}", joinMsg.getId(), channelGroup.size());
                }
                //像所有客户端发送新玩家连接的消息
                channelGroup.writeAndFlush(msg);
            }
            finally {
                ReferenceCountUtil.release(msg);
            }

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            channelGroup.remove(ctx.channel());
            log.info("用户异常退出,剩余玩家数量:{}", channelGroup.size());
            ctx.close();
        }
    }

    private Server() {
    }
}
