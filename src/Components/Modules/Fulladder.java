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
public class Fulladder extends Circuit {

    public Fulladder() {
        super();

        // initialize fulladder
        addInput("A", 0);
        addInput("B", 0);
        addInput("C", 0);
        addOutput("S");
        addOutput("Cout");

        Xor xor1 = new Xor();
        Xor xor2 = new Xor();
        And and1 = new And();
        And and2 = new And();
        Or or1 = new Or();

        getInput("A").connect("0", xor1);
        getInput("A").connect("1", and2);
        getInput("B").connect("1", xor1);
        getInput("B").connect("0", and2);
        getInput("C").connect("1", xor2);
        getInput("C").connect("0", and1);
        xor1.connect("0", xor2);
        xor1.connect("1", and1);
        and1.connect("0", or1);
        and2.connect("1", or1);
        xor2.connect("input", getOutput("S"));
        or1.connect("input", getOutput("Cout"));

        ArrayList<Component> comps = new ArrayList<>();
        comps.addAll(Arrays.asList(xor1, xor2, and1, and2, or1));
        setComponents(comps);
    }
}
