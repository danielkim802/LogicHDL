/**
 * Created by danielkim802 on 12/25/16.
 */
public class OR extends Gate {
    public OR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        Component[] ins = getInputs();
        boolean acc = true;
        for (int i = 0; i < ins.length; i ++) {
            acc = ins[i].getValue() || acc;
        }
        setValue(acc);
        return acc;
    }
}
