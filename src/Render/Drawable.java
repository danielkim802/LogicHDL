package Render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Drawable {
    private int x, y, x2, y2;
    private int imageIndex;
    private List<BufferedImage> images;

    private boolean selected = false;

    public void setX(int pos) {
        x = pos;
    }
    public void setY(int pos) {
        y = pos;
    }
    public void setXY(int xpos, int ypos) {
        x = xpos;
        y = ypos;
    }

    public void setX2(int pos) {
        x2 = pos;
    }
    public void setY2(int pos) {
        y2 = pos;
    }
    public void setXY2(int xpos, int ypos) {
        x2 = xpos;
        y2 = ypos;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

    public void setSelected(boolean bool) {
        selected = bool;
    }

    public abstract void draw(Graphics2D g);
}
