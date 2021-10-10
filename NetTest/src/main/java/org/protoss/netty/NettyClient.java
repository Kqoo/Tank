package org.protoss.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * netty所有方法都是异步的,可以调用sync来阻塞
 */
@Slf4j
public class NettyClient {

    private static EventLoopGroup group = new NioEventLoopGroup(10);//线程池

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture future = bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)//可以切换模式 如bio->ServerSocket.class
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.info("SocketChannel:{}", ch);
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ChannelHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //第一次连上可用
                                    //ByteBuf是direct memory(直接访问操作系统的内存,不需要从操作系统拷贝到jvm里)
                                    ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World!".getBytes());
                                    ctx.writeAndFlush(byteBuf);//所以不能依靠GC释放,要手动释放内存,writeAndFlush调用完后会释放内存

                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = null;
                                    try {
                                        byteBuf = (ByteBuf) msg;
                                        log.info("byteBuf:{}", byteBuf);
                                        log.info("byteBuf的引用数量:{}", byteBuf.refCnt());
                                        //读取数据
                                        byte[] bytes = new byte[byteBuf.readableBytes()];
                                        byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                                        log.info("读取数据:{}", new String(bytes));
                                    } finally {
                                        if (byteBuf != null) {
                                            ReferenceCountUtil.release(byteBuf);
                                            log.info("释放byteBuf->{}", byteBuf.refCnt());
                                        }
                                    }

                                }
                            });
                        }
                    })
                    //connect后调用handler里的初始化方法
                    .connect("127.0.0.1", 8888)
                    .sync();

            future.addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    System.out.println("connect success");
                } else {
                    System.out.println("connect fail");
                }
            });

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
