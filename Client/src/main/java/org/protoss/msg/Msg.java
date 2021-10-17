package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;

public interface Msg {
    void handle(ChannelHandlerContext ctx);

    byte[] toBytes();

    MsgType getMsgType();

    void parse(byte[] bytes);

}
