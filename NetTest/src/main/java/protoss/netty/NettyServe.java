package protoss.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServe {

    //通道组
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static void main(String[] args) {
        EventLoopGroup boosGroup = new NioEventLoopGroup(2);//处理accept事件
        EventLoopGroup workGroup = new NioEventLoopGroup(10);//处理连接后的事件
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap
                    .group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    //.handler()处理server的相关事件
                    .childHandler(new ChannelInitializer<SocketChannel>() { //处理客户端的事件
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.info("客户端：{}", ch);
                            log.info("initChannel Thread id:{},name:{}", Thread.currentThread().getId(), Thread.currentThread().getName());
                            //管道 注释里有图
                            // ->outHandler->outHandler....
                            //<-Handler<-inHandler....
                            ChannelPipeline pipeline = ch.pipeline();
                            //加到管道最后
                            pipeline.addLast(new ChannelHandlerAdapter() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("channelActive Thread id:{},name:{}", Thread.currentThread().getId(), Thread.currentThread().getName());
                                    channelGroup.add(ctx.channel());
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
                                        channelGroup.writeAndFlush(msg);
                                    } finally {
//                                        if (byteBuf != null) {
//                                            ReferenceCountUtil.release(byteBuf);
//                                            log.info("释放byteBuf->{}", byteBuf.refCnt());
//                                        }
                                    }

                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    log.error("通道发生异常", cause);
                                    ctx.channel().closeFuture();
                                }
                            });
                        }
                    })
                    .bind(8888)
                    .sync();
            log.info("server started");
            ChannelFuture closeFuture = channelFuture.channel().closeFuture().sync();
            log.info("close:{}", closeFuture);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
