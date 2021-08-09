package org.protoss.cor;

import org.protoss.Bullet;
import org.protoss.GameObject;
import org.protoss.Tank;

public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            if (bullet.getGroup() == tank.getGroup()) {
                return true;
            }
            //相交
            if (bullet.getRect().intersects(tank.getRect())) {
                tank.die();
                bullet.die();
                return false;
            }
        } else if (o2 instanceof Bullet && o1 instanceof Tank) {
            collide(o2, o1);
        }
        return true;
    }
}
