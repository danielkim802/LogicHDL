/**
 * Created by danielkim802 on 12/25/16.
 */
public class INPUT extends Gate {
    public INPUT(boolean value, int id) {
        super(0, id);
        setValue(value);
    }
    public boolean evaluate() throws GateEvaluationError {
        return getValue(0);
    }
}
