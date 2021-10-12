package org.protoss.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.protoss.Tank;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class JoinMsg {
   private int x;
   private int y;
   private Group group;
   private Dir dir;
   private boolean moving;
   private UUID UUID;

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
        this.UUID = tank.getUuid();
        this.dir = tank.getDir();
    }

    public byte[] toBytes() {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeBoolean(isMoving());
            dos.writeLong(UUID.getMostSignificantBits());
            dos.writeLong(UUID.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
