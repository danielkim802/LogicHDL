package Components;

import Render.Renderer;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Component extends Renderer {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, List<Wire>> outputs = new HashMap<>();

    private boolean propagating = false;

    public Map<String, Wire> getInputs() {
        return inputs;
    }
    public Map<String, List<Wire>> getOutputs() {
        return outputs;
    }
    public void connect(String output, String input, Component other) {
        Wire wire = new Wire(other);
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

    // copies component and everything below it
    public abstract Component copy();
    public abstract void propagate();
}
