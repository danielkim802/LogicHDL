package Components;

import Components.Literals.Constant;
import Components.Literals.Output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Circuit extends Component {
    private Map<String, Constant> inputs;
    private Map<String, Output> outputs;
    private List<Component> components;

    public Circuit() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        components = new ArrayList<>();
    }

    public void addComponent(Component comp) {
        components.add(comp);
    }
    public void setComponents(List<Component> comps) {
        components = comps;
    }
    public void addInput(String name, long value) {
        inputs.put(name, new Constant(value));
        getInputs().put(name, new Wire());
    }
    public void addOutput(String name) {
        outputs.put(name, new Output());
        getOutputs().put(name, new ArrayList<>());
    }
    public Constant getInput(String name) {
        return inputs.get(name);
    }
    public Output getOutput(String name) {
        return outputs.get(name);
    }
    // TODO
    public Circuit copy() {
        return new Circuit();
    }

    public void propagate() {
        if (allInputsValid()) {
            for (String name : inputs.keySet()) {
                inputs.get(name).set(getInputs().get(name).value());
                inputs.get(name).propagate();
            }
            for (String name : outputs.keySet()) {
                for (Wire wire : getOutputs().get(name)) {
                    wire.set(outputs.get(name).value());
                }
            }
        }
    }
}
