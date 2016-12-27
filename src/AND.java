import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class AND extends Gate {
    public AND(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: AND "+getID());
        boolean acc = true;
        for (int i = 0; i < getInputs().length; i ++) {
            acc &= getInputValue(i);
        }
        setEvaluated(true);
        setValue(acc);
        return acc;
    }
}
