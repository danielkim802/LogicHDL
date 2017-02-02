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
    private boolean collapsed;

    public Circuit() {
        super(0, 0);
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        components = new ArrayList<>();
        collapsed = false;
    }

    // IO methods
    public Map<String, Constant> getCircuitInputs() {
        return inputs;
    }
    public Map<String, Output> getCircuitOutputs() {
        return outputs;
    }

    // component methods
    public void addComponent(Component comp) {
        components.add(comp);
    }
    public void remove(Component component) {
        component.delete();
        if (components.remove(component)) {
            return;
        }
        for (String key : inputs.keySet()) {
            if (inputs.get(key) == component) {
                inputs.remove(key);
                return;
            }
        }
        for (String key : outputs.keySet()) {
            if (outputs.get(key) == component) {
                outputs.remove(key);
                return;
            }
        }
    }
    public void setComponents(ArrayList<Component> comps) {
        components = comps;
    }
    public List<Component> getComponents() {
        return components;
    }
    public List<Dot> getAllDots() {
        List<Dot> all = new ArrayList<>();
        for (Component component : getComponents()) {
            all.addAll(component.getDots());
        }
        return all;
    }
    public List<Wire> getAllWires() {
        List<Wire> all = new ArrayList<>();
        for (Component component : getComponents()) {
            all.addAll(component.getWires());
        }
        return all;
    }

    // circuit methods
    public void clear() {
        inputs.clear();
        outputs.clear();
        components.clear();
    }
    public void propagateLocal() {
        for (Component component : new ArrayList<>(getComponents())) {
            component.propagate();
        }
    }
    // collapses circuit down to a single component
    public void collapse() {

        // do commands in constructor
        setIO(0, 0);
        makeDots();
        setDotPositions();
        updateDots();

        collapsed = true;
    }
    public void expand() {

        // clear everything
        inputs.clear();
        outputs.clear();
        getInputs().clear();
        getOutputs().clear();
        getInputDots().clear();
        getOutputDots().clear();

        collapsed = false;
    }

    // abstract methods
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
    // propagates all inputs to outputs within a single function, important
    // for update call in view
    private void propagateToOutput(Component output) {

        // for all inputs get values or propagate previous if not assigned
        for (Wire wire : output.getInputs().values()) {
            propagateToOutput(wire.getFrom());
        }

        output.propagate();
    }
    public void propagate() {
        if (allInputsAssigned()) {

            // set circuit-level inputs to values of component-level inputs
            for (String name : inputs.keySet()) {
                inputs.get(name).set(getInputs().get(name).value());
            }

            // propagate inputs to outputs and assign values to component output wires
            for (String name : outputs.keySet()) {
                if (getOutputs().get(name).size() > 0) {
                    // propagate input to this output
                    propagateToOutput(outputs.get(name));

                    // propagate value of output to wire
                    for (Wire wire : getOutputs().get(name)) {
                        wire.set(outputs.get(name).value());
                    }
                }
            }
        }
    }
    public void setDotPositions() {
        if (getInputs().size() == 0 || getOutputs().size() == 0) {
            return;
        }

        int height = getImage().getHeight();
        int width = getImage().getWidth();
        int inspacing = height / getInputs().size();
        int outspacing = height / getOutputs().size();
        int offset = 0;
        int adjusty = height / 2;
        int adjustx = width / 2;

        int i = 0;
        for (String key : getInputDots().keySet()) {
            getInputDots().get(key).setXYOffset(offset - adjustx, -(i * inspacing) - (inspacing / 2) + adjusty);
            i ++;
        }
        i = 0;
        for (String key : getOutputDots().keySet()) {
            getOutputDots().get(key).setXYOffset(width - offset - adjustx, -(i * outspacing) - (outspacing / 2) + adjusty);
            i ++;
        }
    }
    public void setIO(int ins, int outs) {
        if (components == null) {
            return;
        }
        // assign inputs and outputs at circuit level and component level
        for (Component component : components) {
            if (component instanceof Constant) {
                inputs.put(component.getName(), (Constant) component);
                getInputs().put(component.getName(), new Wire());
            } else if (component instanceof Output) {
                outputs.put(component.getName(), (Output) component);
                getOutputs().put(component.getName(), new ArrayList<>());
            }
        }
    }
    public void draw(Graphics2D g) {
        if (collapsed) {
            drawImage(g);
            drawDots(g);
        } else {
            List<Wire> wires = new ArrayList<>();
            List<Component> copy = new ArrayList<>(components);

            // draw components
            for (Component component : copy) {
                component.draw(g);

                wires.addAll(component.getInputWires());
            }

            // draw wires
            for (Wire wire : wires) {
                wire.draw(g);
            }
        }
    }
}
