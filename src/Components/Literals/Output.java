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
        if (allInputsAssigned()) {
            value = getInputs().get("input").value();
        }
    }

    public void draw(Graphics2D g) {
        setImageIndex(value == 0 ? 0 : 1);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
        drawDots(g);
        drawSelected(g);
    }
    public void setDotPositions() {
        getInputDots().get("input").setXYRelative(-getImage().getWidth() / 2, 0);
    }
    public void setIO(int ins, int outs) {
        getInputs().put("input", new Wire());
    }
}
