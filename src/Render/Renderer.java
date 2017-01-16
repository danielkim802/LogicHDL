package Render;

import java.awt.*;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Renderer {
    private int x;
    private int y;

    private boolean selected = false;

    public void setX(int pos) {
        x = pos;
    }
    public void setY(int pos) {
        y = pos;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setSelected(boolean bool) {
        selected = bool;
    }

    public abstract void draw(Graphics2D g);
}
