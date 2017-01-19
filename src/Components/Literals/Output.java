package Components.Literals;

import Components.Component;
import Components.Wire;
import Render.DrawHandler;

import java.awt.*;

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
        setImageIndex(isPropagating() ? 1 : 0);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
    }
    public void updateDots() {
        getInputDots().get("input").setXY(getX() - (getImage().getWidth() / 2), getY());
    }
    public void setIO(int ins, int outs) {
        getInputs().put("input", new Wire());
    }
}
