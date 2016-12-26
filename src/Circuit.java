import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielkim802 on 12/25/16.
 */
// front end for gate manipulation
public class Circuit {
    // list of all gates in the circuit
    private List<Component> components;

    // next id to assign to the next gate
    private int nextID;

    // constructor
    public Circuit() {
        components = new ArrayList<Component>();
        nextID = 0;
    }

    // component id handlers
    public boolean getValue(int i) {
        return components.get(i).getValue(0);
    }

    // evaluates the circuit
    public void evaluate() {
        for (int i = 0; i < components.size(); i ++) {

            // check for unevaluated components and evaluate them
            if (!components.get(i).isEvaluated()) {
                Component current = components.get(i);
                boolean finished = false;
                while (!finished) {

                }
            }
        }
    }

    // creates a new gate and adds it to the circuit
    public int addGate(String gate, int inputs) {
        Gate add = null;
        switch(gate) {
            case "AND":
                add = new AND(inputs, nextID);
                break;
            case "OR":
                add = new OR(inputs, nextID);
                break;
            case "NOT":
                add = new NOT(nextID);
                break;
            case "NOR":
                add = new NOR(inputs, nextID);
                break;
            case "XOR":
                add = new XOR(inputs, nextID);
                break;
            case "XNOR":
                add = new XNOR(inputs, nextID);
                break;
            case "NAND":
                add = new NAND(inputs, nextID);
                break;
        }
        nextID ++;
        components.add(add);
        return nextID-1;
    }

    // creates a new input or output and adds to the circuit
    public int addIO(String io, boolean val) {
        Component add = null;
        switch (io) {
            case "IN":
                add = new INPUT(val, nextID);
            case "OUT":
                add = new OUTPUT(nextID);
        }
        nextID ++;
        components.add(add);
        return nextID-1;
    }

    // connects two gates together given two ids (comp1 -> comp2)
    public void connect(int comp1, int comp2, int input) throws ComponentConnectionError {
        components.get(comp2).connect(components.get(comp1), input);
    }
}
