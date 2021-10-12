package org.protoss.cor;

import org.protoss.Bullet;
import org.protoss.GameObject;
import org.protoss.Wall;

public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet bullet = (Bullet) o1;
            Wall wall = (Wall) o2;
            if (bullet.getRect().intersects(wall.getRect())) {
                bullet.die();
            }
        } else if (o2 instanceof Bullet && o1 instanceof Wall) {
            collide(o2, o1);
        }
        return true;
    }
}
