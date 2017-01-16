package Components.Literals;

import Components.Component;
import Components.Wire;

import java.util.ArrayList;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Constant extends Component {
    private long value = 0;

    public Constant(long val) {
        super();
        value = val;
        getOutputs().put("output", new ArrayList<>());
    }

    public void set(long val) {
        value = val;
    }

    public void propagate() {
        for (Wire wire : getOutputs().get("output")) {
            wire.set(value);
        }
    }

}
