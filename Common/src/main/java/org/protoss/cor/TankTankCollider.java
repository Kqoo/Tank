package org.protoss.cor;

import org.protoss.GameObject;
import org.protoss.Tank;

public class TankTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank tank1 = (Tank) o1;
            Tank tank2 = (Tank) o2;
            //相交
            if (tank1.getRect().intersects(tank2.getRect())) {
                //回到上一次位置
                tank1.setX(tank1.getPrevX());
                tank1.setY(tank1.getPrevY());
                tank2.setX(tank2.getPrevX());
                tank2.setY(tank2.getPrevY());
            }
        }
        return true;
    }
}
