package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.protoss.GameModel;
import org.protoss.Tank;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Slf4j
public class TankDieMsg implements Msg {

    private UUID id;
    private int x;
    private int y;

    public TankDieMsg() {
    }

    @Override
    public void handle(ChannelHandlerContext ctx) {
        Tank tank = GameModel.getINSTANCE().findTankById(id);
        if (tank != null) {
            tank.die(x, y);
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
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_DIE;
    }

    @Override
    public void parse(byte[] bytes) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            id = new UUID(in.readLong(), in.readLong());
            x = in.readInt();
            y = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
