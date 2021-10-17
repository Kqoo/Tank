package org.protoss.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    /**
     * 旋转图片
     * @param image 原始图片
     * @param degree 角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotate(BufferedImage image, int degree) {
        int width = image.getWidth();
        int height = image.getHeight();
        int transparency = image.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2D;
        (graphics2D = (img = new BufferedImage(width, height, transparency))
                .createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree), width / 2, height / 2);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return img;
    }
}
