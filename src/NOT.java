/**
 * Created by danielkim802 on 12/25/16.
 */
public class NOT extends Gate {
    public NOT(int id) {
        super(1, id);
    }

    public boolean evaluate() throws GateEvaluationError {
        boolean val = !getInputs()[0].getValue();
        setValue(val);
        return val;
    }
}
