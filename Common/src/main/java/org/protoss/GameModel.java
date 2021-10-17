package org.protoss;

import org.protoss.constant.Constant;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.cor.ColliderChain;
import org.protoss.utils.PropertyManager;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel {

    private static final GameModel INSTANCE = new GameModel();
    private Random random = new Random();
    //随机位置
    private final Tank mainTank = new Tank(random.nextInt(Constant.GAME_WIDTH), random.nextInt(Constant.GAME_HEIGHT), Dir.UP, Group.we);
    private List<GameObject> gameObjects = new ArrayList<>();
    private ColliderChain colliderChain;
    private Map<UUID, Tank> tankFromNet = new HashMap<>();

    private GameModel() {
//        add(mainTank);
        int initEnemyCount = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("initEnemyCount")));
        //敌方坦克
        /*
            for (int i = 0; i < initEnemyCount; i++) {
            Tank tank = new Tank(100 * i, 200, Dir.DOWN, Group.enemy);
            tank.setMoving(true);
            add(new HPDecorator(tank));
        }*/
        //墙
        add(new Wall(200, 150, 50, 100));
        add(new Wall(550, 150, 50, 100));
        add(new Wall(200, 400, 400, 100));
        colliderChain = new ColliderChain();
    }

    public Map<UUID, Tank> getTanks() {
        return tankFromNet;
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (gameObject instanceof Tank) {
            Tank tank = (Tank) gameObject;
            if (!tank.getId().equals(mainTank.getId())) {
                tankFromNet.put(tank.getId(), tank);
            }
        }
    }

    public void remove(GameObject gameObject) {
        gameObjects.remove(gameObject);
        if (gameObject instanceof Tank) {
            Tank tank = (Tank) gameObject;
            tankFromNet.remove(tank.getId());
        }
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹数量:" + bullets.size(), 10, 60);
//        g.drawString("敌人数量:" + enemies.size(), 10, 80);
//        g.drawString("爆炸数量:" + explodes.size(), 10, 100);
        g.setColor(color);

        mainTank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }
        //碰撞检测
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
//                collider.collide(gameObjects.get(i), gameObjects.get(j));
//                tanktankCollider.collide(gameObjects.get(i), gameObjects.get(j));
                colliderChain.collide(gameObjects.get(i), gameObjects.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return mainTank;
    }

    public static GameModel getINSTANCE() {
        return INSTANCE;
    }

    public Tank findTankById(UUID id) {
        return tankFromNet.get(id);
    }

    public static boolean isMainTank(UUID id) {
        return getINSTANCE().getMainTank().getId().equals(id);
    }
}
