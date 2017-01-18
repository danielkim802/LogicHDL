import Components.Literals.*;
import Components.Modules.Fulladder;
import Render.ResourceLibrary;

public class Main {

    public static void main(String[] args) {
        long[] A = {1,0,1,1};
        long[] B = {1,0,0,1};

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

        Fulladder f0 = new Fulladder();
        Fulladder f1 = new Fulladder();
        Fulladder f2 = new Fulladder();
        Fulladder f3 = new Fulladder();

        A0.connect("A", f0);
        A1.connect("A", f1);
        A2.connect("A", f2);
        A3.connect("A", f3);
        B0.connect("B", f0);
        B1.connect("B", f1);
        B2.connect("B", f2);
        B3.connect("B", f3);
        C.connect("C", f0);
        f0.connect("Cout", "C", f1);
        f1.connect("Cout", "C", f2);
        f2.connect("Cout", "C", f3);
        f3.connect("Cout", "input", Co);
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
        new View();
    }
}
