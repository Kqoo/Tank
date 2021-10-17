package org.protoss.cor;

import org.protoss.*;

public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet bullet = (Bullet) o1;
            Wall wall = (Wall) o2;
            if (bullet.getRect().intersects(wall.getRect())) {
                //添加爆炸
                Explode explode = new Explode();
                int ex = bullet.getX() + bullet.getWidth() / 2 - explode.getWidth() / 2;
                int ey = bullet.getY() + bullet.getHeight() / 2 - explode.getHeight() / 2;
                explode.setX(ex);
                explode.setY(ey);
                //爆炸
                GameModel.getINSTANCE().add(explode);
                bullet.die();
            }
        } else if (o2 instanceof Bullet && o1 instanceof Wall) {
            collide(o2, o1);
        }
        return true;
    }
}
