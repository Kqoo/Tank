package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.protoss.GameModel;
import org.protoss.Tank;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TankDieMsg implements Msg {

    private UUID id;
    public TankDieMsg() {
    }

    @Override
    public void handle(ChannelHandlerContext ctx) {
        if (GameModel.isMainTank(id)) {
            return;
        }
        Tank tank = GameModel.getINSTANCE().findTankById(id);
        if (tank != null) {
            tank.die();
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
