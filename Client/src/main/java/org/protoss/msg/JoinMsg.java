package org.protoss.msg;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.protoss.GameModel;
import org.protoss.Tank;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.io.*;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Slf4j
public class JoinMsg implements Msg {
    private int x;
    private int y;
    private Group group;
    private Dir dir;
    private boolean moving;
    private UUID id;

    public JoinMsg() {
    }

    public JoinMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public JoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.id = tank.getId();
        this.dir = tank.getDir();
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeBoolean(isMoving());
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
        return MsgType.JOIN;
    }

    @Override
    public void parse(byte[] bytes) {
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            x = in.readInt();
            y = in.readInt();
            dir = Dir.values()[in.readInt()];
            group = Group.values()[in.readInt()];
            moving = in.readBoolean();
            id = new UUID(in.readLong(), in.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx) {
        //判断是否是新玩家加入
        if (!GameModel.getINSTANCE().getMainTank().getId().equals(id) &&
                GameModel.getINSTANCE().findTankById(id) == null) {
            log.info("新坦克加入:{}", this);
            Tank tank = new Tank(this);
            GameModel.getINSTANCE().add(tank);
            ctx.writeAndFlush(new JoinMsg(GameModel.getINSTANCE().getMainTank()));
            Map<UUID, Tank> tankMap = GameModel.getINSTANCE().getTanks();
            log.info("所有坦克:{}", tankMap);
        }
    }
}
