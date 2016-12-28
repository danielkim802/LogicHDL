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
        boolean acc = false;
        boolean unassigned = false;
        for (int i = 0; i < getInputs().length; i ++) {
            if (isInputAssigned(i)) {
                boolean inputval = getInputValue(i);
                acc |= inputval;
            }
            else {
                unassigned = true;
            }
        }

        if (unassigned && !acc) {
            throw new GateEvaluationError("Output unspecified: NOR "+getID());
        }

        setEvaluated(true);
        setValue(!acc);
        return !acc;
    }
}
