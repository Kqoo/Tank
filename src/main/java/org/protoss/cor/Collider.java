package org.protoss.cor;

import org.protoss.GameObject;

/**
 * 碰撞接口
 */
public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
