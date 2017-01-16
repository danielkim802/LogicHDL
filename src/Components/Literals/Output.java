package Components.Literals;

import Components.Component;
import Components.Wire;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Output extends Component {
    private long value;

    public Output() {
        super();
        getInputs().put("input", new Wire());
    }

    public long value() {
        return value;
    }

    public void propagate() {
        if (getInputs().get("input").isValid()) {
            value = getInputs().get("input").value();
        }
    }
}
