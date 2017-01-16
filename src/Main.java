import Components.Gates.*;
import Components.Literals.*;

public class Main {

    public static void main(String[] args) {
        Constant A = new Constant(0);
        Constant B = new Constant(1);
        Constant C = new Constant(1);
        Xor xor1 = new Xor();
        Xor xor2 = new Xor();
        And and1 = new And();
        And and2 = new And();
        Or or1 = new Or();
        Output S = new Output();
        Output Co = new Output();

        A.connect("0", xor1);
        A.connect("1", and2);
        B.connect("1", xor1);
        B.connect("0", and2);
        C.connect("1", xor2);
        C.connect("0", and1);
        xor1.connect("0", xor2);
        xor1.connect("1", and1);
        and1.connect("0", or1);
        and2.connect("1", or1);
        xor2.connect("input", S);
        or1.connect("input", Co);

        A.propagate();
        B.propagate();
        C.propagate();

        System.out.println(S.value());
        System.out.println(Co.value());
    }
}
