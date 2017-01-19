package Render;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by danielkim802 on 1/19/17.
 */
public class Camera {
    private int x = 0;
    private int y = 0;
    private double xScale = 1.0;
    private double yScale = 1.0;
    private int xTrans = 0;
    private int yTrans = 0;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getxScale() {
        return xScale;
    }
    public double getyScale() {
        return yScale;
    }

    public int getXMouse(MouseEvent e) {
        return (e.getX() / (int) xScale) - xTrans;
    }
    public int getYMouse(MouseEvent e) {
        return (e.getY() / (int) yScale) - yTrans;
    }

    public void scale(Graphics2D g, double x, double y) {
        g.scale(x, y);
        xScale = x;
        yScale = y;
    }

    public void translate(Graphics2D g, int x, int y) {
        g.translate(x, y);
        xTrans = x;
        yTrans = y;
    }
}
