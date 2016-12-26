/**
 * Created by danielkim802 on 12/25/16.
 */
// general class used for any component in a circuit
abstract class Component {
    private Component[] inputs;
    private boolean evaluated;
    private boolean value;
    private int id;

    public Component(int ID) {
        id = ID;
        evaluated = false;
        value = false;
    }

    // getters and setters
    public boolean isEvaluated() {
        return evaluated;
    }
    public void setValue(boolean val) {
        value = val;
    }
    public boolean getValue() {
        return value;
    }
    public int getID() {
        return id;
    }
    public Component[] getInputs() {
        return inputs;
    }
    public void setInputs(Component[] i) {
        inputs = i;
    }
    public void setInput(Component val, int index) {
        inputs[index] = val;
    }

    // connects current component to another one
    abstract void connect(Component input, int inputnum) throws ComponentConnectionError;

    // evaluates current gate
    abstract boolean evaluate() throws ComponentEvaluationError;
}

