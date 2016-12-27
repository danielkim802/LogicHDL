import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class NOR extends Gate {
    public NOR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: NOR "+getID());
        boolean acc = true;
        for (int i = 0; i < getInputs().length; i ++) {
            acc |= getInputValue(i);
        }
        setValue(!acc);
        return !acc;
    }
}
