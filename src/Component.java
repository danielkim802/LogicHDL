import ComponentError.ComponentEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
// general class used for any component in a circuit
abstract class Component {
    private Wire[] inputs;
    private Wire[] outputs;
    private boolean[] values;
    private boolean evaluated;
    private int id;

    // constructor
    public Component(int inputlen, int outputlen, int ID) {
        id = ID;
        evaluated = false;
        inputs = new Wire[inputlen];
        outputs = new Wire[outputlen];
        values = new boolean[outputlen];
    }

    // getters and setters
    public boolean isEvaluated() {
        return evaluated;
    }
    public void setEvaluated(boolean val) {
        evaluated = val;
    }
    public int getID() {
        return id;
    }
    public void setValue(boolean val, int out) {
        values[out] = val;
    }
    public boolean getValue(int out) {
        return values[out];
    }
    public Wire[] getInputs() {
        return inputs;
    }
    public void setInput(Wire wire, int input) {
        inputs[input] = wire;
    }
    public Wire[] getOutputs() {
        return outputs;
    }
    public void setOutput(Wire wire, int output) {
        outputs[output] = wire;
    }

    // gets value of connection
    public boolean getInputValue(int index) {
        ComponentHandler handler = ComponentHandler.getHandler();
        int other = inputs[index].getOther(id);
        int otherOutput = inputs[index].getOtherIndex(id);
        return handler.getValue(other, otherOutput);
    }

    // checks if the input is assigned
    public boolean isInputAssigned(int index) {
        return inputs[index] != null;
    }

    // checks if all parents are evaluated
    // not assigned inputs are ignored
    public void checkEvaluated() throws ComponentEvaluationError {
        ComponentHandler handler = ComponentHandler.getHandler();
        boolean acc = true;
        for (int i = 0; i < inputs.length; i ++) {
            if (inputs[i] != null) {
                acc &= handler.isComponentEvaluated(inputs[i].getOther(id));
            }
        }
        if (!acc) {
            throw new ComponentEvaluationError("Parents are not evaluated: Component "+getID());
        }
    }

    // evaluates current gate
    abstract boolean evaluate() throws ComponentEvaluationError;
}

