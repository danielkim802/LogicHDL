import Components.Gates.*;
import Components.Literals.*;

public class Main {

    public static void main(String[] args) {
        And and1 = new And();
        Constant in1 = new Constant(1);
        Constant in2 = new Constant(1);
        Output out1 = new Output();
        in1.connect("output", "0", and1);
        in2.connect("output", "1", and1);
        and1.connect("output", "input", out1);
        in1.propagate();
        in2.propagate();
        System.out.println(out1.value());
    }
}
