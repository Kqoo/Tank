package org.protoss.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JoinMsgEncoder extends MessageToByteEncoder<JoinMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, JoinMsg joinMsg, ByteBuf out) throws Exception {
        out.writeBytes(joinMsg.toBytes());
    }
}
