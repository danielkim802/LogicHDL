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
    private Dot fromDot;
    private Component connect;
    private Dot connectDot;

    public Wire() {}
    public Wire(Component f, Dot fdot, Component c, Dot cdot) {
        from = f;
        fromDot = fdot;
        connect = c;
        connectDot = cdot;

        setXY(fdot.getX(), fdot.getY());
        setXY2(cdot.getX(), cdot.getY());
    }

    public Dot getFromDot() {
        return fromDot;
    }
    public Dot getConnectDot() {
        return connectDot;
    }
    public Component getConnect() {
        return connect;
    }
    public void set(long val) {
        value = val;
        valid = true;
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
        g.setColor(value == 0 ? Color.black : Color.red);
        g.drawLine(fromDot.getX(), fromDot.getY(), connectDot.getX(), connectDot.getY());
    }
    public void updateDots() {}
    public void actionClicked() {}
}
