package Render;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class DrawHandler {
    private static Color defaultColor = Color.black;

    private static int centerCoord(int coord, int width) {
        return coord - (width / 2);
    }

    public static void drawImage(Graphics2D g, BufferedImage img, int x, int y) {
        if (img != null) {
            g.drawImage(img, null, centerCoord(x, img.getWidth()), centerCoord(y, img.getHeight()));
        }
    }

    public static void drawImageRelative(Graphics2D g, BufferedImage img, Drawable relative, int x, int y) {
        int normalx = centerCoord(relative.getX(), relative.getImage().getWidth());
        int normaly = centerCoord(relative.getY(), relative.getImage().getHeight());
        drawImage(g, img, normalx + x, normaly + y);
    }

    public static void drawRect(Graphics2D g, Color c, int width, int height, int x, int y) {
        g.setColor(c);
        g.drawRect(centerCoord(x, width), centerCoord(y, height), width, height);
        g.setColor(defaultColor);
    }
}
