package org.protoss;

import org.protoss.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager {
    public static BufferedImage tankU, tankD, tankL, tankR;
    public static BufferedImage bulletU, bulletD, bulletL, bulletR;
    public static BufferedImage[] explodes = new BufferedImage[16];

    private ResourceManager() {
    }

    static {
        try {
            tankU = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/BadTank1.png"));
            tankD = ImageUtil.rotate(tankU, 180);
            tankL = ImageUtil.rotate(tankU, -90);
            tankR = ImageUtil.rotate(tankU, 90);

            bulletU = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/bulletU.gif"));
            bulletD = ImageUtil.rotate(bulletU, 180);
            bulletL = ImageUtil.rotate(bulletU, -90);
            bulletR = ImageUtil.rotate(bulletU, 90);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(ResourceManager.class.getResourceAsStream("../../images/e" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
