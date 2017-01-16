import Components.Circuit;
import Components.Gates.*;
import Components.Literals.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Circuit fulladder = new Circuit();
        fulladder.addInput("A", 0);
        fulladder.addInput("B", 0);
        fulladder.addInput("C", 0);
        fulladder.addOutput("S");
        fulladder.addOutput("Co");

        Constant A = new Constant(0);
        Constant B = new Constant(0);
        Constant C = new Constant(0);
        Xor xor1 = new Xor();
        Xor xor2 = new Xor();
        And and1 = new And();
        And and2 = new And();
        Or or1 = new Or();
        Output S = new Output();
        Output Co = new Output();

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
        or1.connect("input", fulladder.getOutput("Co"));

        fulladder.setComponents(Arrays.asList(xor1, xor2, and1, and2, or1));

        A.connect("A", fulladder);
        B.connect("B", fulladder);
        C.connect("C", fulladder);
        fulladder.connect("S", "input", S);
        fulladder.connect("Co", "input", Co);

        A.propagate();
        B.propagate();
        C.propagate();

        Constant A2 = new Constant(0);
        Constant B2 = new Constant(0);
        Constant C2 = new Constant(1);
        Output S2 = new Output();
        Output Co2 = new Output();

        Circuit fulladder2 = fulladder.copy();
        A2.connect("A", fulladder2);
        B2.connect("B", fulladder2);
        C2.connect("C", fulladder2);
        fulladder2.connect("S", "input", S2);
        fulladder2.connect("Co", "input", Co2);

        A2.propagate();
        B2.propagate();
        C2.propagate();

        System.out.println(S2.value());
        System.out.println(Co2.value());
    }
}
