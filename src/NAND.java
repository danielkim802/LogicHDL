import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class NAND extends Gate {
    public NAND(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: NAND "+getID());
        boolean acc = true;
        boolean unassigned = false;
        for (int i = 0; i < getInputs().length; i ++) {
            if (isInputAssigned(i)) {
                acc &= getInputValue(i);
            } else {
                unassigned = true;
            }
        }

        if (unassigned && acc) {
            throw new GateEvaluationError("Output unspecified: NAND "+getID());
        }

        setEvaluated(true);
        setValue(!acc);
        return !acc;
    }
}
