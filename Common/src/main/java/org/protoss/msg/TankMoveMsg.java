package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.protoss.GameModel;
import org.protoss.Tank;
import org.protoss.constant.Dir;

import java.io.*;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TankMoveMsg implements Msg {

    private UUID id;
    private int x;
    private int y;
    private Dir dir;
    private boolean isMove;

    public TankMoveMsg() {
    }

    @Override
    public void handle(ChannelHandlerContext ctx) {
        //是自己发出的消息 返回
        if (GameModel.isMainTank(id)) {
            return;
        }
        Tank tank = GameModel.getINSTANCE().findTankById(id);
        if (tank != null) {
            tank.setX(x);
            tank.setY(y);
            tank.setMoving(isMove);
            tank.setDir(dir);
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
            dos.writeBoolean(isMove);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_MOVE;
    }

    @Override
    public void parse(byte[] bytes) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            id = new UUID(in.readLong(), in.readLong());
            x = in.readInt();
            y = in.readInt();
            isMove = in.readBoolean();
            dir = Dir.values()[in.readInt()];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
