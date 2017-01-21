package Render;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by danielkim802 on 1/19/17.
 */
public class Camera {
    private View view;
    private int x = 0;
    private int y = 0;
    private double xScale = 1.0;
    private double yScale = 1.0;

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
        return (e.getX() / (int) xScale) - x;
    }
    public int getYMouse(MouseEvent e) {
        return (e.getY() / (int) yScale) - y;
    }

    public Camera(View view) {
        this.view = view;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void zoom(double x, double y) {
        xScale = x;
        yScale = y;
    }

    public void translate(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public void draw(Graphics2D g) {
        g.clearRect(0, 0, view.getWidth(), view.getHeight());
        g.scale(xScale, yScale);
        g.translate(x, y);
    }
}
