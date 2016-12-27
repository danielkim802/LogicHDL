import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class OUTPUT extends Gate {
    private boolean value;

    public OUTPUT(int id) {
        super(1, id);
        value = false;
    }
    public boolean getValue() {
        return value;
    }

    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: OUTPUT "+getID());
        value = getInputValue(0);
        return value;
    }
}
