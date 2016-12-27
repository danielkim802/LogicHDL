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
    public Component get(int id) {
        return components.get(id);
    }

    // connects two components together (id1 -> id2)
    public void connect(int id1, int output, int id2, int input) {
        Wire wire = new Wire(id1, output, id2, input);
        get(id1).setOutput(wire, output);
        get(id2).setInput(wire, input);
    }
}
