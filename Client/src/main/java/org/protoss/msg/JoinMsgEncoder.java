package org.protoss.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * use {@link MsgEncoder} instead
 */
@Deprecated
public class JoinMsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg joinMsg, ByteBuf out) throws Exception {
        out.writeInt(joinMsg.getMsgType().ordinal());
        byte[] bytes = joinMsg.toBytes();
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
