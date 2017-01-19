package Components;

import Components.Literals.Constant;
import Components.Literals.Output;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Circuit extends Component {
    private Map<String, Constant> inputs;
    private Map<String, Output> outputs;
    private List<Component> components;

    public Circuit() {
        super(0, 0);
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
    public List<Component> getComponentList() {
        List<Component> comps = new ArrayList<>();
        for (Component c : components) {
            comps.add(c);
        }
        for (Constant c : inputs.values()) {
            comps.add(c);
        }
        for (Output c : outputs.values()) {
            comps.add(c);
        }
        return comps;
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
    public Map<String, Constant> getCircuitInputs() {
        return inputs;
    }
    public Output getOutput(String name) {
        return outputs.get(name);
    }
    public Map<String, Output> getCircuitOutputs() {
        return outputs;
    }

    public List<Component> getAllComponents() {
        List<Component> all = new ArrayList<>();
        all.addAll(inputs.values());
        all.addAll(outputs.values());
        all.addAll(components);
        return all;
    }
    public List<Dot> getAllDots() {
        List<Dot> all = new ArrayList<>();
        for (Component component : getAllComponents()) {
            all.addAll(component.getDots());
        }
        return all;
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
                    Wire wirecopy = new Wire(map.get(original), wire.getFromDot(), map.get(wire.getConnect()), wire.getConnectDot());
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
        for (Component component : getComponentList()) {
            component.propagate();
        }
    }

    public void propagate() {
        if (allInputsValid()) {
            for (String name : inputs.keySet()) {
                inputs.get(name).set(getInputs().get(name).value());
            }
            for (String name : outputs.keySet()) {
                for (Wire wire : getOutputs().get(name)) {
                    wire.set(outputs.get(name).value());
                }
            }
            propagateLocal();
        }
    }

    public void draw(Graphics2D g) {
        for (Component component : getComponentList()) {

            // draw components
            component.draw(g);
        }
    }
    public void updateDots() {}
    public void setIO(int ins, int outs) {}
}
