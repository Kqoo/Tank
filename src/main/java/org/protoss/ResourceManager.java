package org.protoss;

import org.protoss.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager {
    public static BufferedImage myTankU, myTankD, myTankL, myTankR;
    public static BufferedImage enemyTankU, enemyTankD, enemyTankL, enemyTankR;
    public static BufferedImage bulletU, bulletD, bulletL, bulletR;
    public static BufferedImage bullet2U, bullet2D, bullet2L, bullet2R;
    public static BufferedImage[] explodes = new BufferedImage[16];

    private ResourceManager() {
    }

    static {
        try {
            myTankU = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/GoodTank1.png"));
            myTankD = ImageUtil.rotate(myTankU, 180);
            myTankL = ImageUtil.rotate(myTankU, -90);
            myTankR = ImageUtil.rotate(myTankU, 90);

            enemyTankU = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/BadTank1.png"));
            enemyTankD = ImageUtil.rotate(enemyTankU, 180);
            enemyTankL = ImageUtil.rotate(enemyTankU, -90);
            enemyTankR = ImageUtil.rotate(enemyTankU, 90);

            bulletU = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/bulletU.gif"));
            bulletD = ImageUtil.rotate(bulletU, 180);
            bulletL = ImageUtil.rotate(bulletU, -90);
            bulletR = ImageUtil.rotate(bulletU, 90);

            bullet2U = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/bulletU.png"));
            bullet2D = ImageUtil.rotate(bullet2U, 180);
            bullet2L = ImageUtil.rotate(bullet2U, -90);
            bullet2R = ImageUtil.rotate(bullet2U, 90);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/e" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
