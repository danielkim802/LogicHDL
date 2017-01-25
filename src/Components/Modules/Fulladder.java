package Components.Modules;

import Components.Circuit;
import Components.Component;
import Components.Gates.And;
import Components.Gates.Or;
import Components.Gates.Xor;
import Components.Wire;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Fulladder extends Component {
    private Circuit fulladder = new Circuit();

    public Fulladder() {
        super(0, 0);

        fulladder.addInput("A", 0);
        fulladder.addInput("B", 0);
        fulladder.addInput("C", 0);
        fulladder.addOutput("S");
        fulladder.addOutput("Cout");

        Xor xor1 = new Xor();
        Xor xor2 = new Xor();
        And and1 = new And();
        And and2 = new And();
        Or or1 = new Or();

        fulladder.getInput("A").connect("0", xor1);
        fulladder.getInput("A").connect("1", and2);
        fulladder.getInput("B").connect("1", xor1);
        fulladder.getInput("B").connect("0", and2);
        fulladder.getInput("C").connect("1", xor2);
        fulladder.getInput("C").connect("0", and1);
        xor1.connect("0", xor2);
        xor1.connect("1", and1);
        and1.connect("0", or1);
        and2.connect("1", or1);
        xor2.connect("input", fulladder.getOutput("S"));
        or1.connect("input", fulladder.getOutput("Cout"));

        ArrayList<Component> comps = new ArrayList<>();
        comps.addAll(Arrays.asList(xor1, xor2, and1, and2, or1));
        fulladder.setComponents(comps);
    }

    public Fulladder copy() {
        Fulladder copy = new Fulladder();
        copy.fulladder = fulladder.copy();
        return copy;
    }

    public void propagate() {
        if (allInputsAssigned()) {
            fulladder.getInput("A").set(getInputs().get("A").value());
            fulladder.getInput("B").set(getInputs().get("B").value());
            fulladder.getInput("C").set(getInputs().get("C").value());
            fulladder.propagateLocal();

            for (Wire wire : getOutputs().get("S")) {
                wire.set(fulladder.getOutput("S").value());
            }
            for (Wire wire : getOutputs().get("Cout")) {
                wire.set(fulladder.getOutput("Cout").value());
            }
        }
    }

    public void draw(Graphics2D g) {}
    public void setDotPositions() {}
    public void setIO(int ins, int outs) {
        getInputs().put("A", new Wire());
        getInputs().put("B", new Wire());
        getInputs().put("C", new Wire());
        getOutputs().put("S", new ArrayList<>());
        getOutputs().put("Cout", new ArrayList<>());
    }
}
