package Components.Literals;

import Components.Component;
import Components.Wire;
import Render.Camera;
import Render.DrawHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Output extends Component {
    private long value;

    public Output() {
        super(0, 0);
    }

    public long value() {
        return value;
    }

    // abstract methods
    public Output copy() {
        return new Output();
    }
    public void propagate() {
        if (allInputsAssigned()) {
            value = getInputs().get("input").value();
            setImageIndex(value == 0 ? 0 : 1);
        }
    }
    public void setDotPositions() {
        getInputDots().get("input").setXYOffset(-getImage().getWidth() / 2, 0);
    }
    public void setIO(int ins, int outs) {
        getInputs().put("input", new Wire());
    }
}
