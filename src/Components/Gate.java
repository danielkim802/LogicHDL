package Components;

import Render.DrawHandler;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Gate extends Component {
    private LongBinaryOperator operator;
    private LongUnaryOperator finaloperator = a -> a;
    private int bits;

    public Gate(int inputlen) {
        super(inputlen, 0);
        bits = 1;
    }

    public void setBits(int b) {
        bits = b;
    }
    public void setOp(LongBinaryOperator op) {
        operator = op;
    }
    public void setFinalOp(LongUnaryOperator op) {
        finaloperator = op;
    }

    public void connect(String input, Component c) {
        connect("output", input, c);
    }

    public Gate copy() {
        try {
            Gate copy;
            if (getClass().getName() == "Not") {
                copy = getClass().newInstance();
            }
            else {
                copy = getClass().getConstructor(int.class).newInstance(getInputs().size());
            }
            copy.operator = operator;
            copy.finaloperator = finaloperator;
            return copy;
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public void propagate() {
        if (allInputsValid()) {
            // map each input to a long |> apply operator to all values
            long result = getInputs().values().stream().mapToLong( a -> a.value() ).reduce( operator ).getAsLong();

            // propagate result to outputs
            for (Wire wire : getOutputs().get("output")) {

                // cut down to appropriate number of bits and set
                wire.set(finaloperator.applyAsLong(result) & (long) (Math.pow(2, bits) - 1));
            }
        }
    }

    public void updateDots() {
        try {
            int height = getImage().getHeight();
            int width = getImage().getWidth();
            int inputs = getInputs().size();
            int offset = 0;
            int adjusty = height / 2;
            int adjustx = width / 2;
            int spacing = height / inputs;

            for (int i = 0; i < getInputDots().size(); i ++) {
                getInputDots().get(""+i).setXY(getX() + offset - adjustx, getY() + (i * spacing) + (spacing / 2) - adjusty);
            }

            getOutputDots().get("output").setXY(getX() + width - offset - adjustx, getY() + (height / 2) - adjusty);
        }
        catch (NullPointerException e) {}
    }

    public void setIO(int inputlen, int outputlen) {
        for (int i = 0; i < inputlen; i ++) {
            getInputs().put(""+i, new Wire());
        }
        getOutputs().put("output", new ArrayList<>());
    }

    public void draw(Graphics2D g) {
        drawWires(g);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
        drawDots(g);
        drawSelected(g);
    }
}
