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

    public Constant(long val) {
        super();
        value = val;
        getOutputs().put("output", new ArrayList<>());
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
        g.setColor(isPropagating() ? Color.red : Color.black);
        g.drawRect(getX(), getY(), 30, 30);
    }

}
