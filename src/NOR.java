/**
 * Created by danielkim802 on 12/25/16.
 */
public class NOR extends Gate {
    public NOR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        checkEvaluated();
        Component[] ins = getInputs();
        boolean acc = true;
        for (int i = 0; i < ins.length; i ++) {
            acc = ins[i].getValue(0) || acc;
        }
        setValue(!acc);
        return !acc;
    }
}
