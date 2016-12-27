import ComponentError.ComponentEvaluationError;
import ComponentError.ComponentInvalidError;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    }

    // gets the id of the handle
    private int get(int handle) {
        return components.get(handle);
    }

    // evaluates a single component
    private void evalComponent(int id) {
        Stack<Integer> eval = new Stack<Integer>();
        eval.push(id);
        boolean finished = false;
        while (!finished) {
            int current = eval.peek();

            // if current component is null then the original component
            // can not be calculated
            if (current == -1) {
                finished = true;
            }

            // if current component is evaluated, then pop off stack
            else if (handler.isEvaluated(current)) {
                eval.pop();
            }

            // if current component parents are evaluated then evaluate
            // the current component
            else if (handler.parentsEvaluated(current)) {
                try {
                    handler.evaluate(current);
                    eval.pop();
                }
                catch (ComponentEvaluationError e) {
                    eval.pop();
                }
            }

            // if current component parents are not evaluated,
            // and current is not evaluated, then add parents to the stack
            else {
                int[] parents = handler.getParents(current);
                for (int i = 0; i < parents.length; i ++) {
                    eval.push(parents[i]);
                }
            }

            // if stack is empty, then we are finished
            if (eval.size() == 0) {
                finished = true;
            }
        }
    }

    // evaluates the circuit
    public void evaluate() {
        for (int i = 0; i < components.size(); i ++) {
            if (!handler.isEvaluated(get(i))) {
                evalComponent(get(i));
            }
        }
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
        int id1 = get(handle1);
        int id2 = get(handle2);
        handler.connect(id1, output, id2, input);
    }

    // gets the component with the handle
    public boolean getValue(int handle, int output) {
        return handler.getValue(get(handle), output);
    }

    // clears the circuit
    public void clear() {
        components.clear();
        nextHandle = 0;
    }
}
