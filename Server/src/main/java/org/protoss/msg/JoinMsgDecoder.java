package org.protoss.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.util.List;
import java.util.UUID;

public class JoinMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 33) {
            return;
        }
        JoinMsg joinMsg = JoinMsg.builder()
                                 .x(in.readInt())
                                 .y(in.readInt())
                                 .dir(Dir.values()[in.readInt()])
                                 .group(Group.values()[in.readInt()])
                                 .moving(in.readBoolean())
                                 .uuid(new UUID(in.readLong(), in.readLong()))
                                 .build();
        out.add(joinMsg);
    }
}
