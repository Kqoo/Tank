package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.protoss.GameModel;
import org.protoss.Tank;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.strategy.FireStrategy;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TankFireMsg implements Msg {

    private UUID id;
    private int x;
    private int y;
    private Dir dir;
    private Group group;

    public TankFireMsg() {
    }

    @Override
    public void handle(ChannelHandlerContext ctx) {
        if (GameModel.isMainTank(id)) {
            return;
        }
        Tank tank = GameModel.getINSTANCE().findTankById(id);
        if (tank != null) {
            tank.fire();
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_FIRE;
    }

    @Override
    public void parse(byte[] bytes) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            id = new UUID(in.readLong(), in.readLong());
            x = in.readInt();
            y = in.readInt();
            dir = Dir.values()[in.readInt()];
            group = Group.values()[in.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
