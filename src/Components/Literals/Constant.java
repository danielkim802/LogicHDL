package Components.Literals;

import Components.Component;
import Components.Wire;
import Render.DrawHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Constant extends Component {
    private long value = 0;

    public Constant(long val) {
        super(0, 0);
        value = val;
    }

    public void set(long val) {
        value = val;
    }

    public void connect(String input, Component c) {
        connect("output", input, c);
    }

    public Constant copy() {
        return new Constant(value);
    }

    public void propagate() {
        setPropagating(true);
        for (Wire wire : getOutputs().get("output")) {
            wire.set(value);
        }
        setPropagating(false);
    }

    public void draw(Graphics2D g) {
        drawWires(g);
        setImageIndex(isPropagating() ? 1 : 0);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
    }

    public void updateDots() {
        getOutputDots().get("output").setXY(getX() + (getImage().getWidth() / 2), getY());
    }
    public void setIO(int ins, int outs) {
        getOutputs().put("output", new ArrayList<>());
    }

}
