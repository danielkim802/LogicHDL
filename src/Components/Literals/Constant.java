package Components.Literals;

import Components.Component;
import Components.Wire;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Constant extends Component {
    private long value = 0;

    public Constant() {
        super(0, 0);
        value = 0;
    }

    public void set(long val) {
        value = val;
    }
    public void connect(String input, Component c) {
        connect("output", input, c);
    }
    public void toggle() {
        value = value == 0 ? 1 : 0;
    }

    // abstract methods
    public Constant copy() {
        Constant c = new Constant();
        c.set(value);
        return c;
    }
    public void propagate() {
        for (Wire wire : getOutputs().get("output")) {
            wire.set(value);
        }
    }
    public void setDotPositions() {
        getOutputDots().get("output").setXYOffset(getImage().getWidth() / 2, 0);
    }
    public void setIO(int ins, int outs) {
        getOutputs().put("output", new ArrayList<>());
    }
    public void draw(Graphics2D g) {
        setImageIndex(value == 0 ? 0 : 1);
        drawImage(g);
        drawDots(g);
        drawSelected(g);
    }

    public void click() {
        toggle();
    }
}
