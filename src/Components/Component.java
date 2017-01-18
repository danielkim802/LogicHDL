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
    private Map<String, List<Wire>> outputs = new HashMap<>();

    private boolean propagating = false;

    public Component() {
        setImages(ResourceLibrary.getImages(this.getClass()));
    }

    public Map<String, Wire> getInputs() {
        return inputs;
    }
    public Map<String, List<Wire>> getOutputs() {
        return outputs;
    }
    public void connect(String output, String input, Component other) {
        Wire wire = new Wire(this, other);
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
}
