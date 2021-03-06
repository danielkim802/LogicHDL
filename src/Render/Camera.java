package Render;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

/**
 * Created by danielkim802 on 1/19/17.
 */
public class Camera {
    private View view;
    private int x, y, xSave, ySave;
    private double xScale = 1.0;
    private double yScale = 1.0;
    private double scale = 1.2;

    public Camera(View view) {
        this.view = view;
    }
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void savePoint() {
        xSave = x;
        ySave = y;
    }
    public void resetSave() {
        xSave = 0;
        ySave = 0;
    }
    public int getXSave() {
        return xSave;
    }
    public int getYSave() {
        return ySave;
    }
    public double getRealXScale() {
        return xScale;
    }
    public double getRealYScale() {
        return yScale;
    }
    public double getXScale() {
        return Math.pow(scale, xScale);
    }
    public double getYScale() {
        return Math.pow(scale, yScale);
    }

    public int getXMouse(MouseEvent e) {
        return x + convertDistX(e.getX());
    }
    public int getYMouse(MouseEvent e) {
        return y + convertDistY(e.getY());
    }
    public int convertDistX(int dist) {
        return (int) (dist / getXScale());
    }
    public int convertDistY(int dist) {
        return (int) (dist / getYScale());
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
        xScale -= x;
        yScale -= y;
    }
    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void clear(Graphics2D g) {
        g.clearRect(0, 0, view.getWidth(), view.getHeight());
    }
    public void draw(Graphics2D g) {
        g.scale(Math.pow(scale, xScale), Math.pow(scale, yScale));
        g.translate(-x, -y);
    }
}
