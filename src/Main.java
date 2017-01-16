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
        or1.connect("input", fulladder.getOutput("Co"));

        fulladder.setComponents(Arrays.asList(xor1, xor2, and1, and2, or1));

        long[] A = {1,0,1,1};
        long[] B = {0,0,0,1};

        Constant A0 = new Constant(A[3]);
        Constant A1 = new Constant(A[2]);
        Constant A2 = new Constant(A[1]);
        Constant A3 = new Constant(A[0]);
        Constant B0 = new Constant(B[3]);
        Constant B1 = new Constant(B[2]);
        Constant B2 = new Constant(B[1]);
        Constant B3 = new Constant(B[0]);
        Constant C = new Constant(1);
        Output S0 = new Output();
        Output S1 = new Output();
        Output S2 = new Output();
        Output S3 = new Output();
        Output Co = new Output();

        Circuit f0 = fulladder.copy();
        Circuit f1 = fulladder.copy();
        Circuit f2 = fulladder.copy();
        Circuit f3 = fulladder.copy();

        A0.connect("A", f0);
        A1.connect("A", f1);
        A2.connect("A", f2);
        A3.connect("A", f3);
        B0.connect("B", f0);
        B1.connect("B", f1);
        B2.connect("B", f2);
        B3.connect("B", f3);
        C.connect("C", f0);
        f0.connect("Co", "C", f1);
        f1.connect("Co", "C", f2);
        f2.connect("Co", "C", f3);
        f3.connect("Co", "input", Co);
        f0.connect("S", "input", S0);
        f1.connect("S", "input", S1);
        f2.connect("S", "input", S2);
        f3.connect("S", "input", S3);

        A0.propagate();
        A1.propagate();
        A2.propagate();
        A3.propagate();
        B0.propagate();
        B1.propagate();
        B2.propagate();
        B3.propagate();
        C.propagate();

        System.out.println(""+S3.value()+S2.value()+S1.value()+S0.value());
        System.out.println(Co.value());
    }
}
