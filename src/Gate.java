/**
 * Created by danielkim802 on 12/25/16.
 */
abstract class Gate {
    private Gate[] inputs;
    private boolean evaluated;
    private boolean value;
    private int id;

    // constructor
    public Gate(int inputlength, int ID) {
        inputs = new Gate[inputlength];
        evaluated = false;
        value = false;
        id = ID;
    }

    // getters and setters
    public int getID() {
        return id;
    }
    public Gate[] getInputs() {
        return inputs;
    }
    public boolean isEvaluated() {
        return evaluated;
    }
    public void setValue(boolean val) {
        value = val;
    }
    public boolean getValue() {
        return value;
    }

    // connects a gate to this gate with the specified input number
    public void connect(Gate in, int inputnum) throws GateInputError {
        try {
            inputs[inputnum] = in;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new GateInputError("Tried connecting to an invalid input: Gate " + id);
        }
    }

    // evaluates current gate
    abstract boolean evaluate() throws GateEvaluationError;
}
