package Components.Literals;

import Components.Component;
import Components.Wire;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danielkim802 on 1/17/17.
 */
public class Joint extends Component {
    private int outputs;

    public Joint() {
        super();
        getInputs().put("from", new Wire());
        getOutputs().put("to", new ArrayList<>());
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
            setPropagating(true);
            for (Wire wire : getOutputs().get("to")) {
                wire.set(getInputs().get("from").value());
            }
            setPropagating(false);
        }
    }

    public void draw(Graphics2D g) {}
}
