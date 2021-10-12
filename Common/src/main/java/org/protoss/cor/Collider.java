package org.protoss.cor;

import org.protoss.GameObject;

/**
 * 碰撞接口
 */
public interface Collider {
    /**
     * 碰撞检测
     * @param o1 游戏物体1
     * @param o2 游戏物体2
     * @return 是否调用下一个责任链结点
     */
    boolean collide(GameObject o1, GameObject o2);
}
