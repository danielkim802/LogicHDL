import ComponentError.ComponentEvaluationError;
import ComponentError.GateEvaluationError;

/**
 * Created by danielkim802 on 12/25/16.
 */
abstract class Gate extends Component {
    // constructor
    public Gate(int inputlength, int ID) {
        super(inputlength, 1, ID);
    }

    // getters and setters
    public void setValue(boolean val) {
        setValue(val, 0);
    }
    public boolean getValue() {
        return getValue(0);
    }

    // check evaluation override
    public void checkEvaluated(String msg) throws GateEvaluationError {
        try {
            super.checkEvaluated();
        }
        catch (ComponentEvaluationError e) {
            throw new GateEvaluationError(msg);
        }
    }
}
