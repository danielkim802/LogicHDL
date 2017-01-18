package Components;

import Render.Drawable;

import java.awt.*;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Wire extends Drawable {
    private long value;
    private boolean valid;
    private Component from;
    private Component connect;

    public Wire() {}
    public Wire(Component f, Component c) {
        // component wire is pointing at
        from = f;
        connect = c;

        setXY(f.getX(), f.getY());
        setXY2(c.getX(), c.getY());
    }

    public Component getConnect() {
        return connect;
    }
    public void set(long val) {
        value = val;
        valid = true;
        connect.propagate();
    }
    public void setValid(boolean val) {
        valid = val;
    }
    public boolean isValid() {
        return valid;
    }
    public long value() {
        return value;
    }

    public void draw(Graphics2D g) {
        g.drawLine(getX(), getY(), getX2(), getY2());
    }
}
