package org.protoss.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MsgDecoder extends ByteToMessageDecoder {
    private static final String packName = "org.protoss.msg.";

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
        if (in.readableBytes() < length) {
            in.resetReaderIndex();//读指针回到上次记录的位置
            return;
        }
        //读取消息体
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        String msgTypeName = lineToHump(msgType + "_MSG", true);
        Msg msg = (Msg) Class.forName(packName + msgTypeName).newInstance();
        if (msg != null) {
            msg.parse(bytes);
            out.add(msg);
        }
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    private String lineToHump(String str, boolean isInitialUpper) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        //首字母大写
        if (isInitialUpper) {
            sb.replace(0, 1, sb.substring(0, 1).toUpperCase());
        }
        return sb.toString();
    }
}
