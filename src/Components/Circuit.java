package Components;

import Components.Literals.Constant;
import Components.Literals.Output;

import java.awt.*;
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
    public Circuit copy() {
        Circuit copy = new Circuit();
        Map<Component, Component> map = new HashMap<>();

        // copy over all parts of the circuit to the map
        for (Constant input : inputs.values()) {map.put(input, input.copy());}
        for (Output output : outputs.values()) {map.put(output, output.copy());}
        for (Component component : components) {map.put(component, component.copy());}

        // note: there is probably a way to simplify this mess

        // connect everything together
        for (Component original : map.keySet()) {

            // copy over output connections from original -> copy
            for (String key : original.getOutputs().keySet()) {

                // copy over wires for a single output
                List<Wire> connections = new ArrayList<>();
                for (Wire wire : original.getOutputs().get(key)) {
                    Wire wirecopy = new Wire(map.get(wire.getConnect()));
                    connections.add(wirecopy);

                    // copy wire to input of other component
                    for (String keyother : wire.getConnect().getInputs().keySet()) {
                        if (wire.getConnect().getInputs().get(keyother) == wire) {
                            map.get(wire.getConnect()).getInputs().put(keyother, wirecopy);
                        }
                    }
                }
                map.get(original).getOutputs().put(key, connections);
            }
        }

        // copy map to new circuit
        for (String key : inputs.keySet()) {
            copy.inputs.put(key, (Constant) map.get(inputs.get(key)));
            copy.getInputs().put(key, new Wire());
        }
        for (String key : outputs.keySet()) {
            copy.outputs.put(key, (Output) map.get(outputs.get(key)));
            copy.getOutputs().put(key, new ArrayList<>());
        }
        for (Component component : components) {
            copy.components.add(map.get(component));
        }

        return copy;
    }

    public void propagateLocal() {
        for (String name : inputs.keySet()) {
            inputs.get(name).propagate();
        }
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

    public void draw(Graphics2D g) {
        for (Constant constant : inputs.values()) {
            constant.draw(g);
        }
        for (Output output : outputs.values()) {
            output.draw(g);
        }
        for (Component component : components) {
            component.draw(g);
        }
    }
}
