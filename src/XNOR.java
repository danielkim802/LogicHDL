/**
 * Created by danielkim802 on 12/25/16.
 */
public class XNOR extends Gate {
    public XNOR(int num, int id) {
        super(num, id);
    }
    public boolean evaluate() throws GateEvaluationError {
        Component[] ins = getInputs();
        boolean acc = false;
        int count = 0;
        for (int i = 0; i < ins.length; i ++) {
            if (ins[i].getValue()) {
                count ++;
            }
        }
        acc = count == 1;
        setValue(!acc);
        return !acc;
    }
}
