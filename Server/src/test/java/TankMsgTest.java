
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.msg.JoinMsg;
import org.protoss.msg.JoinMsgDecoder;
import org.protoss.msg.JoinMsgEncoder;

import java.util.UUID;

public class TankMsgTest {

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline()
               .addLast(new JoinMsgDecoder());

        JoinMsg joinMsg = JoinMsg.builder()
                                 .id(UUID.randomUUID())
                                 .x(1)
                                 .y(2)
                                 .group(Group.enemy)
                                 .dir(Dir.DOWN)
                                 .moving(true)
                                 .build();
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(joinMsg.toBytes());

        channel.writeInbound(buf.duplicate());

        JoinMsg msgR = channel.readInbound();
        System.out.println(msgR);
    }

    @Test
    public void testEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline()
               .addLast(new JoinMsgEncoder());

        JoinMsg joinMsg = JoinMsg.builder()
                               .id(UUID.randomUUID())
                               .x(1)
                               .y(2)
                               .group(Group.enemy)
                               .dir(Dir.DOWN)
                               .moving(true)
                               .build();
        channel.writeOutbound(joinMsg);
        ByteBuf byteBuf = channel.readOutbound();
        JoinMsg readMsg = JoinMsg.builder()
                                 .x(byteBuf.readInt())
                                 .y(byteBuf.readInt())
                                 .dir(Dir.values()[byteBuf.readInt()])
                                 .group(Group.values()[byteBuf.readInt()])
                                 .moving(byteBuf.readBoolean())
                                 .id(new UUID(byteBuf.readLong(), byteBuf.readLong()))
                                 .build();
        System.out.println(readMsg);
        assert joinMsg.equals(readMsg);
    }
}
