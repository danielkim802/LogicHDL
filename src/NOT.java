import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class NOT extends Gate {
    public NOT(int id) {
        super(1, id);
    }

    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: NOT "+getID());
        boolean val = !getInputValue(0);
        setValue(val);
        return val;
    }
}
