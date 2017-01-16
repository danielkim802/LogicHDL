package Components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Component {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, List<Wire>> outputs = new HashMap<>();

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
    public abstract void propagate();
}
