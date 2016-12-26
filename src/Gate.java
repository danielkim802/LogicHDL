/**
 * Created by danielkim802 on 12/25/16.
 */
abstract class Gate extends Component {
    private boolean value;

    // constructor
    public Gate(int inputlength, int ID) {
        super(ID);
        setInputs(new Component[inputlength]);
    }

    // value setters and getters
    public void setValue(boolean val) {
        value = val;
        setEvaluated(true);
    }
    public boolean getValue(int out) {
        return value;
    }

    // connects a gate to this gate with the specified input number
    public void connect(Component in, int inputnum) throws GateInputError {
        try {
            setInput(in, inputnum);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new GateInputError("Tried connecting gate to an invalid input: Gate " + getID());
        }
    }
}
