import java.util.ArrayList;
import java.util.List;
import ComponentError.*;

/**
 * Created by danielkim802 on 12/26/16.
 */
// handles all components
public class ComponentHandler {
    private int nextID;
    private List<Component> components = new ArrayList<Component>();

    private static ComponentHandler handler = new ComponentHandler();

    public static ComponentHandler getHandler() {
        return handler;
    }

    // constructor
    private ComponentHandler() {
        nextID = 0;
    }

    // creates a new id
    private int generateID() {
        nextID ++;
        return nextID-1;
    }

    // checks if parents are evaluated
    public boolean parentsEvaluated(int id) {
        Component current = components.get(id);
        try {
            current.checkEvaluated();
            return true;
        }
        catch (ComponentEvaluationError e) {
            return false;
        }
    }

    // checks if a component is evaluated
    public boolean isEvaluated(int id) {
        return components.get(id).isEvaluated();
    }

    // creates a gate and returns its id
    public int makeGate(String gate, int inputs) throws ComponentInvalidError {
        Gate newgate = null;
        switch(gate) {
            case "AND":
                newgate = new AND(inputs, generateID());
                break;
            case "OR":
                newgate = new OR(inputs, generateID());
                break;
            case "NOT":
                newgate = new NOT(generateID());
                break;
            case "NOR":
                newgate = new NOR(inputs, generateID());
                break;
            case "XOR":
                newgate = new XOR(inputs, generateID());
                break;
            case "XNOR":
                newgate = new XNOR(inputs, generateID());
                break;
            case "NAND":
                newgate = new NAND(inputs, generateID());
                break;
        }
        if (newgate == null || inputs < 1 || inputs == 1 && gate != "NOT") {
            throw new ComponentInvalidError("Not a valid component");
        }
        components.add(newgate);
        return newgate.getID();
    }

    // creates a new input or output and returns its id
    public int makeIO(String io, boolean val) throws ComponentInvalidError {
        Component add = null;
        switch (io) {
            case "IN":
                add = new INPUT(val, generateID());
                break;
            case "OUT":
                add = new OUTPUT(generateID());
                break;
        }
        if (add == null) {
            throw new ComponentInvalidError("Not a valid component");
        }
        components.add(add);
        return add.getID();
    }

    // gets component given an ID
    private Component get(int id) {
        return components.get(id);
    }

    // connects two components together (id1 -> id2)
    public void connect(int id1, int output, int id2, int input) {
        Wire wire = new Wire(id1, output, id2, input);
        get(id1).setOutput(wire, output);
        get(id2).setInput(wire, input);
    }

    // checks if a component is evaluated given an id
    public boolean isComponentEvaluated(int id) {
        return ComponentHandler.getHandler().get(id).isEvaluated();
    }

    // gets value of component
    public boolean getValue(int id, int output) {
        Component component = get(id);
        if (component instanceof OUTPUT) {
            return ((OUTPUT) component).getValue();
        }
        if (component instanceof INPUT) {
            return ((INPUT) component).getValue();
        }
        return component.getValue(output);
    }

    // returns a list of ids of parents
    public int[] getParents(int id) {
        Component current = get(id);
        Wire[] inputs = current.getInputs();
        int len = inputs.length;
        int[] parents = new int[len];
        for (int i = 0; i < len; i ++) {
            if (inputs[i] == null) {
                parents[i] = -1;
            }
            else {
                parents[i] = inputs[i].getOther(id);
            }
        }
        return parents;
    }

    // evaluates component id
    public void evaluate(int id) throws ComponentEvaluationError {
        get(id).evaluate();
    }

    // clears all components
    public void clear() {
        components.clear();
        nextID = 0;
    }
}
