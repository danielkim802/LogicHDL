import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class OR extends Gate {
    public OR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: OR "+getID());
        boolean acc = false;
        for (int i = 0; i < getInputs().length; i ++) {
            acc |= getInputValue(i);
        }
        setEvaluated(true);
        setValue(acc);
        return acc;
    }
}
