/**
 * Created by danielkim802 on 12/25/16.
 */
public class OUTPUT extends Gate {
    public OUTPUT(int id) {
        super(1, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated();
        setValue(getInputs()[0].getValue(0));
        return getValue(0);
    }
}
