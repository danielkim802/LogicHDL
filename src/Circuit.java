import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class Circuit {
    // list of all gates in the circuit
    private List<Component> gates;

    // next id to assign to the next gate
    private int nextID;

    // constructor
    public Circuit() {
        gates = new ArrayList<Component>();
        nextID = 0;
    }

    // evaluates the circuit
    public void evaluate() {}

    // creates a new gate and adds it to the circuit
    public int addGate(String gate, int inputs) {
        Gate add = null;
        switch(gate) {
            case "AND":
                add = new AND(inputs, nextID);
            case "OR":
                add = new OR(inputs, nextID);
            case "NOT":
                add = new NOT(nextID);
            case "NOR":
                add = new NOR(inputs, nextID);
            case "XOR":
                add = new XOR(inputs, nextID);
            case "XNOR":
                add = new XNOR(inputs, nextID);
            case "NAND":
                add = new NAND(inputs, nextID);
        }
        nextID ++;
        gates.add(add);
        return nextID-1;
    }

    // connects two gates together given two ids
    public void connect(int gate1, int gate2, int input) throws GateInputError {
        gates.get(gate2).connect(gates.get(gate1), input);
    }
}
