package org.protoss.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * use {@link MsgDecoder} instead
 */
@Deprecated
public class JoinMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //消息头 msgType,length 8个字节
        if (in.readableBytes() < 8) {
            return;
        }
        //记录读取位置
        in.markReaderIndex();
        //读取消息头
        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();
        //消息体长度小于数据长度返回等下个包
        if (in.readableBytes() < length || msgType != MsgType.JOIN) {
            in.resetReaderIndex();//读指针回到上次记录的位置
            return;
        }
        //读取消息体
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        JoinMsg msg = new JoinMsg();
        msg.parse(bytes);
        out.add(msg);
    }
}
