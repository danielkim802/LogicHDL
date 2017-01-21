package Components.Literals;

import Components.Component;
import Components.Wire;
import Render.DrawHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danielkim802 on 1/17/17.
 */
public class Joint extends Component {
    private int outputs;

    public Joint() {
        super(0, 0);
        outputs = 0;
    }

    public void connect(String input, Component other) {
        super.connect(""+outputs, input, other);
        outputs ++;
    }

    public Joint copy() {
        return new Joint();
    }

    public void propagate() {
        if (allInputsValid()) {
            for (Wire wire : getOutputs().get("to")) {
                wire.set(getInputs().get("from").value());
            }
        }
    }

    public void draw(Graphics2D g) {
        drawWires(g);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
        drawDots(g);
        drawSelected(g);
    }

    public void updateDots() {
        getInputDots().get("from").setXY(getX() - 4, getY());
        getOutputDots().get("to").setXY(getX() + 4, getY());
    }
    public void setIO(int ins, int outs) {
        getInputs().put("from", new Wire());
        getOutputs().put("to", new ArrayList<>());
    }
}
