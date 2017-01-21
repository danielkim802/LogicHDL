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
    public double getXScale() {
        return xScale;
    }
    public double getYScale() {
        return yScale;
    }

    public int getXMouse(MouseEvent e) {
        return x + (int) (e.getX() / xScale);
    }
    public int getYMouse(MouseEvent e) {
        return y + (int) (e.getY() / yScale);
    }

    public Camera(View view) {
        this.view = view;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void scale(double x, double y) {
        xScale = x;
        yScale = y;
    }

    public void zoom(double x, double y) {
        xScale += x;
        yScale += y;
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void draw(Graphics2D g) {
        g.clearRect(0, 0, view.getWidth(), view.getHeight());
        g.scale(xScale, yScale);
        g.translate(-x, -y);
    }
}
