package org.protoss.cor;

import org.protoss.GameObject;
import org.protoss.utils.PropertyManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 碰撞责任链
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        String[] colliders = Objects.requireNonNull(PropertyManager.get("colliders")).split(",");
        for (String colliderName : colliders) {
            try {
                Collider collider = (Collider) Class.forName(colliderName).newInstance();
                add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }


    public boolean collide(GameObject o1, GameObject o2) {
        for (Collider collider : colliders) {
            if (!collider.collide(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
