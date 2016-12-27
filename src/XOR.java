import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
public class XOR extends Gate {
    public XOR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated("Parents are not evaluated: XOR "+getID());
        int count = 0;
        for (int i = 0; i < getInputs().length; i ++) {
            if (getInputValue(0)) {
                count ++;
            }
        }
        setValue(count == 1);
        return count == 1;
    }
}
