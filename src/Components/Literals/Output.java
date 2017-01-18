package Components.Literals;

import Components.Component;
import Components.Wire;

import java.awt.*;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Output extends Component {
    private long value;

    public Output() {
        super();
        getInputs().put("input", new Wire());
    }

    public long value() {
        return value;
    }
    public Output copy() {
        return new Output();
    }

    public void propagate() {
        setPropagating(true);
        if (allInputsValid()) {
            value = getInputs().get("input").value();
        }
        setPropagating(false);
    }

    public void draw(Graphics2D g) {
        g.setColor(isPropagating() ? Color.red : Color.black);
        g.drawRect(getX(), getY(), 30, 30);
    }
}
