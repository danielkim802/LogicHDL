import ComponentError.ComponentInvalidError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielkim802 on 12/25/16.
 */
// front end for gate manipulation
public class Circuit {
    // list of all gate ids in the circuit
    private List<Integer> components;
    private ComponentHandler handler = ComponentHandler.getHandler();
    private int nextHandle;

    // constructor
    public Circuit() {
        components = new ArrayList<Integer>();
        nextHandle = 0;
        System.out.println(components.size());
    }

    // evaluates the circuit
    public void evaluate() {
    }

    // generates a new handle
    public int generateHandle() {
        nextHandle ++;
        return nextHandle-1;
    }

    // adding components
    public int addGate(String gate, int inputs) throws ComponentInvalidError {
        components.add(handler.makeGate(gate, inputs));
        return generateHandle();
    }
    public int addIN(boolean val) throws ComponentInvalidError {
        components.add(handler.makeIO("IN", val));
        return generateHandle();
    }
    public int addOUT() throws ComponentInvalidError {
        components.add(handler.makeIO("OUT", false));
        return generateHandle();
    }

    // connects two components in the circuit together given their handles
    public void connect(int handle1, int output, int handle2, int input) {
        int id1 = handler.get(components.get(handle1)).getID();
        int id2 = handler.get(components.get(handle2)).getID();
        handler.connect(id1, output, id2, input);
    }

    // gets the component with the handle
    public boolean getValue(int handle, int output) {
        Component component = handler.get(components.get(handle));
        if (component instanceof OUTPUT) {
            return ((OUTPUT) component).getValue();
        }
        if (component instanceof INPUT) {
            return ((INPUT) component).getValue();
        }
        return component.getValue(output);
    }
}
