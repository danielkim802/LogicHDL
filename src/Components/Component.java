package Components;

import Render.Drawable;
import Render.ResourceLibrary;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Component extends Drawable {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, Dot> inputDots = new HashMap<>();
    private Map<String, List<Wire>> outputs = new HashMap<>();
    private Map<String, Dot> outputDots = new HashMap<>();
    private boolean propagating = false;

    public Component(int inputlen, int outputlen) {
        setImages(ResourceLibrary.getImages(this.getClass()));

        // make inputs and outputs
        setIO(inputlen, outputlen);

        // make dots
        for (String key : inputs.keySet()) {
            inputDots.put(key, new Dot());
        }
        for (String key : outputs.keySet()) {
            outputDots.put(key, new Dot());
        }

        // move dots to correct position
        updateDots();
    }

    public Map<String, Wire> getInputs() {
        return inputs;
    }
    public Map<String, List<Wire>> getOutputs() {
        return outputs;
    }
    public Map<String, Dot> getInputDots() {
        return inputDots;
    }
    public Map<String, Dot> getOutputDots() {
        return outputDots;
    }
    public void connect(String output, String input, Component other) {
        Wire wire = new Wire(this, outputDots.get(output), other, other.inputDots.get(input));
        other.getInputs().put(input, wire);
        outputs.get(output).add(wire);
    }
    public boolean allInputsValid() {
        return getInputs().values().stream().filter( a -> !a.isValid() ).toArray().length == 0;
    }

    public void setPropagating(boolean val) {
        propagating = val;
    }
    public boolean isPropagating() {
        return propagating;
    }

    public void drawWires(Graphics2D g) {
        for (List<Wire> wires : outputs.values()) {
            for (Wire wire : wires) {
                wire.draw(g);
            }
        }
    }

    // copies component and everything below it
    public abstract Component copy();
    public abstract void propagate();
    public abstract void setIO(int ins, int outs);
}
