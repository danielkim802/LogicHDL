import ComponentError.ComponentInvalidError;

public class Main {

    public static void main(String[] args) throws ComponentInvalidError {
        Circuit circuit = new Circuit();
        int and1 = circuit.addGate("AND", 2);
        int in1 = circuit.addIN(true);
        int in2 = circuit.addIN(false);
        int out1 = circuit.addOUT();
        int out2 = circuit.addOUT();
        circuit.connect(in1, 0, and1, 0);
        circuit.connect(in2, 0, and1, 1);
        circuit.connect(and1, 0, out1, 0);
        circuit.connect(and1, 0, out2, 0);

        System.out.println(circuit.getValue(out1, 0));
    }
}
