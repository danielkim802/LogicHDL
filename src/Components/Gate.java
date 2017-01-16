package Components;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.OptionalLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Gate extends Component {
    private LongBinaryOperator operator;
    private LongUnaryOperator finaloperator = a -> a;

    public Gate(int inputlen) {
        super();
        for (int i = 0; i < inputlen; i ++) {
            getInputs().put(""+i, new Wire());
        }
        getOutputs().put("output", new ArrayList<Wire>());
    }

    public void setOp(LongBinaryOperator op) {
        operator = op;
    }
    public void setFinalOp(LongUnaryOperator op) {
        finaloperator = op;
    }

    public void connect(String input, Component c) {
        connect("output", input, c);
    }

    public Gate copy() {
        try {
            Gate copy;
            if (getClass().getName() == "Not") {
                copy = getClass().newInstance();
            }
            else {
                copy = getClass().getConstructor(int.class).newInstance(getInputs().size());
            }
            copy.operator = operator;
            copy.finaloperator = finaloperator;
            return copy;
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public void propagate() {
        if (allInputsValid()) {

            // map each input to a long |> apply operator to all values
            long result = getInputs().values().stream().mapToLong(a -> a.value() ).reduce( operator ).getAsLong();

            // propagate result to outputs
            for (Wire wire : getOutputs().get("output")) {
                wire.set(finaloperator.applyAsLong(result));
            }
        }
    }
}
