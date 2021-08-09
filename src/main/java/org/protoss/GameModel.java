package org.protoss;

import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.utils.PropertyManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameModel {

    private final Tank mainTank = new Tank(200, 600, Dir.UP, Group.we, this);
    private final List<Tank> enemies = new ArrayList<>();
    private final List<Explode> explodes = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    public GameModel() {
        int initEnemyCount = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("initEnemyCount")));
        //敌方坦克
        for (int i = 0; i < initEnemyCount; i++) {
            Tank tank = new Tank(100 * (i + 1), 200, Dir.DOWN, Group.enemy, this);
            tank.setMoving(true);
            enemies.add(tank);
        }
    }

    public void paint(Graphics g) {
        mainTank.paint(g);
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量:" + bullets.size(), 10, 60);
        g.drawString("敌人数量:" + enemies.size(), 10, 80);
        g.drawString("爆炸数量:" + explodes.size(), 10, 100);
        g.setColor(color);

        List<Bullet> enemyBullets = bullets.stream()
                                           .filter(bullet -> bullet.getGroup().equals(Group.enemy))
                                           .collect(Collectors.toList());
        //敌方坦克
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).paint(g);
        }
        //爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //敌方坦克
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g);
        }
        //碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                bullets.get(i).collideWidth(enemies.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return mainTank;
    }

    public List<Explode> getExplodes() {
        return explodes;
    }

    public List<Tank> getEnemies() {
        return enemies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
