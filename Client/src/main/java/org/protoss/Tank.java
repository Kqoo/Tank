package org.protoss;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.protoss.constant.Constant;
import org.protoss.constant.Dir;
import org.protoss.constant.Group;
import org.protoss.msg.JoinMsg;
import org.protoss.strategy.FireStrategy;
import org.protoss.utils.PropertyManager;
import org.protoss.utils.ResourceManager;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Data
@Slf4j
public class Tank extends GameObject {
    private int prevX;
    private int prevY;
    private Dir dir;
    private int speed = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("tankSpeed")));
    private boolean moving = false;
    private boolean living = true;
    private Group group;
    private static Random random = new Random();
    private Rectangle rect;
    private FireStrategy fireStrategy;
    private UUID id = UUID.randomUUID();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        width = ResourceManager.myTankD.getWidth();
        height = ResourceManager.myTankD.getHeight();
        this.dir = dir;
        this.group = group;
        rect = new Rectangle(x, y, width, height);
        try {
            if (group == Group.we) {
                fireStrategy = (FireStrategy) Class.forName(PropertyManager.get("weFireStrategy")).newInstance();
            } else {
                fireStrategy = (FireStrategy) Class.forName(PropertyManager.get("enemyFireStrategy")).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("开火策略异常：", e);
        }
    }

    public Tank(JoinMsg joinMsg) {
        this(joinMsg.getX(), joinMsg.getY(), joinMsg.getDir(), joinMsg.getGroup());
        moving = joinMsg.isMoving();
        id = joinMsg.getId();
    }


    public void paint(Graphics g) {
        if (!living) {
            GameModel.getINSTANCE().remove(this);
            Color color = g.getColor();
            Font font = g.getFont();
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Default", Font.BOLD, 50));
            g.drawString("GAME OVER!", 250, 350);
            g.setColor(color);
            g.setFont(font);
            return;
        }
        move();
        Image tankImg = null;
        if (group == Group.we) {
            switch (dir) {
                case UP:
                    tankImg = ResourceManager.myTankU;
                    break;
                case DOWN:
                    tankImg = ResourceManager.myTankD;
                    break;
                case LEFT:
                    tankImg = ResourceManager.myTankL;
                    break;
                case RIGHT:
                    tankImg = ResourceManager.myTankR;
                    break;
            }
        } else {
            switch (dir) {
                case UP:
                    tankImg = ResourceManager.enemyTankU;
                    break;
                case DOWN:
                    tankImg = ResourceManager.enemyTankD;
                    break;
                case LEFT:
                    tankImg = ResourceManager.enemyTankL;
                    break;
                case RIGHT:
                    tankImg = ResourceManager.enemyTankR;
                    break;
            }
        }
        g.drawImage(tankImg, x, y, null);
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(id.toString(), x - 50, y - 10);
        g.setColor(color);
    }

    private void move() {
        if (!living) {
            return;
        }
        //记录上一次位置
        prevX = x;
        prevY = y;
        if (moving) {
            switch (dir) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
        }
//        if (group == Group.enemy && Math.random() > 0.88) {
//            fire();
//        }
//        if (group == Group.enemy && Math.random() > 0.88) {
//            randomDir();
//        }
        boundCheck();
        //边侧检测后更新rect坐标
        rect.x = x;
        rect.y = y;
    }

    /**
     * 边界检测
     */
    private void boundCheck() {
        if (x < 0) {
            x = 0;
        } else if (x > Constant.GAME_WIDTH - width) {
            x = Constant.GAME_WIDTH - width;
        }
        if (y < 30) {
            y = 30;
        } else if (y > Constant.GAME_HEIGHT - height) {
            y = Constant.GAME_HEIGHT - height;
        }
    }

    private void randomDir() {
        dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fireStrategy.fire(this);
        //发送消息 因为有3连发 所以写tankFrame里面
//        Client.getINSTANCE().send(TankFireMsg.builder()
//                                             .id(id)
//                                             .x(x)
//                                             .y(y)
//                                             .dir(dir)
//                                             .group(group)
//                                             .build());
    }

    public void die() {
        die(x, y);
    }

    public void die(int x, int y) {
        log.info("坦克<{}>爆炸", id);
        Explode explode = new Explode();
        int ex = x + width / 2 - explode.width / 2;
        int ey = y + height / 2 - explode.height / 2;
        explode.setX(ex);
        explode.setY(ey);
        //爆炸
        GameModel.getINSTANCE().add(explode);
//        //发送消息
//        if (isLiving()) {
//            Client.getINSTANCE().send(new TankDieMsg(id, x, y));
//        }
        living = false;
    }

}
