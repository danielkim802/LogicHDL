package Render;

import Actions.Constants;
import Actions.GUIElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

    public static void drawImage(Graphics2D g, BufferedImage img, Constants.Direction dir, int x, int y) {
        if (img != null) {
            double xpos = img.getWidth() / 2;
            double ypos = img.getHeight() / 2;
            double theta = Math.toRadians(0);
            AffineTransform tx;
            AffineTransformOp op;

            switch (dir) {
                case NORTH:
                    theta = Math.toRadians(270);
                    break;
                case EAST:
                    theta = Math.toRadians(0);
                    break;
                case SOUTH:
                    theta = Math.toRadians(90);
                    break;
                case WEST:
                    theta = Math.toRadians(180);
                    break;
            }

            tx = AffineTransform.getRotateInstance(theta, xpos, ypos);
            op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(op.filter(img, null), centerCoord(x, img.getWidth()), centerCoord(y, img.getHeight()), null);
        }
    }

    public static void drawImageRelative(Graphics2D g, BufferedImage img, GUIElement relative, int x, int y) {
        int normalx = centerCoord(relative.getX(), relative.getImage().getWidth());
        int normaly = centerCoord(relative.getY(), relative.getImage().getHeight());
        drawImage(g, img, normalx + x, normaly + y);
    }

    public static void drawRect(Graphics2D g, Color c, int width, int height, int x, int y) {
        g.setColor(c);
        g.drawRect(centerCoord(x, width), centerCoord(y, height), width, height);
        g.setColor(defaultColor);
    }
    public static void drawRectPoint(Graphics2D g, Color c, int x1, int y1, int x2, int y2) {
        g.setColor(c);
        int x1p = Math.min(x1, x2);
        int x2p = Math.max(x1, x2);
        int y1p = Math.min(y1, y2);
        int y2p = Math.max(y1, y2);
        g.drawRect(x1p, y1p, x2p - x1p, y2p - y1p);
        g.setColor(defaultColor);
    }
    public static void clearRect(Graphics2D g, int width, int height, int x, int y) {
        g.clearRect(centerCoord(x, width), centerCoord(y, height), width, height);
    }
}
