/**
 * Created by danielkim802 on 12/25/16.
 */
// general class used for any component in a circuit
abstract class Component {
    private Component[] inputs;
    private boolean evaluated;
    private int id;

    // constructor
    public Component(int ID) {
        id = ID;
        evaluated = false;
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
    public Component[] getInputs() {
        return inputs;
    }
    public void setInputs(Component[] i) {
        inputs = i;
    }
    public void setInput(Component val, int index) {
        inputs[index] = val;
    }

    // checks if all parents are evaluated
    public boolean checkEvaluated() {
        boolean acc = true;
        for (int i = 0; i < inputs.length; i ++) {
            acc &= inputs[i].isEvaluated();
        }
        return acc;
    }

    // connects current component to another one
    abstract void connect(Component input, int inputnum) throws ComponentConnectionError;

    // evaluates current gate
    abstract boolean evaluate() throws ComponentEvaluationError;

    // gets value of the component
    abstract boolean getValue(int out);
}

